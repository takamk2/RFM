package takamk2.local.rfm.common;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

/**
 * Created by takamk2 on 17/02/10.
 * <p>
 * The Edit Fragment of Base Class.
 */

public abstract class BaseEntity {

    public long mId = -1;
    public long mCreated = -1;
    public long mModified = -1;

    protected Uri mContentUri;

    public abstract ContentValues getValues();

    public abstract Uri getContentUri();

    public Uri getContentUriWithId() {
        return ContentUris.withAppendedId(mContentUri, getId());
    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public void setCreated(long created) {
        mCreated = created;
    }

    public long getCreated() {
        return mCreated;
    }

    public void setModified(long modified) {
        mModified = modified;
    }

    public long getModified() {
        return mModified;
    }
}
