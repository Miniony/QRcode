package com.example.akyllyoy;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class Commands {
    static String ip_address, strPort, strZeroParam, strFirstParam, strSecondParam, strHttp;


    public static void sendJsonObject(Context context, JSONObject allObj, JSONObject obj, JSONArray jsonArray){
        obj = new JSONObject();
        jsonArray = new JSONArray();
        allObj = new JSONObject();

        // IP-Address load from pref_config
        ip_address = Prefconfig1.loadIpPref(context);
        // load data from PrefConfig
        strPort = Prefconfig1.loadPORTPref(context);
        strZeroParam = Prefconfig1.loadZeroParam(context);
        strFirstParam = Prefconfig1.loadFirstParam(context);
        strSecondParam = Prefconfig1.loadSecondParam(context);
        strHttp = Prefconfig1.loadHttpPref(context);
        RequestQueue queue = Volley.newRequestQueue(context);

        String SPHERE_URL = strHttp + "://" + ip_address + strPort + strZeroParam + strFirstParam + strSecondParam;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,SPHERE_URL,allObj, response -> {
            System.out.println(response);
            Log.e("JSON OBJECT","RESPONSE =>" + response);
            },
            error -> Log.e("JSON OBJECT","ERROR =>" + error));
            queue.add(jsObjRequest);
            }
}



