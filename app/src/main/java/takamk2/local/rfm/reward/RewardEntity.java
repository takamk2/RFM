package takamk2.local.rfm.reward;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbInterface;

import takamk2.local.rfm.RFMApplication;
import takamk2.local.rfm.common.BaseEntity;
import takamk2.local.rfm.db.RFMDBStore;
import takamk2.local.rfm.util.DataUtil;
import takamk2.local.rfm.util.DateUtils;
import timber.log.Timber;

/**
 * Created by takamk2 on 17/02/18.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class RewardEntity extends BaseEntity {

    private String mName = null;
    private Long mPrice = null;
    private String mDescription = null;
    private String mUrl = null;
    private String mImageName = null;
    private Long mCompleted = null;
    private Priority mPriority = Priority.MIDDLE;
    private int name;
    private Object price;
    private Object remainingPrice;
    private int url;
    private Priority priority;

    /* ------------------------------------------------------------------------------------------ */
    @Override
    public ContentValues getValuesForInsert() {
        ContentValues values = new ContentValues();
        values.put(RFMDBStore.Rewards.COLUMN_NAME, mName);
        values.put(RFMDBStore.Rewards.COLUMN_PRICE, mPrice);
        values.put(RFMDBStore.Rewards.COLUMN_DESCRIPTION, mDescription);
        values.put(RFMDBStore.Rewards.COLUMN_URL, mUrl);
        values.put(RFMDBStore.Rewards.COLUMN_IMAGE_NAME, mImageName);
        values.put(RFMDBStore.Rewards.COLUMN_COMPLETED, mCompleted);
        values.put(RFMDBStore.Rewards.COLUMN_PRIORITY, mPriority.getValue());

        long now = DateUtils.getTimestampOfNow();
        values.put(RFMDBStore.Rewards.COLUMN_CREATED, now);
        values.put(RFMDBStore.Rewards.COLUMN_MODIFIED, now);

        return values;
    }

    @Override
    public ContentValues getValuesForUpdate() {
        // Todo:
        return null;
    }

    @Override
    public void setValuesFromCursor(Cursor cursor) {
        if (cursor == null) {
            Timber.d("setValuesFromCursor - DEBUG: cursor is null");
            return;
        }
        cursor.moveToFirst();
        mName = cursor.getString(cursor.getColumnIndex(RFMDBStore.Rewards.COLUMN_NAME));
        mPrice = cursor.getLong(cursor.getColumnIndex(RFMDBStore.Rewards.COLUMN_PRICE));
        if (mPrice <= 0) mPrice = null;
        mDescription = cursor.getString(cursor.getColumnIndex(RFMDBStore.Rewards.COLUMN_DESCRIPTION));
        mUrl = cursor.getString(cursor.getColumnIndex(RFMDBStore.Rewards.COLUMN_URL));
        mImageName = cursor.getString(cursor.getColumnIndex(RFMDBStore.Rewards.COLUMN_IMAGE_NAME));
        mCompleted = cursor.getLong(cursor.getColumnIndex(RFMDBStore.Rewards.COLUMN_COMPLETED));
        if (mCompleted <= 0) mCompleted = null;
        mPriority = Priority.get(cursor.getInt(cursor.getColumnIndex(RFMDBStore.Rewards.COLUMN_PRIORITY)));
    }

    public String getName() {
        return mName;
    }

    public Long getPrice() {
        return mPrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public Long getRemainingPrice() {
        return null;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getImageName() {
        return mImageName;
    }

    public Priority getPriority() {
        return mPriority;
    }

    public Long getCompleted() {
        return mCompleted;
    }

    public Bitmap getImage(Context context, RFMApplication application) {

        if (mImageName == null) {
            return null;
        }

        Bitmap bitmap = application.getBitmapCache().getBitmap(mImageName);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeFile(DataUtil.getPictureDirectory(context) + "/" + mImageName);
        }

        return bitmap;
    }

    /**
     * For DEBUG
     */
    public void setDummyData() {
        mName = "Philips 43型ワイド液晶ディスプレイ";
        mPrice = 64788L;
        mDescription = "MultiView 4K で 4 つのシステムを 1 つの画面に";
        mUrl = "https://www.amazon.co.jp/dp/B01D9FP20A?tag=s02a310049-22&th=1";
        mImageName = "dummy.jpg";
        mCompleted = null;
        mPriority = Priority.HIGH;
    }

    /* ------------------------------------------------------------------------------------------ */
    public enum Priority {
        PENDING(0, "Pending"),
        LOW(1, "Low"),
        MIDDLE(2, "Middle"),
        HIGH(3, "high");

        private final int mValue;
        private final String mName;

        public static Priority get(int value) {
            for (Priority priority : Priority.values()) {
                if (value == priority.getValue()) {
                    return priority;
                }
            }
            Timber.w("do not match!. return MIDDLE as a default value");
            return MIDDLE;
        }

        Priority(int value, String name) {
            mValue = value;
            mName = name;
        }

        public int getValue() {
            return mValue;
        }

        public String getName() {
            return mName;
        }
    }
}
