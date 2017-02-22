package takamk2.local.rfm;

import android.app.Application;

import takamk2.local.rfm.image.BitmapCache;
import timber.log.Timber;

/**
 * Created by takamk2 on 17/02/18.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class RFMApplication extends Application {

    private BitmapCache mBitmapCache;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        mBitmapCache = new BitmapCache();
    }

    public BitmapCache getBitmapCache() {
        return mBitmapCache;
    }
}
