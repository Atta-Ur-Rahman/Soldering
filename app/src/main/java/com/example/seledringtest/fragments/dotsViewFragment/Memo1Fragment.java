package com.example.seledringtest.fragments.dotsViewFragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seledringtest.R;
import com.example.seledringtest.utilities.GeneralUtilis;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Memo1Fragment extends Fragment {

    private View view;
    @BindView(R.id.tv_memo1_txet)
    TextView tvMemoText;
    @BindView(R.id.tv_memo1)
    TextView tvMemo;

    private String m_Text = "";


    public Memo1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_memo1, container, false);
        ButterKnife.bind(this, view);
        tvMemoText.setText(GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_1_name", "Memo Name"));


        if(GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("night_mode",false)){
            tvMemoText.setTextColor(getResources().getColor(R.color.light_gray));
            tvMemo.setTextColor(getResources().getColor(R.color.light_gray));
        }

        return view;
    }


}
