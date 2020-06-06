package com.example.hashimoto_app;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * This class is for sending the data inside of the app to the configured server
 */
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

    /**
     * This method sends the actual data of the app to the server via POST
     */
    public static void updateServerData()
    {
        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... voids)
            {
                String query_url = "https://amt-app-studien.virt.uni-oldenburg.de:8443/1/thyreodata";
                try
                {
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    String caString = "-----BEGIN CERTIFICATE-----\n" +
                            "MIIFgjCCBGqgAwIBAgISBGjX+Dc23NluUCbjyVh1ZiM2MA0GCSqGSIb3DQEBCwUA\n" +
                            "MEoxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MSMwIQYDVQQD\n" +
                            "ExpMZXQncyBFbmNyeXB0IEF1dGhvcml0eSBYMzAeFw0yMDA0MjAwNTIxMjBaFw0y\n" +
                            "MDA3MTkwNTIxMjBaMDAxLjAsBgNVBAMTJWFtdC1hcHAtc3R1ZGllbi52aXJ0LnVu\n" +
                            "aS1vbGRlbmJ1cmcuZGUwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDD\n" +
                            "F7faoFFZ3yQlhxbATfIAmmIAj1wv8tR24QJd8DpIr+6vamB8vU1DhirVcVVFajm1\n" +
                            "zTbdgrIr3Z1nXxmsLJ8zEclyFcGx0wJZVRkyXoOjNsVsEvRPcRZJ3f7lZOT0H0PD\n" +
                            "zxnKfCQs2ZA70WiV3PdQwiLFyAdWT0ZlWhxFU4t4xGMGYlTzGOc3WYOs7RKOJN47\n" +
                            "MGeZCPN8vxIuN664VRK8j805Bu+qJa6t/OXyTprqzp8x3zmMLH4jHGEck6RbVErE\n" +
                            "WVi2eqYRssp77K2qhf2lAJR2lJVHQbNW5jrSx7vgtMj2LZxdsSYSNCxwPHLbdVzi\n" +
                            "o5GVeiyv93v6YvjO53hLAgMBAAGjggJ6MIICdjAOBgNVHQ8BAf8EBAMCBaAwHQYD\n" +
                            "VR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMAwGA1UdEwEB/wQCMAAwHQYDVR0O\n" +
                            "BBYEFFz78iPBxZ60S2Yja9iHhLYFipVHMB8GA1UdIwQYMBaAFKhKamMEfd265tE5\n" +
                            "t6ZFZe/zqOyhMG8GCCsGAQUFBwEBBGMwYTAuBggrBgEFBQcwAYYiaHR0cDovL29j\n" +
                            "c3AuaW50LXgzLmxldHNlbmNyeXB0Lm9yZzAvBggrBgEFBQcwAoYjaHR0cDovL2Nl\n" +
                            "cnQuaW50LXgzLmxldHNlbmNyeXB0Lm9yZy8wMAYDVR0RBCkwJ4IlYW10LWFwcC1z\n" +
                            "dHVkaWVuLnZpcnQudW5pLW9sZGVuYnVyZy5kZTBMBgNVHSAERTBDMAgGBmeBDAEC\n" +
                            "ATA3BgsrBgEEAYLfEwEBATAoMCYGCCsGAQUFBwIBFhpodHRwOi8vY3BzLmxldHNl\n" +
                            "bmNyeXB0Lm9yZzCCAQQGCisGAQQB1nkCBAIEgfUEgfIA8AB2AAe3XBvlfWj/8bDG\n" +
                            "HSMVx7rmV3xXlLdq7rxhOhpp06IcAAABcZY/kzEAAAQDAEcwRQIhAOIeiiAWjmsu\n" +
                            "yBYPjNUFP+v80t5MYpWEBboskLBNUFruAiAS+wVF9MjZ4BaS3h50q82H7CpvrIeT\n" +
                            "5JIowpsfE2slAgB2AOcS8rA3fhpi+47JDGGE8ep7N8tWHREmW/Pg80vyQVRuAAAB\n" +
                            "cZY/k2YAAAQDAEcwRQIgCS4gkqoBVW89eTlA/tfQATrkFQARWNVC7hYouXH5JCsC\n" +
                            "IQC05Y1TSr91ZxNrzvoHYlvAzQ0FYLhg9ZU5XuZGSXhhpDANBgkqhkiG9w0BAQsF\n" +
                            "AAOCAQEAdvw0gmMA3ZnoIEYEyQJdKPl2010Cegik705CZRHFEZSbIJeIgOGjA2ue\n" +
                            "N/f3BUWPAELPEHGOIMVcRgl1i7iku2bfEJ14nqCEEPFlcXEsD2r/QkOYLDCbwYyy\n" +
                            "iMS4SiDCIh4Ode915KpeO13l/8k2wUbzxWZ9bFxH5cRo7JcNHUylh6riUi3Pfbx3\n" +
                            "1c9L8wkPUVMZwiFZ7MlitOy4uKuG/J6wYzCCLjAD9ZHL0UE4+hDZOo9LlrlkGU6Y\n" +
                            "TBQ5CpwNcVpphjfCRBncik50So6IuS66GswDmDkdA2Uu/rXoadG5Jgmf4H1rakYh\n" +
                            "mb3AyC16ykihQhWYU7DyL68M7P/IcQ==\n" +
                            "-----END CERTIFICATE-----";
                    InputStream caInput = new ByteArrayInputStream(caString.getBytes());
                    Certificate ca;
                    try
                    {
                        ca = cf.generateCertificate(caInput);
                        System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
                    }
                    finally
                    {
                        caInput.close();
                    }

                    // Create a KeyStore containing our trusted CAs
                    String keyStoreType = KeyStore.getDefaultType();
                    KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                    keyStore.load(null, null);
                    keyStore.setCertificateEntry("ca", ca);

                    // Create a TrustManager that trusts the CAs in our KeyStore
                    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                    tmf.init(keyStore);

                    // Create an SSLContext that uses our TrustManager
                    SSLContext context = SSLContext.getInstance("TLS");
                    context.init(null, tmf.getTrustManagers(), null);

                    URL url = new URL(query_url);
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setSSLSocketFactory(context.getSocketFactory());
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

    /**
     *
     * @return returns all data of the app as json string
     */
    private static String getUserDataAsJson()
    {
        JsonObject sendData = new JsonObject();
        String symptomJson = new Gson().toJson(MainActivity.getDataHolder());
        sendData.add("data", new Gson().fromJson(symptomJson, JsonObject.class));
        return sendData.toString();
    }
}
