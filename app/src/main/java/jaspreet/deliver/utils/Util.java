package jaspreet.deliver.utils;

import android.content.Context;

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

}
