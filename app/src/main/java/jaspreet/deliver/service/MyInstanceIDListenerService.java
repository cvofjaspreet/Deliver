package jaspreet.deliver.service;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by jaspret on 28/10/15.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {
    public void onTokenRefresh() {
        Intent intent = new Intent(this, MyGcmRegistrationService.class);
        startService(intent);
    }
}