package com.example.ueliton.realmlab.DAO.Interface;

import com.example.ueliton.realmlab.Model.RealmPerson;

import java.util.List;

/**
 * Created by ueliton on 3/2/16.
 */
public interface PersonDAO{

    void save(RealmPerson realmPerson);

    List<RealmPerson> findAll();
    void delete(List<RealmPerson> personsToBeDeleted);
}
