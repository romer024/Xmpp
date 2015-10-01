package io.toro.xmpp.connector

import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.SASLAuthentication
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.XMPPException
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.jingleold.JingleManager
import org.jivesoftware.smackx.jingleold.JingleSession
import org.jivesoftware.smackx.jingleold.JingleSessionRequest
import org.jivesoftware.smackx.jingleold.listeners.JingleSessionRequestListener
import org.jivesoftware.smackx.jingleold.media.JingleMediaManager
import org.jivesoftware.smackx.jingleold.mediaimpl.multi.MultiMediaManager
import org.jivesoftware.smackx.jingleold.nat.BasicTransportManager

class VoiceReciever {
	
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
			connection.connect()
			connection.login(userName, password)
			} catch (SmackException e) {
				e.printStackTrace()
			}
	}
	static void acceptVoipSession(){
		List<JingleMediaManager> mediaManagers = new ArrayList<JingleMediaManager>()
		mediaManagers.add(new MultiMediaManager(new BasicTransportManager()))
		JingleManager jm = new JingleManager(connection, mediaManagers)
		jm.addCreationListener()
		jm.addJingleSessionRequestListener(new JingleSessionRequestListener() {
			public void sessionRequested(JingleSessionRequest request) {

				try {
			
					JingleSession incoming = request.accept();
						println 'acc'
			
					incoming.startIncoming();
						println 'inc'
				}
				catch (XMPPException e) {
					println e
				}

			}
		})
		final long start = System.nanoTime()
		while ((System.nanoTime() - start) / 1000000 < 20000) // do for 20 seconds
		{
		  Thread.sleep(500)
		}
	}
	
	public static void main(String [] args){
		def voip = new VoiceReciever()
		voip.setupConnection('ChatBot','localhost', 5222)
		voip.login('raizel','athesis04')
		voip.acceptVoipSession()
	}
}
