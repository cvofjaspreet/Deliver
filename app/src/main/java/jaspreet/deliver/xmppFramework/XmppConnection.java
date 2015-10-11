package jaspreet.deliver.xmppFramework;

import android.content.Context;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import javax.net.ssl.SSLSocketFactory;

import jaspreet.deliver.R;
import jaspreet.deliver.database.Prefrences;
import jaspreet.deliver.utils.Config;

/**
 * Created by jaspret on 11/10/15.
 */
public class XmppConnection {
    private Context context;
    private Prefrences preference;
    private XMPPTCPConnection xmpptcpConnection;

    /**
     * Initialize  ReconnectionManager and PrivacyListManager
     * other vice they will not work
     */
    {
        try {
            Class.forName("org.jivesoftware.smack.ReconnectionManager");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    public XMPPTCPConnection getXmpptcpConnection() {
        return xmpptcpConnection;
    }

    public void setXmpptcpConnection(XMPPTCPConnection xmpptcpConnection) {
        this.xmpptcpConnection = xmpptcpConnection;
    }

    public Prefrences getPreference() {

        return preference;
    }

    public void setPreference(Prefrences preference) {
        this.preference = preference;
    }

    public Context getContext() {

        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        setPreference(Prefrences.getInstance(context));
    }

    private static XmppConnection ourInstance = new XmppConnection();

    public static XmppConnection getInstance(Context context) {

        return ourInstance;
    }

    private XmppConnection() {
    }

    public void connect(){
        XMPPTCPConnectionConfiguration config =
                XMPPTCPConnectionConfiguration.builder()
                        .setServiceName(getContext().getString(R.string.ip))
                        .setCompressionEnabled(false)
                        .setPort(Config.XMPPConfig.port)
                        .setConnectTimeout(Config.XMPPConfig.socketTimeOut)
                        .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                        .setSendPresence(true)
                        .build();

        setXmpptcpConnection( new XMPPTCPConnection(config));
        ConnectXmpp connectXmpp=new ConnectXmpp(getXmpptcpConnection());
        connectXmpp.start();
    }

    public void login(){
        XMPPlogin xmpplogin=new XMPPlogin(getXmpptcpConnection(),getPreference().getUserName(),getPreference().getPassword(),getContext().getString(R.string.resource));
        xmpplogin.start();
    }





}
