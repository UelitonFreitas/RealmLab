package com.example.ueliton.realmlab.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.ueliton.realmlab.Interface.OnRemoveClick;
import com.example.ueliton.realmlab.R;

import io.realm.Realm;

public class BaseActivity extends AppCompatActivity {

    protected Realm realm;
    protected Toolbar toolbar;
    private ImageButton imageButton;
    private OnRemoveClick onRemoveClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();
    }

    protected void setUpToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageButton = (ImageButton) toolbar.findViewById(R.id.image_button_delete);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    protected void showToolBarRemoveButton(){
        imageButton.setVisibility(View.VISIBLE);
    }

    protected void setOnRemoveItem(OnRemoveClick onRemoveClick){
        this.onRemoveClick = onRemoveClick;
        if(imageButton != null) {
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseActivity.this.onRemoveClick.onRemoveClick();
                }
            });
        }
    }
}
