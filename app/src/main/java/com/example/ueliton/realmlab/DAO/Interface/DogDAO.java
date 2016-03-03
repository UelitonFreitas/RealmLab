package com.example.ueliton.realmlab.DAO.Interface;

import com.example.ueliton.realmlab.Model.Dog;

import io.realm.RealmResults;

/**
 * Created by ueliton on 3/2/16.
 */
public interface DogDAO{

    void save(Dog dog);
    void delete();
    void update();
    RealmResults<Dog> findAllWithAgeLessThan(Integer age);
}