package jaspreet.deliver.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

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


}
