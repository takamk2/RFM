package takamk2.local.rfm.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by takamk2 on 17/02/10.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class RFMDBStore {

    public static final String AUTHORITY = "takamk2.local.rfm.rfmprovider";

    public static abstract class BaseColumnsForRFM implements BaseColumns {

        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_CREATED = "created";
        public static final String COLUMN_MODIFIED = "modified";
    }

    public static abstract class Rewards extends BaseColumnsForRFM {

        public static final String PATH = "rewards";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

        public static final String TABLE_NAME = PATH;

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_IMAGE_NAME = "image_name";
        public static final String COLUMN_COMPLETED = "completed";
        public static final String COLUMN_PRIORITY = "priority";

        public static final String CREATE_TABLE =
                "create table " + TABLE_NAME + " ( " +
                        COLUMN_ID + " integer primary key autoincrement, " +
                        COLUMN_NAME + " text not null, " +
                        COLUMN_PRICE + " integer not null, " +
                        COLUMN_DESCRIPTION + " text, " +
                        COLUMN_URL + " text, " +
                        COLUMN_IMAGE_NAME + " text, " +
                        COLUMN_COMPLETED + " integer, " +
                        COLUMN_PRIORITY + " integer not null, " +
                        COLUMN_CREATED + " integer not null, " +
                        COLUMN_MODIFIED + " integer not null " +
                        ");";
        public static final String DROP_TABLE =
                "drop table if exists " + TABLE_NAME;

        public static final String[] PROJECTION_ALL = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_PRICE,
                COLUMN_DESCRIPTION,
                COLUMN_URL,
                COLUMN_IMAGE_NAME,
                COLUMN_COMPLETED,
                COLUMN_PRIORITY,
                COLUMN_CREATED,
                COLUMN_MODIFIED
        };
    }

    public static abstract class Tasks extends BaseColumnsForRFM {

        public static final String PATH = "tasks";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

        public static final String TABLE_NAME = PATH;

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_VISIBILITY = "visibility";

        public static final String CREATE_TABLE =
                "create table " + TABLE_NAME + " ( " +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_TITLE + " text not null, " +
                COLUMN_PRICE + " integer not null, " +
                COLUMN_VISIBILITY + " integer not null, " +
                COLUMN_CREATED + " integer not null, " +
                COLUMN_MODIFIED + " integer not null " +
                ");";
        public static final String DROP_TABLE =
                "drop table if exists " + TABLE_NAME;

        public static final String[] PROJECTION_ALL = {
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_PRICE,
                COLUMN_VISIBILITY,
                COLUMN_CREATED,
                COLUMN_MODIFIED
        };
    }
}
