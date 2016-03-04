package com.example.ueliton.realmlab.DAO.Implementation;

import com.example.ueliton.realmlab.DAO.Interface.PersonDAO;
import com.example.ueliton.realmlab.Model.RealmPerson;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by ueliton on 3/2/16.
 */
public class PersonDAOIpl implements PersonDAO {
    @Override
    public void save(final RealmPerson realmPerson) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(realmPerson);
            }
        });
    }


    @Override
    public List<RealmPerson> findAll() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmPerson> realmPersons = realm.where(RealmPerson.class).findAll();
        return realmPersons;
    }

    @Override
    public void delete(final List<RealmPerson> personsToBeDeleted) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (RealmPerson realmPerson : personsToBeDeleted) {
                    realmPerson.removeFromRealm();
                }
            }
        });

    }
}
