package com.example.ueliton.realmlab.DAO.Implementation;

import com.example.ueliton.realmlab.DAO.Interface.DogDAO;
import com.example.ueliton.realmlab.Model.Dog;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ueliton on 3/2/16.
 */
public class DogDAOIpl implements DogDAO {

    @Override
    public void save(Dog dog) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(dog);
        realm.commitTransaction();
    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }

    @Override
    public RealmResults<Dog> findAllWithAgeLessThan(Integer age) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Dog.class).lessThan("age", age).findAll();
    }
}
