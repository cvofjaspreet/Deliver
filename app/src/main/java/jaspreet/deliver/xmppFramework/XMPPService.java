package jaspreet.deliver.xmppFramework;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.preference.Preference;

import org.jivesoftware.smack.XMPPConnection;

import jaspreet.deliver.database.Prefrences;

public class XMPPService extends Service {
    private XmppConnection xmppConnection;
    private Prefrences preference;

    public Prefrences getPreference() {
        return preference;
    }

    public void setPreference(Prefrences preference) {
        this.preference = preference;
    }

    public XmppConnection getXmppConnection() {
        return xmppConnection;
    }

    public void setXmppConnection(XmppConnection xmppConnection) {
        this.xmppConnection = xmppConnection;
    }

    public XMPPService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setXmppConnection(XmppConnection.getInstance());
        getXmppConnection().setContext(XMPPService.this.getApplicationContext());
        doConnect();
        setPreference(Prefrences.getInstance(XMPPService.this));
        doLogin();
        ConnectionState.getInstance().setXmpptcpConnection(getXmppConnection().getXmpptcpConnection());
        ConnectionState.getInstance().start();
        return START_NOT_STICKY;
    }


    public void doConnect(){
        getXmppConnection().connect();
    }
    public void doLogin(){
        getXmppConnection().login();
    }
}
