package takamk2.local.rfm.common;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by takamk2 on 17/02/10.
 * <p>
 * The Edit Fragment of Base Class.
 */

public abstract class BaseEntity {

    public Long mId = null;
    public Long mCreated = null;
    public Long mModified = null;

    protected Uri mContentUri = null;

    public abstract ContentValues getValuesForInsert();

    public abstract ContentValues getValuesForUpdate();

    public abstract void setValuesFromCursor(Cursor cursor);

    public Uri getContentUri() {
        if (mContentUri == null) {
            throw new IllegalArgumentException("mContentUri is null!");
        }
        return mContentUri;
    }

    public Uri getContentUriWithId() {
        if (mContentUri == null) {
            throw new IllegalArgumentException("mContentUri is null!");
        }
        return ContentUris.withAppendedId(mContentUri, getId());
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getId() {
        return mId;
    }

    public void setCreated(Long created) {
        mCreated = created;
    }

    public Long getCreated() {
        return mCreated;
    }

    public void setModified(Long modified) {
        mModified = modified;
    }

    public Long getModified() {
        return mModified;
    }

    public boolean isRegistered() {
        return mId != -1;
    }
}
