package takamk2.local.rfm.reward;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import takamk2.local.rfm.R;
import takamk2.local.rfm.common.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditRewardFragment extends BaseFragment {

    public static Fragment getNewInstance() {
        EditRewardFragment fragment = new EditRewardFragment();
        return fragment;
    }

    public EditRewardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_reward, container, false);
    }

}
