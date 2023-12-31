package ru.mobile.mygaragepro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AboutApp extends Activity {
    // имя настройки

    TextView new_about_developer;
    TextView new_about_version;

    /** Called when the activity is first created. */
    @SuppressLint("SetTextI18n")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_app);
        int currentVersionApp = BuildConfig.VERSION_CODE;
        String currentVersionName = BuildConfig.VERSION_NAME;

        new_about_developer = findViewById(R.id.about_developer);
        new_about_developer.setText("Разработчик: https://t.me/NashSvyaznoy");
        new_about_version = findViewById(R.id.about_version);
        new_about_version.setText("Версия: " + currentVersionName + " дата: " + currentVersionApp);

    }

    @Override
    public void onBackPressed() {
        Intent intent_gotoMain = new Intent(AboutApp.this, MainActivity.class);
        startActivity(intent_gotoMain);
        finish();
    }
}
