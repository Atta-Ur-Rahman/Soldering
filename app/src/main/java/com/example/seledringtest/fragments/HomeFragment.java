package com.example.seledringtest.fragments;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seledringtest.R;
import com.example.seledringtest.fragments.dotsViewFragment.MemoAdapter;
import com.example.seledringtest.helpers.SolderingCommunicationService;
import com.example.seledringtest.utilities.Constants;
import com.example.seledringtest.utilities.GeneralUtilis;
import com.example.seledringtest.utilities.Utils;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;


import java.util.HashMap;
import java.util.Set;

import it.beppi.knoblibrary.Knob;

public class HomeFragment extends Fragment {
    private final static String TAG = "MainActivity";
    Context context = null;
    Resources res = null;
    TextView knobPowerTV;
    TextView funcIndTV;
    TextView funcLedTV;
    ViewPager viewPager;

    private ViewPagerIndicator viewPagerIndicator;


    ImageView ivLedFunction;
    Knob knob, knobArrow;
    public static int knob_value;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity().getApplicationContext();
        res = getResources();

        knobPowerTV = (TextView) view.findViewById(R.id.knobPowerTV);
        funcIndTV = (TextView) view.findViewById(R.id.funcIndTV);
        funcLedTV = (TextView) view.findViewById(R.id.funcLedTV);
        ivLedFunction = view.findViewById(R.id.iv_function_led);
        knobArrow = view.findViewById(R.id.knob_arrow);
        knobArrow.setClickable(false);
        knob = view.findViewById(R.id.knob);
        knob.setState(7, true);
        knob.setOnStateChanged(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {

                knobPowerTV.setText("Knob Power = " + String.valueOf((state)));

                knobArrow.setState(knobCalculteFunction(state));
                knob_value = knobCalculteFunction(state);

                GeneralUtilis.putValueInEditor(getActivity()).putInt("knob_value", knob_value).commit();

                Log.d("knob number", String.valueOf(state));


            }
        });


        viewPager = view.findViewById(R.id.vp_indicator);
        viewPagerIndicator = view.findViewById(R.id.view_pager_indicator);
        viewPager.setAdapter(new MemoAdapter(getActivity().getSupportFragmentManager()));

        viewPagerIndicator.setupWithViewPager(viewPager);
        viewPagerIndicator.addOnPageChangeListener(mOnPageChangeListener);
        // Start service
        Intent serviceI = new Intent(context, SolderingCommunicationService.class);
        serviceI.putExtra(SolderingCommunicationService.HELLO, true);
        context.startService(serviceI);

        return view;
    }

    private int knobCalculteFunction(int state) {
        int stateReturn = 0;

        if (state == 7) {
            stateReturn = 1;
        } else if (state == 8) {
            stateReturn = 2;
        } else if (state == 9) {
            stateReturn = 3;
        } else if (state == 10) {
            stateReturn = 4;
        } else if (state == 11) {
            stateReturn = 5;
        } else if (state == 12) {
            stateReturn = 6;
        } else if (state == 0) {
            stateReturn = 7;
        } else if (state == 1) {
            stateReturn = 8;
        } else if (state == 2) {
            stateReturn = 9;
        } else if (state == 3) {
            stateReturn = 10;
        } else if (state == 4) {
            stateReturn = 11;
        } else if (state == 5) {
            stateReturn = 12;
        } else if (state == 6) {
            stateReturn = 0;
        }


        return stateReturn;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(localReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register receiver for local messages
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(localReceiver, new IntentFilter(Utils.BROADCAST_LOCAL_MESSAGES));
    }

    BroadcastReceiver localReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Get local messages
            Set<String> iKeys = intent.getExtras().keySet();
            if (Utils.USE_DEBUG) {
                for (String key : iKeys) {
                    Log.d(TAG, "Received local message " + key);
                }
            }

            if (intent.hasExtra(Constants.LOCALM_KNOB_POWER)) {
                HashMap<String, Object> results = (HashMap<String, Object>) intent.getSerializableExtra(Constants.LOCALM_KNOB_POWER);
                boolean result = (Boolean) results.get("result");

                if (result) {
                    int power = (int) results.get(Constants.LOCALM_KNOB_POWER);
                    String knobPower = "Knob Power = " + power;
//                    knob.setState(power);
                    knobPowerTV.setText(knobPower);
                }
            }

            if (intent.hasExtra(Constants.LOCALM_FUNC_INDICATOR)) {
                HashMap<String, Object> results = (HashMap<String, Object>) intent.getSerializableExtra(Constants.LOCALM_FUNC_INDICATOR);
                boolean result = (Boolean) results.get("result");

                if (result) {
                    String indicator = (String) results.get(Constants.LOCALM_FUNC_INDICATOR);
                    indicator = "Functional indicator = " + indicator;
                    funcIndTV.setText(indicator);
                }
            }

            if (intent.hasExtra(Constants.LOCALM_FUNC_LED)) {
                HashMap<String, Object> results = (HashMap<String, Object>) intent.getSerializableExtra(Constants.LOCALM_FUNC_LED);
                boolean result = (Boolean) results.get("result");

                if (result) {
                    String led = (String) results.get(Constants.LOCALM_FUNC_LED);
                    funcLedTV.setText("Functional Led = " + led);


                    if (led.equals("Green")) {
                        ivLedFunction.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/green"));
                    } else if (led.equals("Red")) {
                        ivLedFunction.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/laser_acceso_day"));
                    } else if (led.equals("Gray")) {
                        ivLedFunction.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/laser_spento_day"));
                    }
                    Log.d("function", led);
                }
            }
        }
    };


    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(final int position) {
            Toast.makeText(getActivity(), "Page selected " + position, Toast.LENGTH_SHORT).show();

            if (position == 0) {
                knob.setState(2);
            } else if (position == 1) {

                knob.setState(3);
            } else if (position == 2) {
                knob.setState(5);
            } else if (position == 3) {
                knob.setState(7);
            }
        }

        @Override
        public void onPageScrollStateChanged(final int state) {

        }
    };

}
