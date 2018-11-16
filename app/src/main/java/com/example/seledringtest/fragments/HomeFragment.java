package com.example.seledringtest.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seledringtest.R;
import com.example.seledringtest.fragments.dotsViewFragment.MemoAdapter;
import com.example.seledringtest.helpers.SolderingCommunicationService;
import com.example.seledringtest.utilities.Constants;
import com.example.seledringtest.utilities.GeneralUtilis;
import com.example.seledringtest.utilities.Utils;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;


import java.text.SimpleDateFormat;
import java.util.Date;
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

    int i;

    int KnobSate;

    private ViewPagerIndicator viewPagerIndicator;


    ImageView ivLedFunction;
    Knob knob, knobArrow;
    public static int knob_value;


    private String strMemo1Name, strMemo2Name, strMemo3Name, strMemo4Name;
    private int memoPower1, memoPower2, memoPower3, memoPower4;

    private boolean aBooleanKnobState = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity().getApplicationContext();
        res = getResources();

        GeneralUtilis.putValueInEditor(getActivity()).putBoolean("power_handler", true).commit();
        getValueOnMemoryFragment();

        knobPowerTV = (TextView) view.findViewById(R.id.knobPowerTV);
        funcIndTV = (TextView) view.findViewById(R.id.funcIndTV);
        funcLedTV = (TextView) view.findViewById(R.id.funcLedTV);
        ivLedFunction = view.findViewById(R.id.iv_function_led);
        knobArrow = view.findViewById(R.id.knob_arrow);
        knobArrow.setClickable(false);
        knob = view.findViewById(R.id.knob);
        knob.setState(GeneralUtilis.getSharedPreferences(getActivity()).getInt("knob_value", 0));
        knob.setOnStateChanged(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {

                knobPowerTV.setText(String.valueOf((state)));
                KnobSate = state;
                GeneralUtilis.putValueInEditor(getActivity()).putInt("knob_value", state).commit();

            }
        });


        knobPowerTV.setText(String.valueOf(GeneralUtilis.getSharedPreferences(getActivity()).getInt("knob_value", 0)));

        viewPager = view.findViewById(R.id.vp_indicator);
        viewPagerIndicator = view.findViewById(R.id.view_pager_indicator);

        viewPager.setAdapter(new MemoAdapter(getActivity().getSupportFragmentManager()));
        viewPagerIndicator.setupWithViewPager(viewPager);
        viewPagerIndicator.addOnPageChangeListener(mOnPageChangeListener);
        viewPager.setCurrentItem(GeneralUtilis.getSharedPreferences(getActivity()).getInt("indicator_position",0));
        // Start service
        Intent serviceI = new Intent(context, SolderingCommunicationService.class);
        serviceI.putExtra(SolderingCommunicationService.HELLO, true);
        context.startService(serviceI);


        final Handler handler1 = new Handler();
        final Runnable runnable1 = new Runnable() {
            @Override
            public void run() {

                int power = Integer.parseInt(knobPowerTV.getText().toString());

                if (power == memoPower1) {
                    viewPager.setCurrentItem(0);
                } else if (power == memoPower2) {
                    viewPager.setCurrentItem(1);
                } else if (power == memoPower3) {
                    viewPager.setCurrentItem(2);
                } else if (power == memoPower4) {
                    viewPager.setCurrentItem(3);
                }
                handler1.postDelayed(this, 200);


            }
        };
        handler1.postDelayed(runnable1, 1000);


        return view;
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
//                    knobPowerTV.setText(knobPower);
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
//            Toast.makeText(getActivity(), "Page selected " + position, Toast.LENGTH_SHORT).show();


            GeneralUtilis.putValueInEditor(getActivity()).putInt("indicator_position",position).commit();

            if (position == 0) {
                knob.setState(memoPower1);
            } else if (position == 1) {
                knob.setState(memoPower2);
            } else if (position == 2) {
                knob.setState(memoPower3);
            } else if (position == 3) {
                knob.setState(memoPower4);
            }
        }

        @Override
        public void onPageScrollStateChanged(final int state) {

        }
    };

    private void getValueOnMemoryFragment() {


        strMemo1Name = GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_1_name", "Memo Name ");
        memoPower1 = GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo1_power", 0);
        strMemo2Name = GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_2_name", "Memo Name ");
        memoPower2 = GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo_2_power", 0);
        strMemo3Name = GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_3_name", "Memo Name ");
        memoPower3 = GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo_3_power", 0);
        strMemo4Name = GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_4_name", "Memo Name ");
        memoPower4 = GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo_4_power", 0);

    }

}
