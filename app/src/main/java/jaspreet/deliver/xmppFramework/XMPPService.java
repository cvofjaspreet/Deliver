package jaspreet.deliver.xmppFramework;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.jivesoftware.smack.XMPPConnection;

public class XMPPService extends Service {
    private XmppConnection xmppConnection;

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
        setXmppConnection(XmppConnection.getInstance(XMPPService.this));
        getXmppConnection().setContext(XMPPService.this.getApplicationContext());
        doConnect();
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
