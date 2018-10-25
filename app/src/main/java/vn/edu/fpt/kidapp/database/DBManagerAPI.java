package vn.edu.fpt.kidapp.database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DBManagerAPI {
    private static final String TAG = DBManagerAPI.class.getSimpleName();

    private static final String host = "http://192.168.1.9:49833";
    private Gson gson;

    private Context context;

    public static final String ACTION_LOGIN = "ACTION_LOGIN";
    public static final String ACTION_REGISTER = "ACTION_REGISTER";

    public DBManagerAPI(Context context) {
        gson = new Gson();
        this.context = context;
    }

    private HttpURLConnection createConnection(URL url) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlConnection;
    }

    private void setParams(HttpURLConnection urlConnection, String params) {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.write(params.getBytes());
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readResponse(HttpURLConnection urlConnection) {
        StringBuilder sb = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void login(final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(host + "/login");
                    HttpURLConnection urlConnection = createConnection(url);
                    String params = "username=" + username + "&password=" + password;
                    setParams(urlConnection, params);
                    String result = readResponse(urlConnection);
                    sendDataBroadcast(ACTION_LOGIN, result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void register(final String username, final String password, final String address) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(host + "/register");
                    HttpURLConnection urlConnection = createConnection(url);
                    String params = "username=" + username + "&password=" + password + "&address=" + address;
                    setParams(urlConnection, params);
                    String result = readResponse(urlConnection);
                    Log.e(TAG, "Result REGISTER: " + result);
                    sendDataBroadcast(ACTION_REGISTER, result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendDataBroadcast(String action, String result) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra("API_RESULT", result);
        context.sendBroadcast(intent);
    }
}
