package io.toro.xmpp.connector

//import org.jivesoftware.smack.Chat
//import org.jivesoftware.smack.ChatManager
import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.XMPPException
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration

class XmppConnector {
	static AbstractXMPPConnection  connection
	
    public void login(String userName, String password) throws XMPPException
    {
    XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();

    //this is server name in Open fire
    config.setServiceName('localhost');
    //this is host name in Open fire
    config.setHost('localhost');
    config.setPort(5222);
    config.setDebuggerEnabled(true)
	config.setSecurityMode(SecurityMode.disabled);
    connection = new XMPPTCPConnection(config.build());
 
    try {
	 connection.connect();
     connection.login(userName, password);
	} catch (SmackException | IOException e) {
  	// TODO Auto-generated catch block
  	e.printStackTrace();
  		}
    }
	
	public static void main(String [] args){
		def o = new XmppConnector();
		o.login('admin','raIzel04');
	}
}
