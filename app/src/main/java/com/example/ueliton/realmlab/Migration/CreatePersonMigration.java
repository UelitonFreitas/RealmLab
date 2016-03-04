package com.example.ueliton.realmlab.Migration;

import android.database.sqlite.SQLiteDatabase;

import se.emilsjolander.sprinkles.Migration;

/**
 * Created by ueliton on 3/4/16.
 */
public class CreatePersonMigration extends Migration {
    @Override
    protected void doMigration(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + Attribute.TABLE_NAME + " (" +
                        Attribute.NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Attribute.EMAIL + " INTEGER," +
                        ")"
        );
    }

    public class Attribute {
        public static final String TABLE_NAME = "persons";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
    }
}