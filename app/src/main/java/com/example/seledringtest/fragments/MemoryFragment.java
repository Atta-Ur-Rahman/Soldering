package com.example.seledringtest.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seledringtest.R;
import com.example.seledringtest.utilities.GeneralUtilis;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MemoryFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

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


    @BindView(R.id.sw_memo1)
    SwitchCompat swMemo1;
    @BindView(R.id.sw_memo2)
    SwitchCompat swMemo2;
    @BindView(R.id.sw_memo3)
    SwitchCompat swMemo3;
    @BindView(R.id.sw_memo4)
    SwitchCompat swMemo4;

    private Dialog editDailog;
    private String strName, strPower;
    private int power;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_memory, container, false);

        ButterKnife.bind(this, view);

        tvMemo1Edit.setOnClickListener(this);
        tvMemo2Edit.setOnClickListener(this);
        tvMemo3Edit.setOnClickListener(this);
        tvMemo4Edit.setOnClickListener(this);


        swMemo1.setOnCheckedChangeListener(this);
        swMemo2.setOnCheckedChangeListener(this);
        swMemo3.setOnCheckedChangeListener(this);
        swMemo4.setOnCheckedChangeListener(this);


        swMemo1.setChecked(GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("sw_memo_1", false));
        swMemo2.setChecked(GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("sw_memo_2", false));
        swMemo3.setChecked(GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("sw_memo_3", false));
        swMemo4.setChecked(GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("sw_memo_4", false));


        setVauleMemoTextViews();
        getValueMemoTextViews();

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_memo_1_edit:
                memoEditDailog(tvMemo1Name, tvMemo1Power);
                break;
            case R.id.tv_memo_2_edit:
                memoEditDailog(tvMemo2Name, tvMemo2Power);
                break;
            case R.id.tv_memo_3_edit:
                memoEditDailog(tvMemo3Name, tvMemo3Power);
                break;
            case R.id.tv_memo_4_edit:
                memoEditDailog(tvMemo4Name, tvMemo4Power);
                break;
        }
    }

    private void memoEditDailog(final TextView tvName, final TextView tvPower) {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View dialogView = getLayoutInflater().inflate(R.layout.memo_edit_layout, null);
        dialogView.setBackgroundResource(android.R.color.transparent);
        dialogBuilder.setView(dialogView);
        editDailog = dialogBuilder.create();


        ImageView ivCross = dialogView.findViewById(R.id.iv_edit_cross);
        final EditText etName = dialogView.findViewById(R.id.et_memo_name);
        final EditText etPower = dialogView.findViewById(R.id.et_power);
        Button btnSave = dialogView.findViewById(R.id.btn_save);

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDailog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName = etName.getText().toString();
                strPower = etPower.getText().toString();

                if (strName.equals("")) {
                    etName.setError("enter memo name");
                } else if (strPower.equals("")) {
                    etPower.setError("enter memo power");
                } else {
                    power = Integer.parseInt(strPower);

                    if (power <= 12) {

                        tvName.setText(strName);
                        tvPower.setText(strPower);
                        getValueMemoTextViews();
                        editDailog.dismiss();

                    } else {
                        etPower.setError("your value is greater than 12");
                    }
                }

            }
        });


        editDailog.show();

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sw_memo1:
                GeneralUtilis.putValueInEditor(getActivity()).putBoolean("sw_memo_1", isChecked).commit();
                if (isChecked) {
                    GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_view_pager", 0).commit();
                }else {
                    GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_view_pager", 1).commit();
                }
                break;
            case R.id.sw_memo2:

                GeneralUtilis.putValueInEditor(getActivity()).putBoolean("sw_memo_2", isChecked).commit();
                if (isChecked) {
                    GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_view_pager", 1).commit();
                }else{
                    GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_view_pager", 2).commit();
                }
                break;
            case R.id.sw_memo3:
                GeneralUtilis.putValueInEditor(getActivity()).putBoolean("sw_memo_3", isChecked).commit();
                if (isChecked) {
                    GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_view_pager", 2).commit();
                }else {
                    GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_view_pager", 3).commit();
                }
                break;
            case R.id.sw_memo4:
                GeneralUtilis.putValueInEditor(getActivity()).putBoolean("sw_memo_4", isChecked).commit();
                if (isChecked) {
                    GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_view_pager", 3).commit();
                }else {
                    GeneralUtilis.putValueInEditor(getActivity()).putInt("memo_view_pager", 4).commit();
                }
                break;

        }

    }
}
