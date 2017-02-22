package takamk2.local.rfm.image;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import timber.log.Timber;

public class BitmapCache {

    private static final String LOGTAG = BitmapCache.class.getSimpleName();

    private LruCache<String, Bitmap> mMemoryCache;

    public BitmapCache() {

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    public Bitmap getBitmap(String fileName) {
        return mMemoryCache.get(fileName);
    }

    public void putBitmap(String fileName, Bitmap bitmap) {
        Bitmap old = null;
        synchronized (mMemoryCache) {
            old = mMemoryCache.put(fileName, bitmap);
//            if (getBitmap(fileName) == null) {
//                Timber.i("putBitmap - DEBUG: put bitmap : fileName=" + fileName);
//            } else {
//                Timber.i("putBitmap - DEBUG: bitmap is already exists : fileName=" + fileName);
//            }
        }

        if (old != null) {
            if (!old.isRecycled()) {
                old.recycle();
            }
            old = null;
            Log.d(LOGTAG, "putBitmap - DEBUG: bitmap has been recycled");
        }
    }
}
