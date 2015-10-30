package jaspreet.deliver.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;
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

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jaspreet.deliver.R;
import jaspreet.deliver.activities.RegisterActivity;
import jaspreet.deliver.database.Prefrences;
import jaspreet.deliver.utils.Config;
import jaspreet.deliver.utils.Util;
import jaspreet.deliver.utils.ViewUtil;
import jaspreet.deliver.xmppFramework.AccountListener;
import jaspreet.deliver.xmppFramework.CreateAccount;
import jaspreet.deliver.xmppFramework.XMPPService;
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
    public CallbackManager callbackManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register,null);
        activity=(RegisterActivity)getActivity();
        setPrefrences(Prefrences.getInstance(activity));
        TAG=getClass().getName();
        return  view;
    }

    @Override
    public void onViewCreated(final View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);
         Button mEmailSignInButton = (Button) view.findViewById(R.id.email_sign_in_button);
        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if(Util.haveNetworkConnection(activity))
                    attemptLogin();
                    else
                    ViewUtil.showStackBar(view.findViewById(R.id.email_sign_in_button),getString(R.string.no_internet_connectiion),activity);
                    return true;
                }
                return false;
            }
        });


        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.haveNetworkConnection(activity))
                    attemptLogin();
                else
                    ViewUtil.showStackBar(view.findViewById(R.id.email_sign_in_button), getString(R.string.no_internet_connectiion), activity);
            }
        });

        mLoginFormView = view.findViewById(R.id.login_form);


        /**
         * for facebook
         */

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        // If using in a fragment
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            ProfileTracker mProfileTracker;
            @Override
            public void onSuccess( LoginResult loginResult) {
                // App code
                if (Config.AppConfig.debugEnabled)
                    Log.i(TAG, "onSuccess");

                        // Set the access token using
                        // currentAccessToken when it's loaded or set.
                        getPrefrences().setFbConnected(true);
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        try{
                                        // Application code
                                        Log.v("LoginActivity", response.toString());
                                        JSONObject jsonObject=response.getJSONObject();
                                        String password= jsonObject.getString("id");
                                        getPrefrences().setPassword(password);
                                        getPrefrences().setUserName(jsonObject.getString("id"));
                                        CreateAccount createAccount=new CreateAccount(XmppConnection.getInstance().getXmpptcpConnection(),jsonObject.getString("email"),password);
                                        createAccount.setContext(activity);
                                        createAccount.setAccountListener(RegisterFragment.this);
                                        createAccount.start();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();


                mProfileTracker= new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                        if(Config.AppConfig.debugEnabled)
                            Log.v("facebook - profile", profile2.getFirstName());
                        Profile.setCurrentProfile(profile2);
                        mProfileTracker.stopTracking();
                    }
                };
                mProfileTracker.startTracking();


                // If the access token is available already assign it.


        }

            @Override
            public void onCancel() {
                // App code
                if(Config.AppConfig.debugEnabled)
                    Log.i(TAG,"onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                if(Config.AppConfig.debugEnabled)
                    Log.i(TAG,"onError");
            }
        });

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
        getPrefrences().setRegistered(true);
    }

    @Override
    public void onError() {
        if(Config.AppConfig.debugEnabled)
            Log.i(TAG,"error");
        Intent service = new Intent(activity, XMPPService.class);
        activity.startService(service);
        ViewUtil.closeDialog();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (Config.AppConfig.debugEnabled)
            Log.i(TAG, "onActivityResult");

    }
}
