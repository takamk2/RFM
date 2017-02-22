package takamk2.local.rfm.image;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by takamk2 on 17/02/22.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class AjustBitmap extends Thread {

    private final Bitmap mSourceBitmap;
    private final int mOrientation;
    private final int mSize;
    private final Callbacks mCallbacks;

    public interface Callbacks {
        void onCompleted(Bitmap bitmap);
        void onFailed(String reason);
    }

    public AjustBitmap(Bitmap sourceBitmap, int orientation, int size, Callbacks callbacks) {
        mSourceBitmap = sourceBitmap;
        mOrientation = orientation;
        mSize = size;
        mCallbacks = callbacks;
    }

    @Override
    public void run() {
        if (mSourceBitmap == null) {
            mCallbacks.onFailed("sourceBitmap is null");
            return;
        }

        int sourceWidth = mSourceBitmap.getWidth();
        int sourceHeight = mSourceBitmap.getHeight();
        int minSourceSize = Math.min(sourceWidth, sourceHeight);
        int startWidth = (sourceWidth == minSourceSize) ? 0 : (sourceWidth - minSourceSize) / 2;
        int startHeight = (sourceHeight == minSourceSize) ? 0 : (sourceHeight - minSourceSize) / 2;

        Matrix matrix = new Matrix();
        matrix.postRotate(mOrientation);
        Bitmap intermediate =
                Bitmap.createBitmap(mSourceBitmap, startWidth, startHeight, minSourceSize, minSourceSize, matrix, true);
        Bitmap bitmap = Bitmap.createScaledBitmap(intermediate, mSize, mSize, true);

        // Todo: DEBUG
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mCallbacks.onCompleted(bitmap);
    }
}
