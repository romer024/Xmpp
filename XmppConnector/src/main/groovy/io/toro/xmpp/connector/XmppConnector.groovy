package io.toro.xmpp.connector

import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.SASLAuthentication
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.XMPPException
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode
import org.jivesoftware.smack.chat.Chat
import org.jivesoftware.smack.chat.ChatManager
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.RosterEntry
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration

class XmppConnector {
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

	public void showChatContacts(){
		Roster roster = Roster.getInstanceFor(connection);
		Collection<RosterEntry> contacts = roster.getEntries()
		println(contacts)
	}
	
	public void sendMessage(String id, String message){
		ChatManager chatmanager = ChatManager.getInstanceFor(connection)
		Chat newChat = chatmanager.createChat('raizel@john-garcia.toro.dev')
		newChat.sendMessage(message)
	}
	
	public void changeStatus(){
		Presence presence = new Presence(Presence.Type.available)
		presence.setStatus("Online")
		connection.sendStanza(presence)
	}

	public static void main(String [] args){
		def o = new XmppConnector()
		o.setupConnection('ChatBot','localhost', 5222)
		o.login('moderator','Toro_dev')
		o.showChatContacts()
		//o.sendMessage('raizel@john-garcia.toro.dev','TESTFollow this schema for Creating Issue:\n"createIssue"|{ProjectID}|{Summary}|{Description}')
		//============================================================

//		chatmanager.addChatListener()
//		Presence presence = new Presence(Presence.Type.available)
//		connection.sendStanza(presence)
//		Chat newChat = chatmanager.createChat("raizel@john-garcia.toro.dev")
//		newChat.sendMessage('Follow this schema for Creating Issue:\n"createIssue"|{ProjectID}|{Summary}|{Description}')
	}
}
