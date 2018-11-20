package com.example.seledringtest.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seledringtest.R;
import com.example.seledringtest.fragments.HomeFragment;
import com.example.seledringtest.helpers.SolderingCommunicationService;
import com.example.seledringtest.utilities.Constants;
import com.example.seledringtest.utilities.GeneralUtilis;
import com.example.seledringtest.utilities.Utils;

import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GeneralUtilis.withOutBackStackConnectFragmentWithoutAnimination(this,new HomeFragment());

    }





}
