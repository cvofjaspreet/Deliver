package jaspreet.deliver.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Locale;


/**
 * Created by jaspret on 11/10/15.
 */
public class Util {

    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
            android.net.ConnectivityManager cm = (android.net.ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            android.net.NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (android.net.NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean ret=haveConnectedWifi || haveConnectedMobile;

        return ret ;
    }

    public static String getUserName(String string) {
        if(string.contains("@"))
            return string.substring(0, string.indexOf('@'));
        else
            return string;

    }

   /* for facebook keyhash*/

    public static void  getKeyHash(Context context){

        // Add code to print out the key hash
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "jaspreet.deliver",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    public static String getActualNumber(int cc,String number){
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            String reagon=phoneUtil.getRegionCodeForCountryCode(cc);
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(number, reagon);
            long actualNumber=numberProto.getNationalNumber();
            return ""+actualNumber;
        } catch (Exception e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            return getPhoneNumber(number);
        }
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Gingerbread or later.
     */
    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Honeycomb or later.
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * Honeycomb MR1 or later.
     */
    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * ICS or later.
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * JELLY BEAN or later.
     */
    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * Uses static final constants to detect if the device's platform version is
     * ICS or later.
     */
    public static boolean hasLolipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
    public static String getPhoneNumber(String jid) {
        String mobileNumber=getUserName(jid);

        /**
         * USA // starts with 1 length 10
         * Canada // starts with 1 length 10
         * India // starts with 91 length 10
         * UK // starts with 44 length 10
         * Australia // starts with 61 length 9
         */


        if(mobileNumber.startsWith("61") && mobileNumber.length()>6)
            mobileNumber=mobileNumber.substring(mobileNumber.length()-9,mobileNumber.length());
        else if(mobileNumber.length()>=10)
            mobileNumber=mobileNumber.substring(mobileNumber.length()-10,mobileNumber.length());
        else mobileNumber=mobileNumber;
        return mobileNumber;
    }
    public static int getRandomNumber(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }
}
