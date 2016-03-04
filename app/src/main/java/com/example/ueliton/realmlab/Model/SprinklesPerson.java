package com.example.ueliton.realmlab.Model;

import com.example.ueliton.realmlab.Migration.CreatePersonMigration;

import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import io.realm.annotations.PrimaryKey;
import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.Query;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by ueliton on 3/4/16.
 */

@Table(CreatePersonMigration.Attribute.TABLE_NAME)
public class SprinklesPerson extends Model {

    @Key // Name is not nullable
    @AutoIncrement
    @Column(CreatePersonMigration.Attribute.ID)
    private long id;

    @Column(CreatePersonMigration.Attribute.NAME)
    private String name;

    @Column(CreatePersonMigration.Attribute.IMAGE_URL)
    private String imageUrl; // imageUrl is an optional field

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static List<SprinklesPerson> findAll() {

        String query = " SELECT * FROM "+ CreatePersonMigration.Attribute.TABLE_NAME;
        List<SprinklesPerson> banks = Query.many(SprinklesPerson.class, query).get().asList();

        if (banks != null){
            return banks;
        }
        return Collections.emptyList();
    }
}
