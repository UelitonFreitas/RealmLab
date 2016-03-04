package com.example.ueliton.realmlab.Model;

import com.example.ueliton.realmlab.Migration.CreatePersonMigration;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by ueliton on 3/4/16.
 */

@Table(CreatePersonMigration.Attribute.TABLE_NAME)
public class SprinklesPerson extends Model {
}
