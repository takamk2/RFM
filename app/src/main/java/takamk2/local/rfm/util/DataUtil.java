package takamk2.local.rfm.util;

import android.content.Context;

import java.io.File;

/**
 * Created by takamk2 on 17/02/23.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class DataUtil {

    private static final String PICTURES_DIR_NAME = "pictures";

    public static File getPictureDirectory(Context context) {

        File dir = context.getFilesDir();
        File directory = new File(dir.getPath() + "/" + PICTURES_DIR_NAME);
        if (!directory.exists()) {
            directory.mkdir();
        }

        return directory;

    }
}
