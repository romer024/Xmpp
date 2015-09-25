package io.toro.xmpp.connector;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.sasl.javax.SASLDigestMD5Mechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
 
public class TryXm {
 
  AbstractXMPPConnection  connection;
 
    public void login(String userName, String password) throws XMPPException
    {
    XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();
    config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
    //this is server name in Open fire
    config.setServiceName("servername.com");
    config.setUsernameAndPassword("user","user");
    //this is host name in Open fire
    config.setHost("localhost");
    config.setPort(5222);
    config.setDebuggerEnabled(false);
    SASLMechanism mechanism = new SASLDigestMD5Mechanism();
    SASLAuthentication.registerSASLMechanism(mechanism);
    SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
    SASLAuthentication.unBlacklistSASLMechanism("DIGEST-MD5");
    config.setSecurityMode(SecurityMode.disabled);
    connection = new XMPPTCPConnection(config.build());
 

    try {
		connection.connect();
		connection.login(userName, password);
	} catch (SmackException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
    }
    
    public static void main(String [] args){
    	TryXm tr = new TryXm();
    		try {
				tr.login("choi", "spiderchoi");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
}
