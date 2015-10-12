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

    private static String USER_NAME="USER_NAME";
    private static String PASSWORD="PASSWORD";
    private static String IS_REGISTERED="IS_REGISTERED";
    private static String IS_PROFILE_SET="IS_PROFILE_SET";

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


    public void setUserName(String userName) {
        pref.edit().putString(USER_NAME, userName).commit();
    }

    public String getUserName() {
        return pref.getString(USER_NAME, "jaspreet");
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

}
