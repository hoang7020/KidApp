package vn.edu.fpt.kidapp.database;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import vn.edu.fpt.kidapp.model.APIObjectJSON;

public class DBManagerAPI {
    private static final String TAG = DBManagerAPI.class.getSimpleName();

    private static final String host = "http://192.168.1.6:49833";
    private Gson gson;

    private Context context;

    public static final String ACTION_LOGIN = "ACTION_LOGIN";
    public static final String ACTION_REGISTER = "ACTION_REGISTER";
    public static final String ACTION_GET_ALL = "ACTION_GET_ALL";
    public static final String ACTION_REMOVE_PICTURE = "ACTION_REMOVE_PICTURE";
    public static final String ACTION_ADD_PICTURE = "ACTION_ADD_PICTURE";
    public static final String ACTION_CHANGE_PASSWORD = "ACTION_CHANGE_PASSWORD";

    public DBManagerAPI(Context context) {
        gson = new Gson();
        this.context = context;
    }

    private HttpURLConnection createConnection(URL url, String method) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
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
                    HttpURLConnection urlConnection = createConnection(url, "POST");
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
                    HttpURLConnection urlConnection = createConnection(url, "POST");
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

    public void getAllPicture(final String username) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(host + "/getAllResultOfOneUser");
                    HttpURLConnection urlConnection = createConnection(url, "POST");
                    String params = "username=" + username;
                    setParams(urlConnection, params);
                    String result = readResponse(urlConnection);
                    Log.e(TAG, "Result GetAll: " + result);
                    sendDataBroadcast(ACTION_GET_ALL, result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addPicture(final String username, final String imageName, final long time,
                           final String eng1, final String eng2, final String eng3,
                           final String vie1, final String vie2, final String vie3) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JsonObject object = new JsonObject();
                    object.addProperty("username", username);
                    object.addProperty("image_name", imageName);
                    object.addProperty("image_time", time);
                    JsonObject engSub = new JsonObject();
                    engSub.addProperty("eng_1", eng1);
                    engSub.addProperty("eng_2", eng2);
                    engSub.addProperty("eng_3", eng3);
                    JsonObject vieSub = new JsonObject();
                    vieSub.addProperty("vie_1", vie1);
                    vieSub.addProperty("vie_2", vie2);
                    vieSub.addProperty("vie_3", vie3);
                    object.add("eng_sub", engSub);
                    object.add("vie_sub", vieSub);
                    Log.e(TAG, "addPicture: " + object.toString());

                    URL url = new URL(host + "/add-an-image");
                    HttpURLConnection urlConnection = createConnection(url, "POST");
                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    String params = gson.toJson(object);
                    setParams(urlConnection, params);
                    String result = readResponse(urlConnection);
                    Log.e(TAG, "addPicture: " + result);

                    sendDataBroadcast(ACTION_ADD_PICTURE, result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void deletePicture(final String imageId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(host + "/removeImage");
                    HttpURLConnection urlConnection = createConnection(url, "DELETE");
                    String params = "image_id=" + imageId;
                    setParams(urlConnection, params);
                    String result = readResponse(urlConnection);
                    sendDataBroadcast(ACTION_REMOVE_PICTURE, result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void changePassword(final String username, final String oldPassword, final String newPassword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(host + "/changePassword");
                    HttpURLConnection urlConnection = createConnection(url, "PUT");
                    String params = "username=" + username + "&old_password=" + oldPassword + "&new_password=" + newPassword;
                    setParams(urlConnection, params);
                    String result = readResponse(urlConnection);
                    sendDataBroadcast(ACTION_CHANGE_PASSWORD, result);
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
