package com.example.ueliton.realmlab;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.ueliton.realmlab.Migration.CreatePersonMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import se.emilsjolander.sprinkles.Migration;
import se.emilsjolander.sprinkles.Sprinkles;

/**
 * Created by ueliton on 3/2/16.
 */
public class RealmLab extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);

        Sprinkles sprinkles = Sprinkles.init(getApplicationContext());
        runMigrations(sprinkles);
    }

    public void runMigrations(Sprinkles sprinkles) {
        sprinkles.addMigration(new CreatePersonMigration());
    }
}
