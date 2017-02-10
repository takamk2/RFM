package takamk2.local.rfm.task;

import android.content.ContentValues;
import android.net.Uri;

import takamk2.local.rfm.common.BaseEntity;

/**
 * Created by takamk2 on 17/02/10.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class TaskEntity extends BaseEntity {

    private static Uri mContentUri = RFMStore.Task.CONTENT_URI;

    @Override
    public ContentValues getValues() {
        return null;
    }

    @Override
    public Uri getContentUri() {
        return mContentUri;
    }
}
