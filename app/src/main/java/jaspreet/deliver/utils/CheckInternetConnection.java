package jaspreet.deliver.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import jaspreet.deliver.xmppFramework.XMPPService;

/**
 * Created by jaspret on 13/10/15.
 */
public class CheckInternetConnection extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Util.haveNetworkConnection(context)) {
            Intent service = new Intent(context, XMPPService.class);
            context.startService(service);
        }
    }
}
