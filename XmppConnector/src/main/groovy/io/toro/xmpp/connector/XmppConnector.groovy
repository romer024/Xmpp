package io.toro.xmpp.connector

import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.SASLAuthentication
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.XMPPException
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode
import org.jivesoftware.smack.chat.Chat
import org.jivesoftware.smack.chat.ChatManager
import org.jivesoftware.smack.chat.ChatManagerListener
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.RosterEntry
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.filetransfer.FileTransferManager
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer

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
	
	public void changeStatus(String status){
		Presence presence = new Presence(Presence.Type.available)
		presence.setStatus(status)
		connection.sendStanza(presence)
	}
	
	public void getMessage(){
		ChatManager chatManager = ChatManager.getInstanceFor(connection);
		def bod = chatManager.addChatListener(
			new ChatManagerListener() {
				@Override
				public void chatCreated(Chat chat, boolean createdLocally)
				{
					if (!createdLocally)
						chat.addMessageListener(new MyNewMessageListener());;
				}
			});
		
		final long start = System.nanoTime();
		while ((System.nanoTime() - start) / 1000000 < 20000) // do for 20 seconds
		{
		  Thread.sleep(500);
		}
	}
	
	static void sendFile(){
		FileTransferManager ftm = new FileTransferManager(connection)
		Roster roster = new Roster(connection)
		OutgoingFileTransfer transfer = ftm.createOutgoingFileTransfer('raizel@john-garcia.toro.dev/Smack')
		transfer.sendFile(new File('/Users/johngarcia/Desktop/test_casesx'), 'test cases')
	}
	
	public static void main(String [] args){
		def jbbr = new XmppConnector()
		jbbr.setupConnection('ChatBot','localhost', 5222)
		jbbr.login('moderator','Toro_dev')
		jbbr.sendFile()
		//jbbr.getMessage()
		
		//jbbr.showChatContacts()
		//jbbr.changeStatus('I\'m Available')
		//jbbr.sendMessage('raizel@john-garcia.toro.dev','Follow this schema for Creating Issue:\n"createIssue"|{ProjectID}|{Summary}|{Description}')
		
		//============================================================

	}
}
