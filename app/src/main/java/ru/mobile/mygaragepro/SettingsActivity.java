package ru.mobile.mygaragepro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class SettingsActivity extends Activity {

    // имя настройки
    public static final String APP_PREFERENCES_ID = "id_mobile_app";
    public static final String APP_PREFERENCES_NUM_HOUSE = "num_house";
    public static final String APP_PREFERENCES_NUM_AUTO = "num_auto";
    public static final String APP_PREFERENCES_PASS = "pass_app";
    public static final String APP_PREFERENCES = "mysettings";

    SharedPreferences mSettings;
    String id_app;
    String num_house;
    String num_auto;
    String pass;
    EditText new_id_mobile_app;
    EditText new_num_house;
    EditText new_num_auto;
    EditText new_pass_app;

    /** Called when the activity is first created. */
    @SuppressLint("SetTextI18n")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);

        ProgressBar progressBar = findViewById(R.id.progressBar_settings);
        progressBar.setVisibility(View.INVISIBLE);


        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        id_app = mSettings.getString(APP_PREFERENCES_ID, "DEFAULT_USER_777");
        num_house = mSettings.getString(APP_PREFERENCES_NUM_HOUSE, "123");
        num_auto = mSettings.getString(APP_PREFERENCES_NUM_AUTO, "E777KX777");
        pass = mSettings.getString(APP_PREFERENCES_PASS, "123");
        new_id_mobile_app = findViewById(R.id.edit_id_mobile_app);
        new_id_mobile_app.setText(id_app);
        new_num_house = findViewById(R.id.edit_num_house);
        new_num_house.setText(num_house);
        new_num_auto = findViewById(R.id.edit_num_auto);
        new_num_auto.setText(num_auto);
        new_pass_app = findViewById(R.id.edit_pass_app);
        new_pass_app.setText(pass);

    }

    // Щелчки кнопок
    @SuppressLint({"NewApi", "NonConstantResourceId"})
    public void onClick(View v) {
        // Кнопка сохранения данных
        if (v.getId() == R.id.button_save) {// здесь содержится текст, введенный в текстовом поле
            Editor editor = mSettings.edit();
            String edit_id_mobile_app = new_id_mobile_app.getText().toString();
            editor.putString(APP_PREFERENCES_ID, edit_id_mobile_app);
            String edit_num_house = new_num_house.getText().toString();
            editor.putString(APP_PREFERENCES_NUM_HOUSE, edit_num_house);
            String edit_num_auto = new_num_auto.getText().toString();
            editor.putString(APP_PREFERENCES_NUM_AUTO, edit_num_auto);
            String edit_pass_app = new_pass_app.getText().toString();
            editor.putString(APP_PREFERENCES_PASS, edit_pass_app);
            editor.apply();

            Toast toast = Toast.makeText(getApplicationContext(), "Настройки сохранены", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent intent_balance = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent_balance);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent_gotoMain = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent_gotoMain);
        finish();
    }
}