package takamk2.local.rfm.common;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import takamk2.local.rfm.RFMApplication;
import takamk2.local.rfm.image.BitmapCache;

/**
 * Created by takamk2 on 17/02/11.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class BaseFragment extends Fragment {

    protected boolean isViewInitialized = false;

    private RFMApplication mApplication;

    public static Fragment getNewInstance() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (RFMApplication) getActivity().getApplication();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewInitialized = true;
    }

    @Override
    public void onDestroyView() {
        isViewInitialized = false;
        super.onDestroyView();
    }

    public RFMApplication getApplication() {
        return mApplication;
    }

    public BitmapCache getBitmapCache() {
        return mApplication.getBitmapCache();
    }
}
