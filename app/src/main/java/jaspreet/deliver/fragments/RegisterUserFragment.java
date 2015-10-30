package jaspreet.deliver.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import jaspreet.deliver.R;
import jaspreet.deliver.activities.RegisterActivity;
import jaspreet.deliver.database.Prefrences;
import jaspreet.deliver.http.AsyncTaskListener;
import jaspreet.deliver.lists.CountryCodeList;
import jaspreet.deliver.models.CountyCodeModel;
import jaspreet.deliver.utils.Config;
import jaspreet.deliver.utils.Util;
import jaspreet.deliver.utils.ViewUtil;


public class RegisterUserFragment extends Fragment implements AsyncTaskListener {

    @Override
    public void onHttpResponse(String callResponse, int pageId) {
        System.out.println(callResponse);
    }

    @Override
    public void onError(String error, int pageId) {
//        ViewUtil.callSnackbar(getActivity(), "Server Time out !");
    }


    private static final int SELECT_COUNTRY_CODE_REQUEST = 1;
    private TextView register_user_code;
    public static String TAG;
    public static int NUMBERCOUNT = 8;
    private EditText register_user_phone;
    private Prefrences prefrences;
    private TextView register_user_country, plus;
    private TextView home_learn_more_btn;
    private TextView register_user_text1;

    private boolean add;
    private double lat;
    private double lng;


    private RelativeLayout selectCountryClick;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefrences = Prefrences.getInstance(getActivity());
        if (prefrences.isVerifing()) {
            Fragment confirmCodeFragment = new ConfirmCodeFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, confirmCodeFragment, "confirmCode").addToBackStack("confirmCode");
            ft.commit();
        }
        RegisterActivity registerActivity=(RegisterActivity)getActivity();

        LocationManager mlocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (registerActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && registerActivity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        Location loc = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(loc==null){
            loc = mlocManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if(loc!=null){
            lat=loc.getLatitude();
            lng=loc.getLongitude();
        }
        if(getActivity()!=null) {
            addCountries();
            GetCountry country = new GetCountry(lat, lng);
            country.execute();
            registerCritersism();

        }

    }


    @Override
    public void onResume() {
        super.onResume();

        RegisterActivity registerActivity=(RegisterActivity)getActivity();
        String countryCode=registerActivity.countryCode;
        String countryName=registerActivity.countryName;
//        TextView textView=(TextView)registerActivity.toolbar. findViewById(R.id.toolbar_title);
//        textView.setText(getString(R.string.register_user_lable));

        if(countryCode!=null){
            register_user_code.setText(countryCode);
        }

        if(countryName!=null){
            register_user_country.setText(countryName);
        }
//        register_user_phone.requestFocus();
    }

    /**
     * Register for crittersism
     */
    private void registerCritersism() {
//		Crittercism.initialize(getApplicationContext(), "5499160651de5e9f042ec58c");
    }

    private void addCountries() {

        CountryCodeList codeList = CountryCodeList.getInstance();
        codeList.clear();
        String[] country_codes = Config.country_codes;
        for (int i = 0; i < country_codes.length; i++) {
            CountyCodeModel countyCodeModel = new CountyCodeModel();
            countyCodeModel.setCountryName(country_codes[i]);
            countyCodeModel.setFlagImageName(country_codes[i]);
            i++;
            countyCodeModel.setCountryCode(country_codes[i]);
            codeList.add(countyCodeModel);
        }
    }
    class GetCountry extends AsyncTask<Void, Void, Void> {


        private double lat;
        private double lng;
        private String country;
        public GetCountry(double lat,double lng) {
            this.lat=lat;
            this.lng=lng;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
            Geocoder gCoder = new Geocoder(getActivity());
            List<Address> addresses;

                addresses = gCoder.getFromLocation(lat, lng, 5);
                Log.e("get adderess", addresses + "");
                if (addresses != null && addresses.size() > 0) {
                    country = addresses.get(0).getCountryName();
                    country=country.toLowerCase();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            CountryCodeList codeList = CountryCodeList.getInstance();
//            codeList.clear();
            String[] country_codes = Config.country_codes;
            for (int i = 0; i < country_codes.length; i++) {
//                CountyCodeModel countyCodeModel = new CountyCodeModel();
//                countyCodeModel.setCountryName(country_codes[i]);
//                countyCodeModel.setFlagImageName(country_codes[i]);
                if(country_codes[i].toLowerCase().equals(country)){
                    register_user_country.setText(country_codes[i]);
                    	i++;
                    register_user_code.setText(country_codes[i]);

    				break;
                }

                i++;
//                countyCodeModel.setCountryCode(country_codes[i]);
//                codeList.add(countyCodeModel);
            }

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_register_user, container, false);

        register_user_code = (TextView) rootView.findViewById(R.id.register_user_code);
        rootView.findViewById(R.id.home_login_btn).setVisibility(View.GONE);
        home_learn_more_btn=(TextView)rootView.findViewById(R.id.home_learn_more_btn);
        home_learn_more_btn.setVisibility(View.GONE);
        register_user_text1=(TextView)rootView.findViewById(R.id.register_user_text1);


        register_user_country=(TextView)rootView.findViewById(R.id.register_user_country);
        plus=(TextView)rootView.findViewById(R.id.plus);
        register_user_phone = (EditText) rootView
                .findViewById(R.id.register_user_phone);
        selectCountryClick=(RelativeLayout)rootView.findViewById(R.id.selectCountryLayout);
        register_user_phone.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == EditorInfo.IME_ACTION_DONE) {
                    if (add) {
                        onStartUsingApp(register_user_phone);
                    }
                }
                return false;
            }
        });


        home_learn_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartUsingApp(v);
            }
        });

        register_user_phone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() > 6) {
                    home_learn_more_btn.setVisibility(View.VISIBLE);
                } else {
                    home_learn_more_btn.setVisibility(View.GONE);
                }
            }
        });


        selectCountryClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment register = new CountryCodeFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, register, "countryCode").addToBackStack("countryCode");
                ft.commit();
                View focus = getActivity().getCurrentFocus();
                if (focus != null) {
                   ViewUtil.hideKeyBord(focus, getActivity());
                }
                RegisterActivity registerActivity = (RegisterActivity) getActivity();
                registerActivity.countryCode = register_user_code.getText().toString();
                registerActivity.countryName = register_user_country.getText().toString();
//                TextView textView = (TextView) registerActivity.toolbar.findViewById(R.id.toolbar_title);
//                textView.setText(getString(R.string.header_select_country_code));

            }
        });


        return rootView;
    }







    public void onStartUsingApp(View view) {
        if (register_user_code.getText().toString().trim().equals("241")
                || register_user_code.getText().toString().trim().equals("94")
                || register_user_code.getText().toString().trim().equals("231")) {
            NUMBERCOUNT = 7;
        } else if (register_user_code.getText().toString().trim().equals("299")
                || register_user_code.getText().toString().trim().equals("976")) {
            NUMBERCOUNT = 6;
        } else {
            NUMBERCOUNT = 8;
        }

        if (validate()) {
            int randomNumber = Util.getRandomNumber(
                    Config.AppConfig.MIN_RANDUM_NUMBER, Config.AppConfig.MAX_RANDUM_NUMBER);
            prefrences.setPassword(""+randomNumber);
            String country_code = register_user_code.getText().toString()
                    .trim();
            String mobile_number = register_user_phone.getText().toString()
                    .trim();
            mobile_number=Util.getActualNumber(Integer.parseInt(country_code),country_code+mobile_number);
            register_user_phone.setText(mobile_number);
            String area_code = mobile_number.substring(0, 3);
            prefrences.setCountryCode(country_code);
            prefrences.setMobileNumber(mobile_number);
            prefrences.setAreaCode(area_code);
//            prefrences.setMyId(country_code+mobile_number);
            /**
             * TODO 1.call sms api web service
             *
             * 2.call fill sms verifivication screen
             */
//            SendSms sendSms=new SendSms(getActivity());
//            WorkQueue queue=WorkQueue.getInstance();
//            queue.execute(sendSms);
//            Intent intent = new Intent(RegisterUserActivity.this, ConfirmCodeActivity.class);
//            overridePendingTransition(R.anim.abc_right_to_left, R.anim.abc_fade_out);
//            startActivity(intent);
            Fragment register = new ConfirmCodeFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, register, "confirmCode").addToBackStack("countryCode");
            ft.commit();
            ViewUtil.hideKeyBord(register_user_phone, getActivity());
            prefrences.setVerifing(false);
        }

    }

    private boolean validate() {
        if (register_user_phone.getText().toString().trim().equals("")) {
            ViewUtil.showStackBar(register_user_phone,"Enter your mobile number",getActivity());
            return false;
        } else if (register_user_phone.getText().toString().trim().length() < NUMBERCOUNT) {
            ViewUtil.showStackBar(register_user_phone, "Enter a valid mobile number", getActivity());
            return false;
        }else if (!Util.haveNetworkConnection(getActivity())) {
            ViewUtil.showStackBar(register_user_phone, getString(R.string.no_internet_connectiion), getActivity());
            return false;
        }else if (prefrences.getDeviceToken().equals("")) {
            RegisterActivity registerActivity=(RegisterActivity)getActivity();
//            registerActivity.registerInBackground();
//            ViewUtil.callSnackbar(getActivity(), getString(R.string.opps_something_went_wrong));
            return false;
        }
        String token =prefrences.getDeviceToken();
        System.out.println(token);
        return true;
    }
}
