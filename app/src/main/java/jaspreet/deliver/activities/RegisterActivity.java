package jaspreet.deliver.activities;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import jaspreet.deliver.R;
import jaspreet.deliver.database.Prefrences;
import jaspreet.deliver.fragments.RegisterFragment;
import jaspreet.deliver.fragments.SetProfileFragment;
import jaspreet.deliver.utils.Config;
import jaspreet.deliver.utils.Util;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity  {

    private static String TAG="";
    private Prefrences prefrences;

    public Prefrences getPrefrences() {
        return prefrences;
    }

    public void setPrefrences(Prefrences prefrences) {
        this.prefrences = prefrences;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG=getClass().getName();
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setPrefrences(Prefrences.getInstance(RegisterActivity.this));
        FacebookSdk.sdkInitialize(getApplicationContext());
        openRegisterFragment();

//        Util.getKeyHash(RegisterActivity.this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void openRegisterFragment(){
        if(!getPrefrences().isRegistered()) {
            Fragment programList = new RegisterFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, programList, "register");
            ft.commit();
        }else{
            Fragment programList = new SetProfileFragment();
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, programList, "setProfile");
            ft.commit();
        }
    }


}

