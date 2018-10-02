package vn.edu.fpt.kidapp.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import vn.edu.fpt.kidapp.JsonModel.JSONLanguage;

public class TranslateUtil {
    private static final String TAG = TranslateUtil.class.getSimpleName();
    static String subscriptionKey = "ad4c763eda3c413b91ddc250e5b1a393";
    static String host = "https://api.cognitive.microsofttranslator.com";
    static String path = "/translate?api-version=3.0";
    static String params = "&to=en&to=vi";
    static String text = "Hello world!";

    public static class RequestBody {
        String Text;
        public RequestBody(String text) {
            this.Text = text;
        }
    }

    public static void translateEnglishToVietnamese(List<RequestBody> objList, Context context) {
        Post(objList, context);
    }

    public static void Post (final List<RequestBody> objList, final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(host + path + params);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
                    connection.setRequestProperty("X-ClientTraceId", UUID.randomUUID().toString());
                    connection.setDoOutput(true);

                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//                    List<RequestBody> objList = new ArrayList();
//                    objList.add(new RequestBody(text));
                    String content = new Gson().toJson(objList);
                    byte[] encoded_content = content.getBytes("UTF-8");
                    wr.write(encoded_content, 0, encoded_content.length);
                    wr.flush();
                    wr.close();

                    StringBuilder response = new StringBuilder ();
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    Log.e(TAG, "JSON: " + response.toString());

                    sendBroadCast(response.toString(), context);
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "UnsupportedEncodingException: " + e.getMessage());
                } catch (ProtocolException e) {
                    Log.e(TAG, "ProtocolException: " + e.getMessage());
                } catch (IOException e) {
                    Log.e(TAG, "IOException: " + e.getMessage());
                }
            }
        }).start();
    }

    private static void sendBroadCast(String json, Context context) {
        Intent intent = new Intent();
        intent.setAction("ACTION_TRANSLATE_SUCCESS");
        intent.putExtra("RESULT_JSON", json);
        context.sendBroadcast(intent);
    }

    public static List<JSONLanguage> convertJSONToList(String json) {
        List<JSONLanguage> list = null;
        Gson gson = new Gson();
        list = gson.fromJson(json, new TypeToken<List<JSONLanguage>>() {}.getType());
        return list;
    }

    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(json_text);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}
