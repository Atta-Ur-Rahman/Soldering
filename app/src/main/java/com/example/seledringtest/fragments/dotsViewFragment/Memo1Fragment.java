package com.example.seledringtest.fragments.dotsViewFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seledringtest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Memo1Fragment extends Fragment {

    private View view;

    public Memo1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_memo1, container, false);



        return view;
    }

}
