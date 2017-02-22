package takamk2.local.rfm.task;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import takamk2.local.rfm.R;
import takamk2.local.rfm.common.BaseActivity;
import takamk2.local.rfm.common.BaseFragment;

/**
 * Created by takamk2 on 17/02/11.
 * <p>
 * The Edit Fragment of Base Class.
 */

public class ListTaskFragment extends BaseFragment {

    private static final String LOGTAG = ListTaskFragment.class.getSimpleName();

    private Button mBtAdd = null;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_add:
                    onClickAdd();
                    break;
                default:
                    break;
            }
        }
    };

    private TasksLoaderCallbacks mTasksLoaderCallbacks;
    private TasksLoaderCallbacks.Listener mTasksLoaderCallbacksListener
            = new TasksLoaderCallbacks.Listener() {

        @Override
        public void onLoadFinished(Cursor cursor) {
            Log.d(LOGTAG, "onLoadFinished - DEBUG: START count=" + cursor.getCount());
        }

        @Override
        public void onLoaderReset() {
            Log.d(LOGTAG, "onLoaderReset - DEBUG: START");
            // NOP
        }
    };

    public static Fragment getNewInstance() {
        ListTaskFragment fragment = new ListTaskFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTasksLoaderCallbacks =
                new TasksLoaderCallbacks(getActivity(), mTasksLoaderCallbacksListener);
        getLoaderManager().initLoader(0, null, mTasksLoaderCallbacks);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_task, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtAdd = (Button) view.findViewById(R.id.bt_add);
        mBtAdd.setOnClickListener(mOnClickListener);
    }

    private void onClickAdd() {
        Log.d(LOGTAG, "onClickAdd - DEBUG: START");
        Fragment fragment = EditTaskFragment.getNewInstance(1);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(((BaseActivity)getActivity()).getFragmentContainerId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
