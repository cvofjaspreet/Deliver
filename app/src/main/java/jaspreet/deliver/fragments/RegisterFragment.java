package jaspreet.deliver.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import android.content.pm.PackageManager;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jaspreet.deliver.R;
import jaspreet.deliver.activities.RegisterActivity;
import jaspreet.deliver.database.Prefrences;
import jaspreet.deliver.utils.Config;
import jaspreet.deliver.utils.Util;
import jaspreet.deliver.utils.ViewUtil;
import jaspreet.deliver.xmppFramework.AccountListener;
import jaspreet.deliver.xmppFramework.CreateAccount;
import jaspreet.deliver.xmppFramework.XmppConnection;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Created by jaspret on 11/10/15.
 */
public class RegisterFragment extends Fragment implements AccountListener {
    private String TAG;

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private RegisterActivity activity;
    private Prefrences prefrences;


    public Prefrences getPrefrences() {
        return prefrences;
    }

    public void setPrefrences(Prefrences prefrences) {
        this.prefrences = prefrences;
    }

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register,null);
        activity=(RegisterActivity)getActivity();
        setPrefrences(Prefrences.getInstance(activity));
        TAG=getClass().getName();
        return  view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);

        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = view.findViewById(R.id.login_form);

    }




    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid email address.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            ViewUtil.showDialog(activity);
            CreateAccount createAccount=new CreateAccount(XmppConnection.getInstance().getXmpptcpConnection(),email,password);
            createAccount.setContext(activity);
            createAccount.setAccountListener(this);
            createAccount.start();

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    @Override
    public void onSucess() {
        if(Config.AppConfig.debugEnabled)
            Log.i(TAG,"sucess");
        ViewUtil.closeDialog();
        Fragment programList = new SetProfileFragment();
        android.support.v4.app.FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, programList, "setProfile");
        ft.commit();
    }

    @Override
    public void onError() {
        if(Config.AppConfig.debugEnabled)
            Log.i(TAG,"error");

        ViewUtil.closeDialog();
    }
}
