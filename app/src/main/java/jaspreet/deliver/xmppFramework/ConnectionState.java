package jaspreet.deliver.xmppFramework;

import android.util.Log;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.ReconnectionManager;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import jaspreet.deliver.utils.Config;

/**
 * Created by jaspret on 11/10/15.
 */
public class ConnectionState implements ConnectionListener {

    private String TAG="";

    private XMPPTCPConnection xmpptcpConnection;

    public XMPPTCPConnection getXmpptcpConnection() {
        return xmpptcpConnection;
    }
    private static ConnectionState ourInstance = new ConnectionState();

    public static ConnectionState getInstance() {
        return ourInstance;
    }

    private ConnectionState() {
    }

    public void start(){
        TAG=getClass().getName();
        ReconnectionManager.getInstanceFor(getXmpptcpConnection()).enableAutomaticReconnection();
        getXmpptcpConnection().addConnectionListener(this);
    }
    public void setXmpptcpConnection(XMPPTCPConnection xmpptcpConnection) {
        this.xmpptcpConnection = xmpptcpConnection;
    }

    @Override
    public void connected(XMPPConnection connection) {
        if(Config.AppConfig.debugEnabled)
            Log.i(TAG, "connected");
    }

    @Override
    public void authenticated(XMPPConnection connection, boolean resumed) {
        if(Config.AppConfig.debugEnabled)
            Log.i(TAG,"authenticated");
    }

    @Override
    public void connectionClosed() {
        if(Config.AppConfig.debugEnabled)
            Log.i(TAG,"connectionClosed");
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        if(Config.AppConfig.debugEnabled)
            Log.i(TAG,"connectionClosedOnError");
    }

    @Override
    public void reconnectionSuccessful() {
        if(Config.AppConfig.debugEnabled)
            Log.i(TAG,"reconnectionSuccessful");
    }

    @Override
    public void reconnectingIn(int seconds) {
        if(Config.AppConfig.debugEnabled)
            Log.i(TAG,"reconnectingIn in ="+seconds);
    }

    @Override
    public void reconnectionFailed(Exception e) {
        if(Config.AppConfig.debugEnabled)
            Log.i(TAG,"reconnectionFailed");
    }


}
