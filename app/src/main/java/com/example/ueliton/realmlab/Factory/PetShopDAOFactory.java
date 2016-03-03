package com.example.ueliton.realmlab.Factory;

import com.example.ueliton.realmlab.DAO.Implementation.DogDAOIpl;
import com.example.ueliton.realmlab.DAO.Implementation.PersonDAOIpl;
import com.example.ueliton.realmlab.DAO.Interface.DogDAO;
import com.example.ueliton.realmlab.DAO.Interface.PersonDAO;

/**
 * Created by ueliton on 3/2/16.
 */
public class PetShopDAOFactory{


    private static DogDAOIpl dogDAOIpl;
    private static PersonDAOIpl personsDAOIpl;


    public static DogDAO getDogDAO() {
        if(dogDAOIpl == null)
            dogDAOIpl = new DogDAOIpl();
        return dogDAOIpl;
    }


    public static PersonDAO getPersonDAO() {
        if(personsDAOIpl == null)
            personsDAOIpl = new PersonDAOIpl();
        return personsDAOIpl;
    }
}
