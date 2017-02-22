package takamk2.local.rfm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import takamk2.local.rfm.common.BaseActivity;
import takamk2.local.rfm.db.RFMDBStore;
import takamk2.local.rfm.reward.ListRewardFragment;
import takamk2.local.rfm.task.TaskEntity;

public class MainActivity extends BaseActivity {

    private static final String LOGTAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = ListRewardFragment.getNewInstance();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(getFragmentContainerId(), fragment);
        fragmentTransaction.commit();

        TaskEntity task = new TaskEntity();
        task.setDummyData();
        getContentResolver().insert(RFMDBStore.Tasks.CONTENT_URI, task.getValuesForInsert());
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.fragment_container;
    }
}
