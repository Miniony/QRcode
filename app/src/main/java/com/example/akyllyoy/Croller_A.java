package com.example.akyllyoy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Croller_A extends AppCompatActivity {
    Croller analog, analog2;
    TextView txt_analog, txt_analog2;
    private int get_pro, get_pro2;
    private String ip_address, strPort, strZeroParam, strFirstParam, strSecondParam, strHttp;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch stove_state;
    int stove_int, stove1_pro, stove2_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croller);
        // IP-Address load from pref_config
        ip_address = Prefconfig1.loadIpPref(this);
        // load data from PrefConfig
        strPort = Prefconfig1.loadPORTPref(this);
        strZeroParam = Prefconfig1.loadZeroParam(this);
        strFirstParam = Prefconfig1.loadFirstParam(this);
        strSecondParam = Prefconfig1.loadSecondParam(this);
        strHttp = Prefconfig1.loadHttpPref(this);
        // load stove data from PrefConfig
        stove_int = Prefconfig1.loadStateStove(this);
        stove1_pro = Prefconfig1.loadProST1(this);
        stove2_pro = Prefconfig1.loadProST2(this);

        //declare variables
        // stove control analogs
        analog = findViewById(R.id.cooker_analog);
        analog2 = findViewById(R.id.cooker2_analog);
        // TextView (analog names)
        txt_analog = findViewById(R.id.pro_cook1);
        txt_analog2 = findViewById(R.id.pro_cook2);
        stove_state = findViewById(R.id.sw_stove);


        if (stove_int == 1) {
            stove_state.setChecked(true);
            analog.setEnabled(true);
            analog2.setEnabled(true);
        } else if (stove_int == 0) {
            stove_state.setChecked(false);
            analog.setEnabled(false);
            analog2.setEnabled(false);
        }

        analog.setProgress(stove1_pro);
        analog2.setProgress(stove2_pro);


        stove_state.setOnClickListener(v -> {
            if (stove_state.isChecked()) {
                ActivatedStove(getApplicationContext());
                analog.setEnabled(true);
                analog2.setEnabled(true);
                PrefConfig.saveStateStove(getApplicationContext(), 1);

            } else {
                InactivatedStove(getApplicationContext());
                FirstStoveOff(getApplicationContext());
                SecondStoveOff(getApplicationContext());

                analog.setEnabled(false);
                analog2.setEnabled(false);
                PrefConfig.saveStateStove(getApplicationContext(), 0);

                PrefConfig.saveProST1(getApplicationContext(), 1);
                PrefConfig.saveProST2(getApplicationContext(), 1);
            }
        });

        analog.setOnCrollerChangeListener(new com.unity.tabletgorjav.OnCrollerChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                get_pro = progress-1;
                txt_analog.setText(String.valueOf(get_pro));
            }
            @Override
            public void onStartTrackingTouch(Croller croller) { }
            @Override
            public void onStopTrackingTouch(Croller croller) {
                Thread thread = new Thread(() -> {
                    try {
                        URL url = new URL(strHttp + "://" + ip_address + strPort + strZeroParam + strFirstParam + strSecondParam);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept","application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        JSONObject jsonParam = new JSONObject();
                        JSONObject json2 = new JSONObject();
                        JSONArray pin_array2 = new JSONArray();
                        jsonParam.put("command", "cooker1");
                        if (get_pro == 0) {
                            jsonParam.put("action", "0");
                        } else if (get_pro == 1) {
                            jsonParam.put("action", "1");
                        } else if (get_pro == 2) {
                            jsonParam.put("action", "2");
                        } else if (get_pro == 3) {
                            jsonParam.put("action", "3");
                        } else if (get_pro == 4) {
                            jsonParam.put("action", "4");
                        } else if (get_pro == 5) {
                            jsonParam.put("action", "5");
                        } else if (get_pro == 6) {
                            jsonParam.put("action", "6");
                        } else if (get_pro == 7) {
                            jsonParam.put("action", "7");
                        } else if (get_pro == 8) {
                            jsonParam.put("action", "8");
                        }


                        pin_array2.put(jsonParam);
                        json2.put("command", "smart_stove");
                        json2.put("pins", pin_array2);
                        Log.i("JSON", json2.toString());
                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        os.writeBytes(json2.toString());
                        os.flush();
                        os.close();
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            conn.disconnect();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
                PrefConfig.saveProST1(getApplicationContext(), get_pro +1);
                Log.e("STOVE 1 => ", "SET =>" + get_pro);
            }
        });
        analog2.setOnCrollerChangeListener(new com.unity.tabletgorjav.OnCrollerChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(Croller croller, int progress) {
                get_pro2 = progress-1;
                txt_analog2.setText(String.valueOf(get_pro2));
            }
            @Override
            public void onStartTrackingTouch(Croller croller) { }
            @Override
            public void onStopTrackingTouch(Croller croller) {
                Thread thread = new Thread(() -> {
                    try {
                        URL url = new URL(strHttp + "://" + ip_address + strPort + strZeroParam + strFirstParam + strSecondParam);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        conn.setRequestProperty("Accept","application/json");
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        JSONObject jsonParam = new JSONObject();
                        JSONObject json2 = new JSONObject();
                        JSONArray pin_array2 = new JSONArray();
                        jsonParam.put("command", "cooker3");
                        if (get_pro2 == 0) {
                            jsonParam.put("action", "0");
                        } else if (get_pro2 == 1) {
                            jsonParam.put("action", "1");
                        } else if (get_pro2 == 2) {
                            jsonParam.put("action", "2");
                        } else if (get_pro2 == 3) {
                            jsonParam.put("action", "3");
                        } else if (get_pro2 == 4) {
                            jsonParam.put("action", "4");
                        } else if (get_pro2 == 5) {
                            jsonParam.put("action", "5");
                        } else if (get_pro2 == 6) {
                            jsonParam.put("action", "6");
                        } else if (get_pro2 == 7) {
                            jsonParam.put("action", "7");
                        } else if (get_pro2 == 8) {
                            jsonParam.put("action", "8");
                        }

                        pin_array2.put(jsonParam);
                        json2.put("command", "smart_stove");
                        json2.put("pins", pin_array2);
                        Log.i("JSON", json2.toString());
                        DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                        os.writeBytes(json2.toString());
                        os.flush();
                        os.close();
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            conn.disconnect();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
                PrefConfig.saveProST2(getApplicationContext(), get_pro2 +1);
                Log.e("STOVE 1 => ", "SET =>" + get_pro);
            }
        });
    }
}