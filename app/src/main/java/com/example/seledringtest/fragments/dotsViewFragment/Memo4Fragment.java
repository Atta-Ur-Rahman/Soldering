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
public class Memo4Fragment extends Fragment {

    private View view;
    @BindView(R.id.tv_memo4_txet)
    TextView tvMemoText;
    @BindView(R.id.tv_memo4)
    TextView tvMemo;

    public Memo4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_memo4, container, false);

        ButterKnife.bind(this, view);

        tvMemoText.setText(GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_4_name","Memo Name"));


        if(GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("night_mode",false)){
            tvMemoText.setTextColor(getResources().getColor(R.color.light_gray));
            tvMemo.setTextColor(getResources().getColor(R.color.light_gray));
        }
        return view;
    }

}
