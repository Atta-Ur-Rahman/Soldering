package com.example.seledringtest.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seledringtest.R;
import com.example.seledringtest.utilities.GeneralUtilis;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MemoryFragment extends Fragment {

    private View view;

    @BindView(R.id.tv_memo_1_name)
    TextView tvMemo1Name;
    @BindView(R.id.tv_memo_1_power)
    TextView tvMemo1Power;
    @BindView(R.id.tv_memo_1_edit)
    TextView tvMemo1Edit;
    @BindView(R.id.tv_memo_2_name)
    TextView tvMemo2Name;
    @BindView(R.id.tv_memo_2_power)
    TextView tvMemo2Power;
    @BindView(R.id.tv_memo_2_edit)
    TextView tvMemo2Edit;
    @BindView(R.id.tv_memo_3_name)
    TextView tvMemo3Name;
    @BindView(R.id.tv_memo_3_power)
    TextView tvMemo3Power;
    @BindView(R.id.tv_memo_3_edit)
    TextView tvMemo3Edit;
    @BindView(R.id.tv_memo_4_name)
    TextView tvMemo4Name;
    @BindView(R.id.tv_memo_4_power)
    TextView tvMemo4Power;
    @BindView(R.id.tv_memo_4_edit)
    TextView tvMemo4Edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_memory, container, false);

        ButterKnife.bind(this, view);


        GeneralUtilis.putValueInEditor(getActivity()).putBoolean("power_handler",false).commit();

        Toast.makeText(getActivity(), String .valueOf(tvMemo1Power.getText().toString()), Toast.LENGTH_SHORT).show();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getValueMemoTextViews();
                setVauleMemoTextViews();

            }
        },500);



        return view;
    }

    private void setVauleMemoTextViews() {

        tvMemo1Name.setText(GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_1_name", "Memo Name "));
        tvMemo1Power.setText(String.valueOf(GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo_1_power", 1)));
        tvMemo2Name.setText(GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_2_name", "Memo Name "));
        tvMemo2Power.setText(String.valueOf(GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo2_power", 3)));
        tvMemo3Name.setText(GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_3_name", "Memo Name "));
        tvMemo3Power.setText(String.valueOf(GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo_3_power", 5)));
        tvMemo4Name.setText(GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_4_name", "Memo Name "));
        tvMemo4Power.setText(String.valueOf(GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo_4_power", 7)));
    }

    private void getValueMemoTextViews() {

        GeneralUtilis.putValueInEditor(getActivity()).putString("memo_1_name", tvMemo1Name.getText().toString()).commit();
        GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_1_power", Integer.parseInt(tvMemo1Power.getText().toString())).commit();

        GeneralUtilis.putValueInEditor(getActivity()).putString("memo_2_name", tvMemo2Name.getText().toString()).commit();
        GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_2_power", Integer.parseInt(tvMemo2Power.getText().toString())).commit();

        GeneralUtilis.putValueInEditor(getActivity()).putString("memo_3_name", tvMemo3Name.getText().toString()).commit();
        GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_3_power", Integer.parseInt(tvMemo3Power.getText().toString())).commit();

        GeneralUtilis.putValueInEditor(getActivity()).putString("memo_4_name", tvMemo4Name.getText().toString()).commit();
        GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_4_power", Integer.parseInt(tvMemo4Power.getText().toString())).commit();


    }
}
