package com.example.seledringtest.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seledringtest.R;
import com.example.seledringtest.helpers.SolderingCommunicationService;
import com.example.seledringtest.utilities.Constants;
import com.example.seledringtest.utilities.GeneralUtilis;
import com.example.seledringtest.utilities.Utils;

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
    Knob knob;
    public static int knob_value;
    SwitchCompat switchCompat;
    ImageView ivDotOne,ivDotTwo,ivDotThree,ivDotFour;

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
        knob = view.findViewById(R.id.knob);
        switchCompat = view.findViewById(R.id.switch1);
        ivDotOne = view.findViewById(R.id.iv_dot_one);
        ivDotTwo = view.findViewById(R.id.iv_dot_two);
        ivDotThree = view.findViewById(R.id.iv_dot_three);
        ivDotFour = view.findViewById(R.id.iv_dot_four);


        //updating the power indicator with knob rotating
        knob.setState(7);
        knob.setOnStateChanged(new Knob.OnStateChanged() {
            @Override
            public void onState(int state) {

                knobPowerTV.setText("Power = " + String.valueOf(knobCalculteFunction(state)));

                knob_value = knobCalculteFunction(state);

                GeneralUtilis.putValueInEditor(getActivity()).putInt("knob_value", knob_value).commit();

                Log.d("knob number", String.valueOf(state));

            }
        });
        //end

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(context, "show dark theme", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "show light theme", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //memory dot when we click on that we will store some thing locally
        ivDotOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "what should I do when click on these dot", Toast.LENGTH_LONG).show();
            }
        });
        ivDotTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "what should I do when click on these dot", Toast.LENGTH_LONG).show();
            }
        });
        ivDotThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "what should I do when click on these dot", Toast.LENGTH_LONG).show();
            }
        });
        ivDotFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "what should I do when click on these dot", Toast.LENGTH_LONG).show();
            }
        });

        //end


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
                    String knobPower = "Power = " + power;
                    knob.setState(knobCalculteFunction(power));
                    knobPowerTV.setText(knobPower);
                }
            }

            if (intent.hasExtra(Constants.LOCALM_FUNC_INDICATOR)) {
                HashMap<String, Object> results = (HashMap<String, Object>) intent.getSerializableExtra(Constants.LOCALM_FUNC_INDICATOR);
                boolean result = (Boolean) results.get("result");

                if (result) {
                    String indicator = (String) results.get(Constants.LOCALM_FUNC_INDICATOR);
                    indicator = "" + indicator;
                    funcIndTV.setText(indicator);
                }
            }

            if (intent.hasExtra(Constants.LOCALM_FUNC_LED)) {
                HashMap<String, Object> results = (HashMap<String, Object>) intent.getSerializableExtra(Constants.LOCALM_FUNC_LED);
                boolean result = (Boolean) results.get("result");

                if (result) {
                    String led = (String) results.get(Constants.LOCALM_FUNC_LED);
                    funcLedTV.setText("Functional Led = " + led);


                    Log.d("function", led);
                }
            }
        }
    };

}
