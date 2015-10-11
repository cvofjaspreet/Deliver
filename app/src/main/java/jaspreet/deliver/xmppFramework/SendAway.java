package jaspreet.deliver.xmppFramework;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

/**
 * Created by jaspret on 11/10/15.
 */
public class SendAway extends Thread {
    private XMPPTCPConnection xmpptcpConnection;

    public XMPPTCPConnection getXmpptcpConnection() {
        return xmpptcpConnection;
    }

    public void setXmpptcpConnection(XMPPTCPConnection xmpptcpConnection) {
        this.xmpptcpConnection = xmpptcpConnection;
    }

    public SendAway(XMPPTCPConnection xmpptcpConnection){
        setXmpptcpConnection(xmpptcpConnection);
    }

    @Override
    public void run() {
        super.run();
        Presence presence=new Presence(Presence.Type.available,"away",MAX_PRIORITY, Presence.Mode.away);
        try {
            getXmpptcpConnection().sendPacket(presence);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
