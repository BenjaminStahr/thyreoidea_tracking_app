package com.example.hashimoto_app;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.hashimoto_app.backend.DataHolder;
import com.example.hashimoto_app.backend.FileManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NetworkWorker extends Worker
{
    public NetworkWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {
        updateServerData();
        return Result.success();
    }

    public static void updateServerData()
    {
        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... voids)
            {
                //return initServerData(symptomData);
                String query_url = "http://srvgvm33.offis.uni-oldenburg.de:8080/1/thyreodata";
                try
                {
                    URL url = new URL(query_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    OutputStream os = conn.getOutputStream();
                    os.write(getUserDataAsJson().getBytes());
                    os.close();
                    BufferedReader br = null;
                    String strCurrentLine = "";
                    if (conn.getResponseCode() == 201)
                    {
                        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while ((strCurrentLine = br.readLine()) != null)
                        {
                            System.out.println(strCurrentLine);
                        }
                    }
                    conn.disconnect();
                    return strCurrentLine;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    return "unsuccessful update to server";
                }
            }
        }.execute();
    }
    private static String getUserDataAsJson()
    {
        JsonObject sendData = new JsonObject();
        String symptomJson = new Gson().toJson(MainActivity.getDataHolder());
        sendData.add("data", new Gson().fromJson(symptomJson, JsonObject.class));
        return sendData.toString();
    }
}
