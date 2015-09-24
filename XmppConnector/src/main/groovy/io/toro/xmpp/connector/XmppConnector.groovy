package io.toro.xmpp.connector

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration

class XmppConnector {
	public static void main(String [] args) {
	//ConnectionConfiguration conf = new ConnectionConfiguration("localhost", 5222)
		XMPPTCPConnectionConfiguration conf = XMPPConnectionConfiguration.builder()
		XMPPTCPConnection connection = new XMPPTCPConnection(conf);
		connection.connect();
		connection.login("choi", "spiderchoi");
		
	}
	//conn.connect();
}
