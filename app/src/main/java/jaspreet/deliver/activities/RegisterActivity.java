package jaspreet.deliver.activities;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import jaspreet.deliver.R;
import jaspreet.deliver.database.Prefrences;
import jaspreet.deliver.fragments.RegisterFragment;
import jaspreet.deliver.fragments.SetProfileFragment;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity  {

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
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setPrefrences(Prefrences.getInstance(RegisterActivity.this));
        openRegisterFragment();

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
            ft.replace(R.id.container, programList, "setProfile").addToBackStack("setProfile");
            ft.commit();
        }
    }
}

