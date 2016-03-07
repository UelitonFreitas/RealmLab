package com.example.ueliton.realmlab.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ueliton.realmlab.DAO.Interface.DogDAO;
import com.example.ueliton.realmlab.DAO.Interface.PersonDAO;
import com.example.ueliton.realmlab.Model.Dog;
import com.example.ueliton.realmlab.Factory.PetShopDAOFactory;
import com.example.ueliton.realmlab.Model.RealmPerson;
import com.example.ueliton.realmlab.Model.SprinklesPerson;
import com.example.ueliton.realmlab.R;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ManyQuery;

public class MainActivity extends BaseActivity {

    private static final String DIVIDER = "\n-------------------\n";
    private static int FOLD = 1;
    private static int BLEND = 5000;
    private Dialog loadingDialog;
    private String TAG = "REAL LAB";

    @Bind(R.id.realm_test_report)
    TextView realmTestReport;

    @Bind(R.id.edit_text_folder_rep)
    TextView folders;

    @Bind(R.id.edit_text_nmb_data)
    TextView numberOfData;

    @Bind(R.id.sprinkles_test_report)
    TextView sprinklesTestReport;

    @Bind(R.id.realm_progressBar)
    ProgressBar realmProgressBar;

    @Bind(R.id.sprinkles_progressBar)
    ProgressBar sprinklesProgressBar;

    private PersonDAO personDAO;
    private static int personId = 0;
    private RealmAsyncTask transaction;
    private Integer remalmNumberOfElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        personDAO = new PetShopDAOFactory().getPersonDAO();

        setUpToolBar();

        setUpFab();
//
//        // Use them like regular java objects
//        Dog dog = new Dog();
//        dog.setName("Rex");
//        dog.setAge(1);
//        Log.v(TAG, "Name of the dog: " + dog.getName());
//
//
//        DogDAO dogDAO = PetShopDAOFactory.getDogDAO();
//        dogDAO.findAllWithAgeLessThan(2);

        // Query Realm for all dogs less than 2 years old
//        RealmResults<Dog> puppies = realm.where(Dog.class).lessThan("age", 2).findAll();
//        RealmResults<Dog> puppies = dogDAO.findAllWithAgeLessThan(2);
//        puppies.size(); // => 0 because no dogs have been added to the Realm yet
//
//        dogDAO.save(dog);
//
//        // Queries are updated in real time
//        puppies.size(); // => 1
    }

    private void setUpFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FOLD = Integer.parseInt(folders.getText().toString());
                BLEND =  Integer.parseInt(numberOfData.getText().toString());
                setRealmMessage(DIVIDER);
                setSprinklesMessage(DIVIDER);
                showLoadingDialog(MainActivity.this);
                runTest();
            }
        });
    }

    private void hideLoadingDialog() {
        realmProgressBar.setVisibility(View.GONE);
    }

    private void showLoadingDialog(Context context) {

        realmProgressBar.setVisibility(View.VISIBLE);
        sprinklesProgressBar.setVisibility(View.VISIBLE);
    }


    private void runTest() {

        runRowInsertRealm();
        runSprinklesInsert();
    }

    private void runSprinklesInsert() {
        setSprinklesMessage("Processando...");
        new SprinklesAssync().execute();
    }

    class SprinklesAssync extends AsyncTask<Void, Void, Void> {

        private long initialTime;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            initialTime = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int j = 0; j < FOLD; j++) {
                for (int i = 0; i < BLEND; i++) {
                    SprinklesPerson person = new SprinklesPerson();
                    person.setName("Name " + getPersonId());
                    person.save();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ManyQuery.ResultHandler<SprinklesPerson> handler = new ManyQuery.ResultHandler<SprinklesPerson>() {
                @Override
                public boolean handleResult(CursorList<SprinklesPerson> result) {

                    setSprinklesMessage("Tempo gasto Sprinkles: " + (System.currentTimeMillis() - initialTime) + "m");
                    setSprinklesMessage("Número total de itens: " + result.size());
                    sprinklesProgressBar.setVisibility(View.GONE);
                    return false;
                }
            };
            SprinklesPerson.findAllAsync(MainActivity.this, handler);

        }
    }

    private void setSprinklesMessage(String test) {
        String m = sprinklesTestReport.getText().toString();
        sprinklesTestReport.setText(m + test + "\n");
    }

    private void runRowInsertRealm() {

        final long initialTime = System.currentTimeMillis();

        setRealmMessage("Processando...");

        realmProgressBar.setVisibility(View.VISIBLE);
        transaction = realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                for (int j = 0; j < FOLD; j++) {
                    for (int i = 0; i < BLEND; i++) {
                        RealmPerson realmPerson = new RealmPerson();
                        realmPerson.setId(getPersonId());
                        realmPerson.setName("Name " + getPersonId());
                        bgRealm.copyToRealm(realmPerson);
                    }
                }
            }
        }, new Realm.Transaction.Callback() {

            @Override
            public void onSuccess() {
                updateSprinklesResults(initialTime);
            }

            @Override
            public void onError(Exception e) {
                setRealmMessage("Erro! ");
                hideLoadingDialog();
                e.printStackTrace();

                // transaction is automatically rolled-back, do any cleanup here
            }
        });

    }

    private void updateSprinklesResults(final long initialTime) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                remalmNumberOfElements = realm.where(RealmPerson.class).findAll().size();
            }
        }, new Realm.Transaction.Callback() {
            @Override
            public void onSuccess() {

                setRealmMessage("Tempo gasto Realm: " + (System.currentTimeMillis() - initialTime) + "m");
                setRealmMessage("Número total de itens: " + MainActivity.this.remalmNumberOfElements.toString());
                hideLoadingDialog();
            }
        });
    }

    private void setRealmMessage(String test) {
        String m = realmTestReport.getText().toString();
        realmTestReport.setText(m + test + "\n");
    }

    public String getPersonId() {
        return UUID.randomUUID().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();

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

        if (transaction != null && !transaction.isCancelled()) {
            transaction.cancel();
        }
    }

}
