package takamk2.local.rfm.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class RFMProvider extends ContentProvider {

    private static final int CODE_TASKS = 1;
    private static final int CODE_TASK_ITEM = 2;
    private static final int CODE_REWARDS = 3;
    private static final int CODE_REWARD_ITEM = 4;

    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        mUriMatcher.addURI(RFMDBStore.AUTHORITY, RFMDBStore.Tasks.PATH, CODE_TASKS);
        mUriMatcher.addURI(RFMDBStore.AUTHORITY, RFMDBStore.Tasks.PATH + "/#", CODE_TASK_ITEM);
        mUriMatcher.addURI(RFMDBStore.AUTHORITY, RFMDBStore.Rewards.PATH, CODE_REWARDS);
        mUriMatcher.addURI(RFMDBStore.AUTHORITY, RFMDBStore.Rewards.PATH + "/#", CODE_REWARD_ITEM);
    }

    private RFMDBHelper mHelper;

    public RFMProvider() {
        // NOP
    }

    @Override
    public boolean onCreate() {
        mHelper = new RFMDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        String table;
        switch (mUriMatcher.match(uri)) {
            case CODE_TASKS:
                table = RFMDBStore.Tasks.TABLE_NAME;
                break;
            case CODE_TASK_ITEM:
                table = RFMDBStore.Tasks.TABLE_NAME;
                selection = RFMDBStore.BaseColumnsForRFM._ID + " = ?";
                selectionArgs = new String[]{ uri.getLastPathSegment() };
                break;
            case CODE_REWARDS:
                table = RFMDBStore.Rewards.TABLE_NAME;
                break;
            case CODE_REWARD_ITEM:
                table = RFMDBStore.Rewards.TABLE_NAME;
                selection = RFMDBStore.BaseColumnsForRFM._ID + " = ?";
                selectionArgs = new String[]{ uri.getLastPathSegment() };
                break;
            default:
                throw new IllegalArgumentException("unknown uri! uri=" + uri.toString());
        }

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(table, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return  cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        String table;
        switch (mUriMatcher.match(uri)) {
            case CODE_TASKS:
                table = RFMDBStore.Tasks.TABLE_NAME;
                break;
            case CODE_REWARDS:
                table = RFMDBStore.Tasks.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("unknown uri! uri=" + uri.toString());
        }

        SQLiteDatabase db = mHelper.getWritableDatabase();
        long newId = db.insert(table, null, values);
        Uri newUri = ContentUris.withAppendedId(uri, newId);

        getContext().getContentResolver().notifyChange(uri, null);

        return newUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        String table;
        switch (mUriMatcher.match(uri)) {
            case CODE_TASKS:
                table = RFMDBStore.Tasks.TABLE_NAME;
                break;
            case CODE_TASK_ITEM:
                table = RFMDBStore.Tasks.TABLE_NAME;
                selection = RFMDBStore.BaseColumnsForRFM._ID + " = ?";
                selectionArgs = new String[]{ uri.getLastPathSegment() };
                break;
            case CODE_REWARDS:
                table = RFMDBStore.Rewards.TABLE_NAME;
                break;
            case CODE_REWARD_ITEM:
                table = RFMDBStore.Rewards.TABLE_NAME;
                selection = RFMDBStore.BaseColumnsForRFM._ID + " = ?";
                selectionArgs = new String[]{ uri.getLastPathSegment() };
                break;
            default:
                throw new IllegalArgumentException("unknown uri! uri=" + uri.toString());
        }

        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.update(table, values, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        String table;
        switch (mUriMatcher.match(uri)) {
            case CODE_TASKS:
                table = RFMDBStore.Tasks.TABLE_NAME;
                break;
            case CODE_TASK_ITEM:
                table = RFMDBStore.Tasks.TABLE_NAME;
                selection = RFMDBStore.BaseColumnsForRFM._ID + " = ?";
                selectionArgs = new String[]{ uri.getLastPathSegment() };
                break;
            case CODE_REWARDS:
                table = RFMDBStore.Rewards.TABLE_NAME;
                break;
            case CODE_REWARD_ITEM:
                table = RFMDBStore.Rewards.TABLE_NAME;
                selection = RFMDBStore.BaseColumnsForRFM._ID + " = ?";
                selectionArgs = new String[]{ uri.getLastPathSegment() };
                break;
            default:
                throw new IllegalArgumentException("unknown uri! uri=" + uri.toString());
        }

        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(table, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public String getType(Uri uri) {
        return "content";
    }
}
