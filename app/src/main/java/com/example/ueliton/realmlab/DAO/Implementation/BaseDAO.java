package com.example.ueliton.realmlab.DAO.Implementation;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by ueliton on 3/2/16.
 */
class BaseDAO{

    Realm mRealm;

    public BaseDAO(Realm realm) {
        this.mRealm = realm;
    }

    public void save(RealmObject object){

        mRealm.beginTransaction();
        mRealm.copyFromRealm(object);
        mRealm.commitTransaction();
    }

    public void delete(RealmObject t){

    }

    public void update(RealmObject object){
        mRealm.beginTransaction();
        mRealm.copyFromRealm(object);
        mRealm.commitTransaction();
    }
}
