package jaspreet.deliver.service;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by jaspret on 28/10/15.
 */
public class MyGcmListenerService extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        final String message = data.getString("message");
//        makeNotification(message);
    }
}
