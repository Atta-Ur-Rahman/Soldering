package com.example.seledringtest.fragments.dotsViewFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.seledringtest.R;
import com.example.seledringtest.utilities.GeneralUtilis;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Memo2Fragment extends Fragment {

    private View view;
    @BindView(R.id.tv_memo2_text)
    TextView tvMemoText;
    @BindView(R.id.tv_memo2)
    TextView tvMemo1;

    public Memo2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_memo2, container, false);
        ButterKnife.bind(this, view);

        tvMemoText.setText(GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_2_name", "Memo Name"));

        return view;
    }

}
