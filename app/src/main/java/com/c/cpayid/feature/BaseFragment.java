package com.c.cpayid.feature;

import android.view.View;

import androidx.fragment.app.Fragment;

/**
 * DICKY <akbar.attijani@gmail.com>
 */

public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();
        settingLayout(getView());
    }

    public void settingLayout(View view) {}
}
