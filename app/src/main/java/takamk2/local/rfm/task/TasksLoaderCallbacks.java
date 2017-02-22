package takamk2.local.rfm.task;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import takamk2.local.rfm.db.RFMDBStore;

/**
 * Created by takamk2 on 17/02/11.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class TasksLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String KEY_SELECTION = "selection";
    private static final String KEY_SELECTION_ARGS = "selection_args";
    private static final String KEY_SORT_ORDER = "sort_order";

    private final Context mContext;

    private long mId = 0;

    private Listener mListener;

    public static List<TaskEntity> getTasks(Cursor cursor) {

        List<TaskEntity> tasks = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                TaskEntity task = new TaskEntity();
                task.setTitle(cursor.getString(cursor.getColumnIndex(RFMDBStore.Tasks.COLUMN_TITLE)));
                tasks.add(task);
            }
        }

        return tasks;
    }

    public interface Listener {
        void onLoadFinished(Cursor cursor);
        void onLoaderReset();
    }

    public TasksLoaderCallbacks(Context context, Listener listener) {
        mContext = context;
        mListener = listener;
    }

    public TasksLoaderCallbacks(Context context, Listener listener, long id) {
        this(context, listener);
        mId = id;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri uri = RFMDBStore.Tasks.CONTENT_URI;
        if (mId != 0) {
            uri = ContentUris.withAppendedId(uri, mId);
        }
        String[] projection = RFMDBStore.Tasks.PROJECTION_ALL;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        if (bundle != null) {
            selection = bundle.getString(KEY_SELECTION);
            selectionArgs = bundle.getStringArray(KEY_SELECTION_ARGS);
            sortOrder = bundle.getString(KEY_SORT_ORDER);
        }
        Loader<Cursor> loader =
                new CursorLoader(mContext, uri, projection, selection, selectionArgs, sortOrder);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mListener.onLoadFinished(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListener.onLoaderReset();
    }
}
