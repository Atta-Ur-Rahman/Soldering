package com.example.seledringtest.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.seledringtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MemoryFragment extends Fragment {

    private  View view;

    @BindView(R.id.tv_memo_1_name)TextView tvMemo1Name;
    @BindView(R.id.tv_memo_1_power)TextView tvMemo1Power;
    @BindView(R.id.tv_memo_1_edit)TextView tvMemo1Edit;
    @BindView(R.id.tv_memo_2_name)TextView tvMemo2Name;
    @BindView(R.id.tv_memo_2_power)TextView tvMemo2Power;
    @BindView(R.id.tv_memo_2_edit)TextView tvMemo2Edit;
    @BindView(R.id.tv_memo_3_name)TextView tvMemo3Name;
    @BindView(R.id.tv_memo_3_power)TextView tvMemo3Power;
    @BindView(R.id.tv_memo_3_edit)TextView tvMemo3Edit;
    @BindView(R.id.tv_memo_4_name)TextView tvMem41Name;
    @BindView(R.id.tv_memo_4_power)TextView tvMemo4Power;
    @BindView(R.id.tv_memo_4_edit)TextView tvMemo4Edit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_memory, container, false);

        ButterKnife.bind(this,view);









        return view;
    }
}
