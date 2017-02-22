package takamk2.local.rfm.task;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import takamk2.local.rfm.common.BaseEntity;
import takamk2.local.rfm.db.RFMDBStore;
import takamk2.local.rfm.util.DateUtils;

/**
 * Created by takamk2 on 17/02/10.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class TaskEntity extends BaseEntity {

    private static Uri mContentUri = RFMDBStore.Tasks.CONTENT_URI;

    private String mTitle = null;
    private Long mPrice = null;
    private Boolean mVisible = null;

    @Override
    public ContentValues getValuesForInsert() {
        ContentValues values = new ContentValues();
        values.put(RFMDBStore.Tasks.COLUMN_TITLE, mTitle);
        values.put(RFMDBStore.Tasks.COLUMN_PRICE, mPrice);
        values.put(RFMDBStore.Tasks.COLUMN_VISIBILITY, mVisible);

        long now = DateUtils.getTimestampOfNow();
        values.put(RFMDBStore.Tasks.COLUMN_CREATED, now);
        values.put(RFMDBStore.Tasks.COLUMN_MODIFIED, now);

        return values;
    }

    @Override
    public ContentValues getValuesForUpdate() {
        ContentValues values = getValuesForInsert();
        values.remove(RFMDBStore.Tasks.COLUMN_CREATED);
        return values;
    }

    @Override
    public void setValuesFromCursor(Cursor cursor) {
        // TODO:
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setPrice(Long price) {
        mPrice = price;
    }

    public Long getPrice() {
        return mPrice;
    }

    public Boolean getVisible() {
        return mVisible;
    }

    public void setDummyData() {
        mTitle = "禁酒";
        mPrice = 400L;
        mVisible = true;
    }
}
