package com.example.seledringtest.helpers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.seledringtest.activities.MainActivity;
import com.example.seledringtest.activities.NavigationActivity;
import com.example.seledringtest.fragments.HomeFragment;
import com.example.seledringtest.utilities.Constants;
import com.example.seledringtest.utilities.GeneralUtilis;
import com.example.seledringtest.utilities.Utils;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SolderingCommunicationService extends Service {

    private final static String TAG = "SolderingCommService";

    public final static String HELLO = "HELLO";
    public final static String SEND_SET_POWER = "SEND_SET_POWER";

    Context context;
    final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public SolderingCommunicationService getService() {
            // Return this instance of service so clients can call public methods
            return SolderingCommunicationService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(HELLO)) {
            sendKnobPower();
            sendFunctionalIndicator();
            sendFunctionalLed();
        }

        if (intent.hasExtra(SEND_SET_POWER)) {
            Log.d(TAG, "will send power to soldering torch");
        }

        return Service.START_STICKY;
    }


    private void sendKnobPower() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        KnobPowerRunnable knobPowerRunnable = new KnobPowerRunnable();
        executorService.submit(knobPowerRunnable);
    }

    private void sendFunctionalIndicator() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FunctionalIndicatorRunnable functionalIndicatorRunnable = new FunctionalIndicatorRunnable();
        executorService.submit(functionalIndicatorRunnable);

    }

    private void sendFunctionalLed() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FunctionalLedRunnable functionalLedRunnable = new FunctionalLedRunnable();
        executorService.submit(functionalLedRunnable);

    }

    public class KnobPowerRunnable implements Runnable {

        @Override
        public void run() {
            Random rn = new Random();
            int min =  GeneralUtilis.getSharedPreferences(getApplicationContext()).getInt("knob_value",0); ;
            int max = 12;           //da knob values ba dlta set ko bs te ye sirf dlta rawala bya ba nor kar ze okam
            while (true) {
                int power = rn.nextInt(max - min + 1);

                // Send message
                HashMap<String, Object> results = createResultsMap(true, 0, "Ok");
                results.put(Constants.LOCALM_KNOB_POWER, power);
                sendLocalBroadcastMessage(context, Constants.LOCALM_KNOB_POWER, results, TAG + "." + "powerKnob");


                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "KnobPowerRunnable: error", e);
                }
            }
        }
    }

    public class FunctionalIndicatorRunnable implements Runnable {

        @Override
        public void run() {

            String[] indicators = new String[5];
            indicators[0] = "Laser OK";
            indicators[1] = "Laser Not working";
            indicators[2] = "Error";
            indicators[3] = "Ready";
            indicators[4] = "Warming up";

            while (true) {
                int rnd = new Random().nextInt(indicators.length);
                String indicator = indicators[rnd];


                // Send message
                HashMap<String, Object> results = createResultsMap(true, 0, "Ok");
                results.put(Constants.LOCALM_FUNC_INDICATOR, indicator);
                sendLocalBroadcastMessage(context, Constants.LOCALM_FUNC_INDICATOR, results, TAG + "." + "funcIndicator");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "FunctionalIndicatorRunnable: error", e);
                }
            }
        }
    }

    public class FunctionalLedRunnable implements Runnable {

        @Override
        public void run() {

            String[] leds = new String[3];
            leds[0] = "Gray";
            leds[1] = "Green";
            leds[2] = "Red";


            while (true) {
                int rnd = new Random().nextInt(leds.length);
                String led = leds[rnd];

                // Send message
                HashMap<String, Object> results = createResultsMap(true, 0, "Ok");
                results.put(Constants.LOCALM_FUNC_LED, led);
                sendLocalBroadcastMessage(context, Constants.LOCALM_FUNC_LED, results, TAG + "." + "funcLed");


                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "FunctionalLedRunnable: error", e);
                }
            }
        }
    }

    /**
     * @param context
     * @param key
     * @param results
     * @param methodName
     */
    private static void sendLocalBroadcastMessage(Context context, String key, HashMap<String, Object> results, String methodName) {
        if (Utils.USE_DEBUG) {
            Log.i(TAG, "Sending local message with key " + key);
        }
        results.put("methodName", methodName);
        Intent intent = new Intent(Utils.BROADCAST_LOCAL_MESSAGES);
        intent.putExtra(key, results);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    /**
     * @param result
     * @param returnCode
     * @param resultDetail
     * @return
     */
    private static HashMap<String, Object> createResultsMap(boolean result, int returnCode, String resultDetail) {
        HashMap<String, Object> results = new HashMap<String, Object>();
        results.put("result", result);
        results.put("returnCode", returnCode);
        results.put("resultDetail", resultDetail);

        return results;
    }
}
