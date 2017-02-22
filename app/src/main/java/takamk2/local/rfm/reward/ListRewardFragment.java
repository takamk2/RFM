package takamk2.local.rfm.reward;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import takamk2.local.rfm.R;
import takamk2.local.rfm.common.BaseActivity;
import takamk2.local.rfm.common.BaseFragment;
import takamk2.local.rfm.db.RFMDBStore;
import takamk2.local.rfm.util.DataUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRewardFragment extends BaseFragment {

    private final Handler mHandler = new Handler();

    private RewardsAdapter mRewardsAdapter;

    private ListView mLvRewards = null;
    private Button mBtDisplay = null;
    private Button mBtAdd = null;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_go_to_display:
                    onClickDisplay();
                    break;
                case R.id.bt_go_to_add:
                    onClickAdd();
                    break;
            }
        }
    };

    LoaderManager.LoaderCallbacks<Cursor> mRewardsLoadCallbacks
            = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            return new CursorLoader(getActivity(), RFMDBStore.Rewards.CONTENT_URI,
                    RFMDBStore.Rewards.PROJECTION_ALL, null, null, null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            mRewardsAdapter.swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mRewardsAdapter.swapCursor(null);
        }
    };

    /* ------------------------------------------------------------------------------------------ */
    public static Fragment getNewInstance() {
        ListRewardFragment fragment = new ListRewardFragment();
        return fragment;
    }

    /* ------------------------------------------------------------------------------------------ */
    public ListRewardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRewardsAdapter = new RewardsAdapter(getActivity());

        getLoaderManager().initLoader(0, null, mRewardsLoadCallbacks);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_reward, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLvRewards = (ListView) view.findViewById(R.id.lv_rewards);
        mBtDisplay = (Button) view.findViewById(R.id.bt_go_to_display);
        mBtAdd = (Button) view.findViewById(R.id.bt_go_to_add);

        // Attach
        mLvRewards.setAdapter(mRewardsAdapter);

        // Actions
        mBtDisplay.setOnClickListener(mOnClickListener);
        mBtAdd.setOnClickListener(mOnClickListener);
    }

    private void onClickDisplay() {
        Fragment fragment = DisplayRewardFragment.getNewInstance(1);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(((BaseActivity) getActivity()).getFragmentContainerId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void onClickAdd() {
        Fragment fragment = EditRewardFragment.getNewInstance();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(((BaseActivity) getActivity()).getFragmentContainerId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /* ------------------------------------------------------------------------------------------ */
    class RewardsAdapter extends CursorAdapter {

        public RewardsAdapter(Context context) {
            super(context, null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            LayoutInflater inflater
                    = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.item_rewards, viewGroup, false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            String name = cursor.getString(cursor.getColumnIndex(RFMDBStore.Rewards.COLUMN_NAME));
            Long price = cursor.getLong(cursor.getColumnIndex(RFMDBStore.Rewards.COLUMN_PRICE));
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvName.setText(name);
            tvPrice.setText(String.valueOf(price));
        }
    }
}
