package io.toro.xmpp.connector

import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.SASLAuthentication
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.XMPPException
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.filetransfer.FileTransferListener
import org.jivesoftware.smackx.filetransfer.FileTransferManager
import org.jivesoftware.smackx.filetransfer.FileTransferRequest
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer

class RecevingEnd {
	static AbstractXMPPConnection connection
	
	
	static void setupConnection(String serviceName, String hostName, int portNum){
		XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder()
		.setServiceName(serviceName)
		.setHost(hostName)
		.setPort(portNum)
		.setDebuggerEnabled(true)
		.setSecurityMode(SecurityMode.disabled)
		SASLAuthentication.unBlacklistSASLMechanism("PLAIN")
		SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5")
		connection = new XMPPTCPConnection(config.build())
	}
	
	public void login(String userName, String password) throws XMPPException {
		try {
			connection.connect();
			connection.login(userName, password);
			} catch (SmackException e) {
				e.printStackTrace();
			}
	}
	
	static void receiveFile(){
		
		final FileTransferManager manager = FileTransferManager.getInstanceFor(connection);
		// Create the listener
		manager.addFileTransferListener(new FileTransferListener() {
			public void fileTransferRequest(FileTransferRequest request) {
			// Check to see if the request should be accepted
				IncomingFileTransfer transfer = request.accept();
				transfer.recieveFile(new File("/Users/johngarcia/Desktop/test_casesx"))
		}
		})
		Presence presence = new Presence(Presence.Type.available)
		connection.sendStanza(presence)
		final long start = System.nanoTime();
		while ((System.nanoTime() - start) / 1000000 < 20000) // do for 20 seconds
		{
		  Thread.sleep(500);
		}
	}
	
	static void main (String [] args){
		def a = new RecevingEnd()
		a.setupConnection('ChatBot','localhost', 5222)
		a.login('raizel','athesis04')
		a.receiveFile()
	}
}
