package jaspreet.deliver.xmppFramework;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

/**
 * Created by jaspret on 11/10/15.
 */
public class XMPPlogin extends Thread{
    private String userName;
    private String password;
    private XMPPTCPConnection xmpptcpConnection;
    private String resource;
    private AccountListener accountListener;

    public AccountListener getAccountListener() {
        return accountListener;
    }

    public void setAccountListener(AccountListener accountListener) {
        this.accountListener = accountListener;
    }
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public XMPPTCPConnection getXmpptcpConnection() {
        return xmpptcpConnection;
    }

    public void setXmpptcpConnection(XMPPTCPConnection xmpptcpConnection) {
        this.xmpptcpConnection = xmpptcpConnection;
    }
    public XMPPlogin(XMPPTCPConnection xmpptcpConnection,String userName,String password,String resource){
        setXmpptcpConnection(xmpptcpConnection);
        setUserName(userName);
        setPassword(password);
        setResource(resource);
    }
    public XMPPlogin(XMPPTCPConnection xmpptcpConnection,String userName,String password,String resource,AccountListener accountListener){
        setAccountListener(accountListener);
        setXmpptcpConnection(xmpptcpConnection);
        setUserName(userName);
        setPassword(password);
        setResource(resource);
    }
    @Override
    public void run() {
        super.run();
        try {
            SASLAuthentication.unBlacklistSASLMechanism("PLAIN");
            SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");
            if(!getUserName().equals(""))
            getXmpptcpConnection().login(getUserName(),getPassword(),getResource());
            else
            getXmpptcpConnection().loginAnonymously();

            if(getAccountListener()!=null) {
                getAccountListener().onSucess();
            }
        } catch (XMPPException e) {
            e.printStackTrace();
            if(getAccountListener()!=null)
            getAccountListener().onError();
        } catch (SmackException e) {
            e.printStackTrace();
            if(getAccountListener()!=null)
            getAccountListener().onError();
        } catch (IOException e) {
            e.printStackTrace();
            if(getAccountListener()!=null)
            getAccountListener().onError();
        }

    }
}
