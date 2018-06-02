package de.rocktale.birthdaydroid;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

public class BirthdaydroidApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
