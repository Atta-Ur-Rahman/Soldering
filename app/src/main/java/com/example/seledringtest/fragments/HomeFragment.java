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
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
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

import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.beppi.knoblibrary.Knob;

public class HomeFragment extends Fragment {
    private final static String TAG = "MainActivity";
    Context context = null;
    Resources res = null;

    @BindView(R.id.tv_knob_power)
    TextView knobPowerTV;
    @BindView(R.id.tv_func_led)
    TextView funcLedTV;
    @BindView(R.id.vp_memo)
    ViewPager viewPager;
    @BindView(R.id.view_pager_indicator)
    ViewPagerIndicator viewPagerIndicator;
    @BindView(R.id.iv_function_led)
    ImageView ivLedFunction;
    @BindView(R.id.knob)
    Knob knob;
    @BindView(R.id.tv_no_memory)
    TextView tvNoMemory;

    @BindView(R.id.image_arrow)ImageView ivArrow;

    @BindView(R.id.sw_night_mod)
    SwitchCompat swNightMode;

    private int KnobSate;
    private MemoAdapter adapter;

    private String strMemo1Name, strMemo2Name, strMemo3Name, strMemo4Name;
    private int memoPower1, memoPower2, memoPower3, memoPower4;

    private boolean aBooleanKnobState = true;

    boolean aBooleanMemo1, aBooleanMemo2, aBooleanMemo3, aBooleanMemo4, aBooleanNightMode;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        aBooleanNightMode = GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("night_mode", false);
        if (aBooleanNightMode) {
            view = inflater.inflate(R.layout.fragment_home_night, container, false);

        } else {
            view = inflater.inflate(R.layout.fragment_home, container, false);
        }


        ButterKnife.bind(this, view);

        context = getActivity().getApplicationContext();
        res = getResources();




        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivArrow.animate().rotation(10).start();
            }
        });



        if (GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("sw_memo_1", false)) {
            tvNoMemory.setVisibility(View.VISIBLE);
            tvNoMemory.setText("No Memory Present");

        }

        swNightMode.setChecked(aBooleanNightMode);

        swNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GeneralUtilis.putValueInEditor(getActivity()).putBoolean("night_mode", isChecked).commit();
                Refresh(false);
            }
        });

////refresh the hime fragment
        if (GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("refresh", false)) {
            GeneralUtilis.putValueInEditor(getActivity()).putBoolean("refresh", false).commit();
            Refresh(true);
        }

        getValueOnMemoryFragment();
        getBoleanMemoryFragment();


        knob.setState(GeneralUtilis.getSharedPreferences(getActivity()).getInt("knob_value", 0));
        knob.setOnStateChanged(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {

                knobPowerTV.setText(String.valueOf((state)));
                KnobSate = state;
                GeneralUtilis.putValueInEditor(getActivity()).putInt("knob_value", state).commit();

                ivArrow.animate().rotation(state*20).start();

            }
        });


        adapter = new MemoAdapter(getActivity(), getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPagerIndicator.setupWithViewPager(viewPager);
        viewPagerIndicator.addOnPageChangeListener(mOnPageChangeListener);
        viewPager.setCurrentItem(GeneralUtilis.getSharedPreferences(getActivity()).getInt("view_pager_position", 0), true);
        knobPowerTV.setText(String.valueOf(GeneralUtilis.getSharedPreferences(getActivity()).getInt("knob_value", 0)));

        // Start service
        Intent serviceI = new Intent(context, SolderingCommunicationService.class);
        serviceI.putExtra(SolderingCommunicationService.HELLO, true);
        context.startService(serviceI);


        knobPowerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


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
                handler1.postDelayed(this, 100);
            }
        };
        handler1.postDelayed(runnable1, 300);


        return view;
    }

    private void Refresh(boolean with_out_animinatio) {


        getActivity().finish();
        if (with_out_animinatio) {
            getActivity().overridePendingTransition(R.anim.scale_out, R.anim.scale_in);
        }
        getActivity().startActivity(getActivity().getIntent());
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

                }
            }

            if (intent.hasExtra(Constants.LOCALM_FUNC_LED)) {
                HashMap<String, Object> results = (HashMap<String, Object>) intent.getSerializableExtra(Constants.LOCALM_FUNC_LED);
                boolean result = (Boolean) results.get("result");

                if (result) {
                    String led = (String) results.get(Constants.LOCALM_FUNC_LED);
                    funcLedTV.setText("Functional Led = " + led);

                    aBooleanNightMode = GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("night_mode", false);

                    if (led.equals("Green")) {
                        if (aBooleanNightMode) {
                            ivLedFunction.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/sun"));
                        } else {
                            ivLedFunction.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/green"));
                        }
                    }
                    if (led.equals("Red")) {
                        if (aBooleanNightMode) {
                            ivLedFunction.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/laser_acceso_night"));
                        } else {
                            ivLedFunction.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/laser_acceso_day"));
                        }
                    }
                    if (led.equals("Gray")) {
                        if (aBooleanNightMode) {
                            ivLedFunction.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/laser_spento_night"));
                        } else {
                            ivLedFunction.setImageURI(Uri.parse("android.resource://" + context.getPackageName() + "/drawable/laser_spento_day"));
                        }
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

            GeneralUtilis.putValueInEditor(getActivity()).putInt("view_pager_position", position).commit();

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
        memoPower1 = GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo_1_power", 1);
        strMemo2Name = GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_2_name", "Memo Name ");
        memoPower2 = GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo_2_power", 2);
        strMemo3Name = GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_3_name", "Memo Name ");
        memoPower3 = GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo_3_power", 3);
        strMemo4Name = GeneralUtilis.getSharedPreferences(getActivity()).getString("memo_4_name", "Memo Name ");
        memoPower4 = GeneralUtilis.getSharedPreferences(getActivity()).getInt("memo_4_power", 4);

    }

    private void getBoleanMemoryFragment() {

        aBooleanMemo1 = GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("memo1", true);
        aBooleanMemo2 = GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("memo2", true);
        aBooleanMemo3 = GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("memo3", true);
        aBooleanMemo4 = GeneralUtilis.getSharedPreferences(getActivity()).getBoolean("memo4", true);
    }


}
