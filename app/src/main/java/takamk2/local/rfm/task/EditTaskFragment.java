package takamk2.local.rfm.task;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import takamk2.local.rfm.R;
import takamk2.local.rfm.common.BaseFragment;

/**
 * Created by takamk2 on 17/02/11.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class EditTaskFragment extends BaseFragment {

    private static final String LOGTAG = EditTaskFragment.class.getSimpleName();

    private static final String KEY_ID = "id";

    private long mId = 0L;

    private TextView mTvTitle;

    private TasksLoaderCallbacks mTasksLoaderCallbacks;
    private TasksLoaderCallbacks.Listener mTasksLoaderCallbacksListener
            = new TasksLoaderCallbacks.Listener() {

        @Override
        public void onLoadFinished(Cursor cursor) {
            List<TaskEntity> tasks = TasksLoaderCallbacks.getTasks(cursor);
            TaskEntity task = tasks.get(0);
            if (mTvTitle != null) {
                mTvTitle.setText(task.getTitle());
            }
        }

        @Override
        public void onLoaderReset() {
            Log.d(LOGTAG, "onLoaderReset - DEBUG: START");
            // NOP
        }
    };

    public static Fragment getNewInstance() {
        EditTaskFragment fragment = new EditTaskFragment();
        return fragment;
    }

    public static Fragment getNewInstance(long id) {
        EditTaskFragment fragment = (EditTaskFragment) getNewInstance();
        Bundle args = new Bundle();
        args.putLong(KEY_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mId = args.getLong(KEY_ID, 0L);
        }

        if (mId != 0) {
            mTasksLoaderCallbacks =
                    new TasksLoaderCallbacks(getContext(), mTasksLoaderCallbacksListener, mId);
            getLoaderManager().initLoader(0, null, mTasksLoaderCallbacks);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOGTAG, "onCreateView - DEBUG: START");
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
    }
}
