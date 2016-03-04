package com.example.ueliton.realmlab.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ueliton.realmlab.Adapter.PersonAdapter;
import com.example.ueliton.realmlab.DAO.Interface.DogDAO;
import com.example.ueliton.realmlab.DAO.Interface.PersonDAO;
import com.example.ueliton.realmlab.Interface.OnRemoveClick;
import com.example.ueliton.realmlab.Model.Dog;
import com.example.ueliton.realmlab.Factory.PetShopDAOFactory;
import com.example.ueliton.realmlab.Model.RealmPerson;
import com.example.ueliton.realmlab.Interface.OnCheckBoxesShow;
import com.example.ueliton.realmlab.R;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class MainActivity extends BaseActivity {

    private String TAG="REAL LAB";

    @Bind(R.id.recycler_view_persons)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private PersonAdapter mAdapter;
    List<RealmPerson> mRealmPersons = Collections.emptyList();

    private PersonDAO personDAO;
    private static Long personId = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        personDAO = new PetShopDAOFactory().getPersonDAO();

        setUpToolBar();

        setUpFab();

        setUpPersonsList();

        setOnRemoveItem(new OnRemoveClick() {
            @Override
            public void onRemoveClick() {
                personDAO.delete(mAdapter.getSelectedItens());
                mAdapter.updatePersons(personDAO.findAll());
            }
        });

        // Use them like regular java objects
        Dog dog = new Dog();
        dog.setName("Rex");
        dog.setAge(1);
        Log.v(TAG, "Name of the dog: " + dog.getName());


        DogDAO dogDAO = PetShopDAOFactory.getDogDAO();
        dogDAO.findAllWithAgeLessThan(2);

        // Query Realm for all dogs less than 2 years old
//        RealmResults<Dog> puppies = realm.where(Dog.class).lessThan("age", 2).findAll();
        RealmResults<Dog> puppies = dogDAO.findAllWithAgeLessThan(2);
        puppies.size(); // => 0 because no dogs have been added to the Realm yet

        dogDAO.save(dog);

        // Queries are updated in real time
        puppies.size(); // => 1
    }

    private void setUpFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PersonDAO personDAO = PetShopDAOFactory.getPersonDAO();

                Log.d("LOG", "Salvando");
                for (int i = 0; i < 3000; i++) {
                    RealmPerson realmPerson = new RealmPerson();
                    realmPerson.setName("Name " + getPersonId());
                    personDAO.save(realmPerson);
                }

                Log.d("LOG", "salvo");
                mAdapter.updatePersons(personDAO.findAll());
            }
        });
    }

    private String getPersonId() {
        return (personId++).toString();
    }

    private void setUpPersonsList() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new PersonAdapter(this, mRealmPersons, new OnCheckBoxesShow() {
            @Override
            public void onCheckBoxShow() {
                showToolBarRemoveButton();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRealmPersons = personDAO.findAll();
        mAdapter.updatePersons(mRealmPersons);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
