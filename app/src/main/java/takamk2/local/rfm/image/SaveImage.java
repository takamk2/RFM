package takamk2.local.rfm.image;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import takamk2.local.rfm.util.DataUtil;
import timber.log.Timber;

/**
 * Created by takamk2 on 17/02/22.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class SaveImage extends Thread {

    private static final String PICTURE_DIR_NAME = "picture";

    private final Context mContext;

    private final String mFileName;
    private final Bitmap mBitmap;

    private Callbacks mCallbacks;

    public interface Callbacks {
        void onCompleted(String fileName, Bitmap bitmap);
        void onFailed(String reason);
    }

    public SaveImage(Context context, String fileName, Bitmap bitmap, Callbacks callbacks) {
        mContext = context;
        mFileName = fileName;
        mBitmap = bitmap;
        mCallbacks = callbacks;
    }

    @Override
    public void run() {
        if (mBitmap == null) {
            mCallbacks.onFailed("bitmap is null");
            return;
        }

        if (mFileName == null) {
            mCallbacks.onFailed("bitmap is null");
            return;
        }

        File directory = DataUtil.getPictureDirectory(mContext);
        try {
            FileOutputStream fos =
                    new FileOutputStream(directory.getAbsolutePath() + "/" + mFileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            mCallbacks.onFailed(e.getMessage());
            Timber.i("onCreate - DEBUG: FileNotFoundException=%s", e.getMessage());
        } catch (IOException e) {
            mCallbacks.onFailed(e.getMessage());
            Timber.i("onCreate - DEBUG: IOException=%s", e.getMessage());
        }

        // Todo: DEBUG
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mCallbacks.onCompleted(mFileName, mBitmap);
    }
}
