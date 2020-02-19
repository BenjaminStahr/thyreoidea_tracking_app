package com.example.hashimoto_app;

import android.content.Context;

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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public String updateServerData()
    {
        String query_url = "http://srvgvm33.offis.uni-oldenburg.de:8080/1/thyreodata/";
        query_url += new Gson().toJson(new Gson().fromJson(FileManager.getFileAsString("userData", getApplicationContext()), DataHolder.class)
                .getUSER_ID());
        try
        {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("PUT");
            OutputStream os = conn.getOutputStream();
            //String s = getUserDataAsJson();
            os.write(getUserDataAsJson().getBytes());
            os.close();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = in.toString();
            //JSONObject myResponse = new JSONObject(result);
            in.close();
            conn.disconnect();
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "unsuccessful initialization of connection to server";
        }
    }
    public String getUserDataAsJson()
    {
        DataHolder dataHolder = new Gson().fromJson(FileManager.getFileAsString("userData", getApplicationContext()), DataHolder.class);
        JsonObject sendData = new JsonObject();
        String idJson = new Gson().toJson(dataHolder.getUSER_ID());
        String symptomJson = new Gson().toJson(dataHolder.getSymptomData());
        sendData.add("id", new Gson().fromJson(idJson, JsonPrimitive.class));
        sendData.add("symptomData", new Gson().fromJson(symptomJson, JsonArray.class));
        return sendData.toString();
    }
}
