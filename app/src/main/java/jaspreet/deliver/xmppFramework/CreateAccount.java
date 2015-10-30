package jaspreet.deliver.xmppFramework;


import android.content.Context;
import android.content.Intent;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import  org.jivesoftware.smackx.iqregister.AccountManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jaspreet.deliver.database.Prefrences;
import jaspreet.deliver.utils.Util;

/**
 * Created by jaspret on 11/10/15.
 */
public class CreateAccount extends Thread {

    private XMPPTCPConnection xmpptcpConnection;
    private String email,password;
    private Prefrences prefrences;
    private AccountListener accountListener;

    public AccountListener getAccountListener() {
        return accountListener;
    }

    public void setAccountListener(AccountListener accountListener) {
        this.accountListener = accountListener;
    }

    public Prefrences getPrefrences() {

        return prefrences;
    }

    public void setPrefrences(Prefrences prefrences) {
        this.prefrences = prefrences;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        setPrefrences(Prefrences.getInstance(getContext()));
    }

    private Context context;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;

    }
    public void setEmail(String email) {
        this.email = email;
    }

    public XMPPTCPConnection getXmpptcpConnection() {
        return xmpptcpConnection;
    }

    public void setXmpptcpConnection(XMPPTCPConnection xmpptcpConnection) {
        this.xmpptcpConnection = xmpptcpConnection;
    }

    public CreateAccount(XMPPTCPConnection xmpptcpConnection,String email,String password){
        setXmpptcpConnection(xmpptcpConnection);
        setEmail(email);
        setPassword(password);

    }

    @Override
    public void run() {
        super.run();
        AccountManager accountManager = AccountManager.getInstance(getXmpptcpConnection());
        try {
//            getXmpptcpConnection().login("jaspreet","9319396142");
            accountManager.sensitiveOperationOverInsecureConnection(true);
            Map<String ,String> map=new HashMap<>();
            map.put("email",getEmail());
            accountManager.createAccount(Util.getUserName(getEmail()), getPassword(), map);
            getXmpptcpConnection().disconnect();
            getPrefrences().setUserName(Util.getUserName(getEmail()));
            getPrefrences().setPassword(getPassword());
            getPrefrences().setEmail(getEmail());
            getAccountListener().onSucess();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
            getAccountListener().onError();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
            if(e.getMessage().equals("XMPPError: conflict - cancel")) {
                getXmpptcpConnection().disconnect();
                doConnect();
                getPrefrences().setUserName(Util.getUserName(getEmail()));
                getPrefrences().setPassword(getPassword());
                getPrefrences().setEmail(getEmail());
                doLogin();
            }else
            getAccountListener().onError();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
            getAccountListener().onError();
        }


        Intent intent=new Intent(getContext(),XMPPService.class);
        getContext().startService(intent);
    }
    public void doConnect(){
        XmppConnection.getInstance().connect();
    }
    public void doLogin(){
        XmppConnection.getInstance().login(getAccountListener());
    }
}
