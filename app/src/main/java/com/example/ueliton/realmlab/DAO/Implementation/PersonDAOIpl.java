package com.example.ueliton.realmlab.DAO.Implementation;

import com.example.ueliton.realmlab.DAO.Interface.PersonDAO;
import com.example.ueliton.realmlab.Model.Dog;
import com.example.ueliton.realmlab.Model.Person;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ueliton on 3/2/16.
 */
public class PersonDAOIpl implements PersonDAO {
    @Override
    public void save(final Person person) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(person);
            }
        });
    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }

    @Override
    public List<Person> findAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Person> persons = realm.where(Person.class).findAll();
        return persons;
    }

    @Override
    public void delete(final List<Person> personsToBeDeleted) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Person person: personsToBeDeleted) {
                    person.removeFromRealm();
                }
            }
        });

    }
}
