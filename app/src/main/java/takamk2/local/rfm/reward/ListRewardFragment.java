package takamk2.local.rfm.reward;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import takamk2.local.rfm.R;
import takamk2.local.rfm.RFMApplication;
import takamk2.local.rfm.image.BitmapCache;
import takamk2.local.rfm.common.BaseActivity;
import takamk2.local.rfm.common.BaseFragment;
import takamk2.local.rfm.image.SaveImage;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRewardFragment extends BaseFragment {

    private final Handler mHandler = new Handler();

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

    public static Fragment getNewInstance() {
        ListRewardFragment fragment = new ListRewardFragment();
        return fragment;
    }

    public ListRewardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mBtDisplay = (Button) view.findViewById(R.id.bt_go_to_display);
        mBtAdd = (Button) view.findViewById(R.id.bt_go_to_add);

        // Actions
        mBtDisplay.setOnClickListener(mOnClickListener);
        mBtAdd.setOnClickListener(mOnClickListener);
    }

    private void onClickDisplay() {
        Fragment fragment = DisplayRewardFragment.getNewInstance(1);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(((BaseActivity)getActivity()).getFragmentContainerId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void onClickAdd() {
        Fragment fragment = EditRewardFragment.getNewInstance();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(((BaseActivity)getActivity()).getFragmentContainerId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
