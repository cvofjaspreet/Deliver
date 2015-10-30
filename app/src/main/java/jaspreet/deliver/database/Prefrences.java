package jaspreet.deliver.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jaspret on 11/10/15.
 */
public class Prefrences {
    /**
     * Keys for preference values
     */
    public static String ITEM_SELECTED = "item_selected";
    private static String USER_NAME="USER_NAME";
    private static String PASSWORD="PASSWORD";
    private static String IS_REGISTERED="IS_REGISTERED";
    private static String IS_PROFILE_SET="IS_PROFILE_SET";
    private static String IS_FACEBOOK_CONNECTED="IS_FACEBOOK_CONNECTED";
    private static String USER_EMAIL="USER_EMAIL";
    private static String USER_PIC="USER_PIC";
    private static String NICK_NAME="NICK_NAME";
    private static final String IS_VERIFYING = "IS_VERIFYING";
    public static String COUNTRY_CODE="country_code";
    public static String AREA_CODE="area_code";
    public static String MOBILE_NUMBER="mobile_number";
    public static String DEVICE_TOKEN = "device_token";

    static SharedPreferences pref;
    private static Prefrences ourInstance ;

    public static Prefrences getInstance(Context context)
    {
        if(ourInstance!=null)
        return ourInstance;
        else
         return ourInstance=new Prefrences(context);
    }

    private Prefrences(Context context) {
        pref = context.getSharedPreferences("yayePreferences",
                Context.MODE_PRIVATE);
    }

    public void setDeviceToken(String deviceToken) {
        pref.edit().putString(DEVICE_TOKEN, deviceToken).commit();
    }
    public String getDeviceToken() {
        return pref.getString(DEVICE_TOKEN,"");
    }
    public boolean isVerifing() {
        return pref.getBoolean(IS_VERIFYING, false);
    }
    public void setVerifing(boolean verifing) {
        pref.edit().putBoolean(IS_VERIFYING,verifing ).commit();
    }
    public void setSelectedCountry(int selected) {
               pref.edit().putInt(ITEM_SELECTED, selected).commit();
    }
    public int getSelectedCountry() {
        return pref.getInt(ITEM_SELECTED, -1);
    }

    public void setUserName(String userName) {
        pref.edit().putString(USER_NAME, userName).commit();
    }
    public String getUserName() {
        return pref.getString(USER_NAME, "jaspreet");
    }

    public void setNickName(String nickName) {
        pref.edit().putString(NICK_NAME, nickName).commit();
    }
    public String getNickName() {
        return pref.getString(NICK_NAME, "");
    }

    public String getEmail() {
        return pref.getString(USER_EMAIL, "");
    }
    public void setEmail(String email) {
        pref.edit().putString(USER_EMAIL, email).commit();
    }

    public String getUserPicUrl() {
        return pref.getString(USER_PIC, "");
    }
    public void setUserPicUrl(String url) {
        pref.edit().putString(USER_PIC, url).commit();
    }

    public void setPassword(String password) {
        pref.edit().putString(PASSWORD, password).commit();
    }

    public String getPassword() {
        return pref.getString(PASSWORD, "9319396142");
    }

    public void setRegistered(boolean registered) {
        pref.edit().putBoolean(IS_REGISTERED, registered).commit();
    }

    public boolean isRegistered() {
        return pref.getBoolean(IS_REGISTERED, false);
    }

    public void setProfile(boolean set) {
        pref.edit().putBoolean(IS_PROFILE_SET, set).commit();
    }

    public boolean isProfileSet() {
        return pref.getBoolean(IS_PROFILE_SET, false);
    }

    public void setFbConnected(boolean connected) {
        pref.edit().putBoolean(IS_FACEBOOK_CONNECTED, connected).commit();
    }

    public boolean isFbConnected() {
        return pref.getBoolean(IS_FACEBOOK_CONNECTED, false);
    }

    public void setMobileNumber(String mobileNumber) {
        pref.edit().putString(MOBILE_NUMBER, mobileNumber).commit();
    }

    public String getMobileNumber() {
        return pref.getString(MOBILE_NUMBER,"");
    }

    public void setCountryCode(String countryCode) {
        pref.edit().putString(COUNTRY_CODE, countryCode).commit();
    }
    public void setAreaCode(String areacode) {
        pref.edit().putString(AREA_CODE, areacode).commit();
    }
    public String getAreaCode() {
        return pref.getString(AREA_CODE,"");
    }
    public String getCountryCode() {
        return pref.getString(COUNTRY_CODE,"");
    }
}
