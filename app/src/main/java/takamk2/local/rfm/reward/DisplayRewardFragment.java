package takamk2.local.rfm.reward;


import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import takamk2.local.rfm.R;
import takamk2.local.rfm.RFMApplication;
import takamk2.local.rfm.common.BaseFragment;
import takamk2.local.rfm.db.RFMDBStore;
import takamk2.local.rfm.image.AjustBitmap;
import takamk2.local.rfm.image.BitmapCache;
import takamk2.local.rfm.image.SaveImage;
import takamk2.local.rfm.util.Converter;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayRewardFragment extends BaseFragment {

    private static final String LOGTAG = DisplayRewardFragment.class.getSimpleName();

    private static final String KEY_REWARD_ID = "reward_id";

    private static final int REQUEST_ACTION_PICK = 1;

    private final Handler mHandler = new Handler();

    private long mRewardId;

    private TextView mTvName = null;
    private TextView mTvPrice = null;
    private TextView mTvRemainingPrice = null;
    private TextView mTvDescription = null;
    private TextView mTvUrl = null;
    private TextView mTvPriority = null;
    private ImageView mIvImage = null;
    private Button mBtChoose = null;
    private Button mBtBuy = null;
    private Button mBtRevert = null;
    private TextView mTvCompleted = null;
    private TextView mTvLastModified = null;
    private ListView mLvTasks = null;
    private Button mBtGoToEdit = null;

    private RewardEntity mReward = null;

    private boolean mCanUseView = false;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_choose:
                    onClickChoose();
                    break;
                case R.id.bt_buy:
                    onClickBuy();
                    break;
                case R.id.bt_revert:
                    onClickRevert();
                    break;
                case R.id.bt_go_to_edit:
                    onClickGoToEdit();
                    break;
                default:
                    break;
            }
        }
    };

    private LoaderManager.LoaderCallbacks<Cursor> mRewardLoaderCallbacks
            = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            Timber.d("onCreateLoader - DEBUG: START");

            if (mRewardId == -1) {
                return null;
            }

            Uri uri = ContentUris.withAppendedId(RFMDBStore.Rewards.CONTENT_URI, mRewardId);
            return new CursorLoader(getActivity(), uri, RFMDBStore.Rewards.PROJECTION_ALL,
                    null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            Timber.d("onLoadFinished - DEBUG: START");

            if (cursor != null) {
                mReward = new RewardEntity();
                mReward.setValuesFromCursor(cursor);
            }

            updateViews();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Timber.d("onLoaderReset - DEBUG: START");
        }
    };


    private AjustBitmap.Callbacks mAjustBitmapCallbacks = new AjustBitmap.Callbacks() {

        @Override
        public void onCompleted(Bitmap bitmap) {
            new SaveImage(getActivity(), "dummy.jpg", bitmap, mSaveImageCallbacks).start();
        }

        @Override
        public void onFailed(String reason) {

        }
    };

    private SaveImage.Callbacks mSaveImageCallbacks = new SaveImage.Callbacks() {

        @Override
        public void onCompleted(final String fileName, final Bitmap bitmap) {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    BitmapCache bitmapCache = ((RFMApplication) getActivity().getApplication()).getBitmapCache();
                    bitmapCache.putBitmap(fileName, bitmap);
                    updateViews();
                    Toast.makeText(getActivity(), "Save success: byteSize=" + bitmap.getRowBytes(), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onFailed(final String reason) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Save failed: " + reason, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    /* ------------------------------------------------------------------------------------------ */
    public static Fragment getNewInstance(long rewardId) {
        DisplayRewardFragment fragment = new DisplayRewardFragment();
        Bundle args = new Bundle();
        args.putLong(KEY_REWARD_ID, rewardId);
        fragment.setArguments(args);
        return fragment;
    }

    /* ------------------------------------------------------------------------------------------ */
    public DisplayRewardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mRewardId = args.getLong(KEY_REWARD_ID, -1);

        if (mRewardId == -1) {
            Toast.makeText(getActivity(), "Data does not exist.", Toast.LENGTH_LONG).show();
            getFragmentManager().popBackStack();
            return;
        }

        getLoaderManager().initLoader(0, null, mRewardLoaderCallbacks);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_display_reward, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mTvRemainingPrice = (TextView) view.findViewById(R.id.tv_remaining_price);
        mTvDescription = (TextView) view.findViewById(R.id.tv_description);
        mTvUrl = (TextView) view.findViewById(R.id.tv_url);
        mTvPriority = (TextView) view.findViewById(R.id.tv_priority);
        mIvImage = (ImageView) view.findViewById(R.id.iv_image);
        mBtChoose = (Button) view.findViewById(R.id.bt_choose);
        mBtBuy = (Button) view.findViewById(R.id.bt_buy);
        mBtRevert = (Button) view.findViewById(R.id.bt_revert);
        mTvCompleted = (TextView) view.findViewById(R.id.tv_completed);
        mTvLastModified = (TextView) view.findViewById(R.id.tv_last_modified);
        mLvTasks = (ListView) view.findViewById(R.id.lv_tasks);
        mBtGoToEdit = (Button) view.findViewById(R.id.bt_go_to_edit);

        // Actions
        mBtChoose.setOnClickListener(mOnClickListener);
        mBtBuy.setOnClickListener(mOnClickListener);
        mBtRevert.setOnClickListener(mOnClickListener);
        mBtGoToEdit.setOnClickListener(mOnClickListener);

        mCanUseView = true;
    }

    @Override
    public void onDestroyView() {
        mCanUseView = false;
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.i("onActivityResult - DEBUG: requestCode=%d resultCode=%d data=%s", requestCode, resultCode, data.toString());

        switch (requestCode) {
            case REQUEST_ACTION_PICK:
                if (resultCode == Activity.RESULT_OK) {
//                    Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 59878);
                    Uri uri = data.getData();
                    Bitmap sourceBitmap = null;
                    try {
                        sourceBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (sourceBitmap == null) {
                        Timber.i("onCreate - DEBUG: sourceBitmap is null");
                    }

                    int orientation = 0;
                    String[] projection = {MediaStore.Images.Media.ORIENTATION};
                    Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        orientation = cursor.getInt(cursor.getColumnIndex(projection[0]));
                    }

                    new AjustBitmap(sourceBitmap, orientation, 800, mAjustBitmapCallbacks).start();
                }
                break;
            default:
                Timber.i("onActivityResult - DEBUG: Unknown result");
                break;
        }
    }

    /* ------------------------------------------------------------------------------------------ */
    private void updateViews() {

        if (!mCanUseView) {
            Timber.i("Cannot update view because views are not initialized yet");
            return;
        }

        if (mReward == null) {
            Timber.i("Cannot update view because values are got by db yet");
            return;
        }

        mTvName.setText(mReward.getName());
        mTvPrice.setText(Converter.convertLongToCurrency(mReward.getPrice()));
        mTvRemainingPrice.setText(Converter.convertLongToCurrency(mReward.getRemainingPrice())); // Todo: necessary savings
        mTvDescription.setText(mReward.getDescription());
        mTvUrl.setText(mReward.getUrl());
        mTvPriority.setText(mReward.getPriority().getName());
        mTvCompleted.setText(Converter.convertTimestampToStringDate(mReward.getCompleted()));
        mTvLastModified.setText(Converter.convertTimestampToStringDate(mReward.getModified()));

        Long c = mReward.getCompleted();
        if (mReward.getCompleted() == null) {
            mBtBuy.setVisibility(View.VISIBLE);
            mBtRevert.setVisibility(View.GONE);
            mTvCompleted.setVisibility(View.GONE);
            mBtGoToEdit.setVisibility(View.VISIBLE);
        } else {
            mBtBuy.setVisibility(View.GONE);
            mBtRevert.setVisibility(View.VISIBLE);
            mTvCompleted.setVisibility(View.VISIBLE);
            mBtGoToEdit.setVisibility(View.GONE);
        }

        Bitmap bitmap = mReward.getImage(getActivity(), getApplication());
        if (bitmap != null) {
            mIvImage.setImageBitmap(bitmap);
        }

//        mReward.loadImageFromPath(new RewardEntity.LoadImageCallback() {
//            @Overide
//            public void onLoaded(Bitmap bitmap) {
//                mIvImage.setImageBitmap(bitmap);
//            }
//            @Overide
//            public void onFailed(String reason) {
//                mIvImage.setImageBitmap(bitmap);
//            }
//        });

//        mLvTasks
    }

    private void onClickChoose() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_ACTION_PICK);
    }

    private void onClickBuy() {
        Log.d(LOGTAG, "onClickBuy -- DEBUG: START");
    }

    private void onClickRevert() {
        Log.d(LOGTAG, "onClickRevert -- DEBUG: START");
    }

    private void onClickGoToEdit() {
        Log.d(LOGTAG, "onClickGoToEdit -- DEBUG: START");
    }
}
