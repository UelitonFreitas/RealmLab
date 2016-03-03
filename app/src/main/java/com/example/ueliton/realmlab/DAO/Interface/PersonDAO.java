package com.example.ueliton.realmlab.DAO.Interface;

import com.example.ueliton.realmlab.Model.Dog;
import com.example.ueliton.realmlab.Model.Person;

import java.util.List;

/**
 * Created by ueliton on 3/2/16.
 */
public interface PersonDAO{

    void save(Person person);
    void delete();
    void update();
    List<Person> findAll();
    void delete(List<Person> personsToBeDeleted);
}
