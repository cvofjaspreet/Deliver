package jaspreet.deliver.xmppFramework;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

/**
 * Created by jaspret on 11/10/15.
 */
public class ConnectXmpp extends Thread {
    private XMPPTCPConnection xmpptcpConnection;

    public XMPPTCPConnection getXmpptcpConnection() {
        return xmpptcpConnection;
    }

    public void setXmpptcpConnection(XMPPTCPConnection xmpptcpConnection) {
        this.xmpptcpConnection = xmpptcpConnection;
    }

    public ConnectXmpp(XMPPTCPConnection xmpptcpConnection){
        setXmpptcpConnection(xmpptcpConnection);
    }

    @Override
    public void run() {
        super.run();

        try {
            getXmpptcpConnection().connect();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
}
