package jaspreet.deliver.service;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import jaspreet.deliver.database.Prefrences;

/**
 * Created by jaspret on 28/10/15.
 */
public class MyGcmRegistrationService extends IntentService {
    private static final String TAG = "MyRegistrationService";
    private static final String GCM_SENDER_ID = "46358341318";
    private static final String[] TOPICS = {"global"};

    public MyGcmRegistrationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            synchronized (TAG) {
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(GCM_SENDER_ID,
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                sendRegistrationToServer(token);
                Prefrences.getInstance(MyGcmRegistrationService.this).setDeviceToken(token);
                subscribeTopics(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void subscribeTopics(String token) throws IOException {
        for (String topic : TOPICS) {
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }
}