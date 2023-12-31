package ru.mobile.mygaragepro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//import android.util.Log;


public class MainActivity extends Activity implements View.OnClickListener {

    public static final String APP_PREFERENCES_ID = "id_mobile_app";
    public static final String APP_PREFERENCES_NUM_HOUSE = "num_house";
    public static final String APP_PREFERENCES_NUM_AUTO = "num_auto";
    public static final String APP_PREFERENCES_PASS = "pass_app";
    public static final String APP_PREFERENCES = "mysettings";
    public String URL_Links_1;
    SharedPreferences mSettings;
    String id_app;
    String num_house;
    String num_auto;
    String pass;



    Button car_button1;
    Button car_button2;
    Button car_button3;
    Button car_button4;
    private ProgressBar progressBarMain;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        id_app = mSettings.getString(APP_PREFERENCES_ID, "DEFAULT_USER_777");
        num_house = mSettings.getString(APP_PREFERENCES_NUM_HOUSE, "123");
        num_auto = mSettings.getString(APP_PREFERENCES_NUM_AUTO, "E777KX777");
        pass = mSettings.getString(APP_PREFERENCES_PASS, "123");

        progressBarMain = findViewById(R.id.progressBarMain);
        progressBarMain.setVisibility(View.INVISIBLE);

        URL_Links_1 = "https://app.nash-svyaznoy.ru/remedyconnection/test_task.php?user_app=" + id_app + "&pass_app=" + pass + "&num_house="+ num_house + "&num_auto=" + num_auto + "&";

        car_button1 = findViewById(R.id.Button1);
        car_button1.setOnClickListener(this);

        car_button2 = findViewById(R.id.Button2);
        car_button2.setOnClickListener(this);

        car_button3 = findViewById(R.id.Button3);
        car_button3.setOnClickListener(this);

        car_button4 = findViewById(R.id.Button4);
        car_button4.setOnClickListener(this);

    }

    @SuppressLint("StaticFieldLeak")
    private class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressBarMain = findViewById(R.id.progressBarMain);
            progressBarMain.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            InputStream inputstream = null;
            String data;
            try {
                HttpURLConnection connection;
                HttpsURLConnection https;
                URL url = new URL(urls[0]);
                if(url.getProtocol().equalsIgnoreCase("https")){
                    https = (HttpsURLConnection) url.openConnection();
                    connection = https;
                }else{
                    connection = (HttpURLConnection) url.openConnection();

                }
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(7000);

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK | responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                    inputstream = connection.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    int read;
                    while ((read = inputstream.read()) != -1) {
                        bos.write(read);
                    }
                    bos.close();

                    data = bos.toString();

                } else {
                    data = connection.getResponseMessage() + " . Error Code : " + responseCode;
                }
                connection.disconnect();
            } catch (IOException e) {
                data = "Возникла ошибка подключения !:\n" + e;
            } finally {
                if (inputstream != null) {
                    try {
                        inputstream.close();
                    } catch (IOException e) {
                        data = "Не удалось завершить сессию !\n" + e;
                        e.printStackTrace();
                    }
                }
            }
            return data;
        }


        @SuppressLint("ShowToast")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBarMain.setVisibility(View.INVISIBLE);
            //Log.d(LOG_TAG, "Response: " + result);
            Toast toast;
            if(result != null){
                toast = Toast.makeText(getApplicationContext(),
                        result,
                        Toast.LENGTH_SHORT);
            }else{
                toast = Toast.makeText(getApplicationContext(),
                        "Возникла ощибка при получении данных с сервера.",
                        Toast.LENGTH_SHORT);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    //@Override
    public void onClick(View v) {
        ConnectivityManager myConnMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert myConnMgr != null;
        NetworkInfo networkinfo = myConnMgr.getActiveNetworkInfo();

        if (networkinfo != null && networkinfo.isConnected()) {

            if (v == car_button1) {
                new MainActivity.RequestTask().execute(URL_Links_1 + "btn1=1");
            }
            if (v == car_button2) {
                new MainActivity.RequestTask().execute(URL_Links_1 + "btn2=1");
            }
            if (v == car_button3) {
                new MainActivity.RequestTask().execute(URL_Links_1 + "btn3=1");
            }
            if (v == car_button4) {
                new MainActivity.RequestTask().execute(URL_Links_1 + "btn4=1");
            }

        }else{
            Toast toast = Toast.makeText(this, "Нет доступа к сети Интернет", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void onBackPressed() {
        finish();
    }
    @Override
    protected void onUserLeaveHint() {
        finish();
        super.onUserLeaveHint();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent_main = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent_main);
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_prog) {
            Intent intent_main = new Intent(MainActivity.this, AboutApp.class);
            startActivity(intent_main);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

