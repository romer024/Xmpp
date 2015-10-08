package io.toro.xmpp.connector

import java.util.List;

import org.jivesoftware.smack.AbstractXMPPConnection
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.SASLAuthentication
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.XMPPException
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode
import org.jivesoftware.smack.chat.Chat
import org.jivesoftware.smack.chat.ChatManager
import org.jivesoftware.smack.chat.ChatManagerListener
import org.jivesoftware.smack.chat.ChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Presence
import org.jivesoftware.smack.roster.Roster
import org.jivesoftware.smack.roster.RosterEntry
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jivesoftware.smackx.filetransfer.FileTransferManager
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer
import org.jivesoftware.smackx.jingleold.JingleManager
import org.jivesoftware.smackx.jingleold.JingleSession
import org.jivesoftware.smackx.jingleold.JingleSessionRequest
import org.jivesoftware.smackx.jingleold.listeners.JingleSessionRequestListener
import org.jivesoftware.smackx.jingleold.media.JingleMediaManager
import org.jivesoftware.smackx.jingleold.mediaimpl.jmf.JmfMediaManager
import org.jivesoftware.smackx.jingleold.mediaimpl.jspeex.SpeexMediaManager
import org.jivesoftware.smackx.jingleold.mediaimpl.multi.MultiMediaManager
import org.jivesoftware.smackx.jingleold.mediaimpl.sshare.ScreenShareMediaManager
import org.jivesoftware.smackx.jingleold.mediaimpl.test.TestMediaManager
import org.jivesoftware.smackx.jingleold.nat.BasicTransportManager
import org.jivesoftware.smackx.jingleold.nat.ICETransportManager
import org.jivesoftware.smackx.muc.MultiUserChat
import org.jivesoftware.smackx.muc.MultiUserChatManager
import org.jivesoftware.smackx.xdata.Form
import org.jivesoftware.smackx.xdata.FormField.Type
import org.jivesoftware.smackx.xdata.packet.DataForm

class XmppConnector {
	static AbstractXMPPConnection connection
	
	static String saveCredentials(String alias, String username, String password){
		String credentials = "${alias}~${username}~${password}"
		"xmpp.${alias}".saveTOROProperty(credentials)
		'Xmpp Credentials Saved!'
	}
	
	static List<String> validateCredentials(String alias) {
		String sets = "xmpp.${alias}".getTOROProperty()
		String [] credentials = sets.split('~')
		credentials as List
	}
	
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
	
	static void setupConnection(String username, String password, String server){
		//TODO
		AbstractXMPPConnection conn = new XMPPTCPConnection(username, password, server)
		conn.connect()
	}
	
    public void login(String userName, String password) throws XMPPException {
		try {
			connection.connect()
			connection.login(userName, password)
			} catch (SmackException e) {
				e.printStackTrace()
			}
    }
	
	
	public void contactXmpp(String alias){
		List credentials =  validateCredentials(alias)
		setupConnection('chatBot','localhost',5222)
		login(credentials[1],credentials[2])
	}
	
	public void showChatContacts(){
		Roster roster = Roster.getInstanceFor(connection);
		Collection<RosterEntry> contacts = roster.getEntries()
		println(contacts)
	}
	
	public void sendMessage(String id, String message){
		ChatManager chatmanager = ChatManager.getInstanceFor(connection)
		Chat newChat = chatmanager.createChat(id)
		newChat.sendMessage(message)
		return null
	}
	
	public changeStatus(String status){
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
		
		final long start = System.nanoTime()
		while ((System.nanoTime() - start) / 1000000 < 20000) // do for 20 seconds
		{
		  Thread.sleep(500)
		}
	}
	
	static void sendFile(){
		FileTransferManager ftm = new FileTransferManager(connection)
		OutgoingFileTransfer transfer = ftm.createOutgoingFileTransfer('raizel@john-garcia.toro.dev/sparkweb')
		transfer.sendFile(new File('/Users/johngarcia/Desktop/test_casesx'), 'test cases')
		//sleep(5000)
		println transfer.getStatus()
	}
	
	static void createMultiUserChatRoom(){
		MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
		MultiUserChat muc = manager.getMultiUserChat('newRoom@conference.john-garcia.toro.dev')
		muc.create('TestRoom')
		Form mucForm = new Form(new DataForm())
		muc.sendConfigurationForm(new Form(DataForm.Type.submit));
		final long start = System.nanoTime()
	}

	static void joinRoom(String roomAddress){
		MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
		MultiUserChat muc = manager.getMultiUserChat(roomAddress)
		muc.join('moderator','Toro_dev')
//		final long start = System.nanoTime()
//		while ((System.nanoTime() - start) / 1000000 < 20000) // do for 20 seconds
//		{
//		  Thread.sleep(500)
//		}
	}
	
	static void muChat(){
		MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection)
		MultiUserChat muc = manager.getMultiUserChat('newRoom@conference.john-garcia.toro.dev/sparkweb')
		muc.sendMessage('Hello there!')
	}
	
	static void outgoingVoipSession() {
		List<JingleMediaManager> mediaManagers = new ArrayList<JingleMediaManager>()
		mediaManagers.add(new MultiMediaManager(new BasicTransportManager()))
		JingleManager jm = new JingleManager(connection, mediaManagers)
     	JingleSession outgoing = jm.createOutgoingJingleSession('raizel@john-garcia.toro.dev')
		outgoing.startOutgoing()
		println outgoing.sessionState
	}
		
	public static void main(String [] args){
		def jbbr = new XmppConnector()
		//jbbr.contactXmpp('toroio')
		
//		jbbr.setupConnection('ChatBot','localhost', 5222)
//		jbbr.login('moderator','Toro_dev')		
		//jbbr.changeStatus('updating')
		
		//jbbr.saveCredentials('toroio','moderator', 'Toro_dev')
		//jbbr.sendMessage('raizel@john-garcia.toro.dev','hi there')
		//jbbr.outgoingVoipSession()
		//jbbr.CreateMultiUserChatRoom()
		//jbbr.joinRoom('newRoom@conference.john-garcia.toro.dev')
		//jbbr.muChat()

		//jbbr.sendFile()
		//jbbr.getMessage()
		//jbbr.showChatContacts()
		//jbbr.changeStatus('I\'m Available')
		//jbbr.sendMessage('raizel@john-garcia.toro.dev','Follow this schema for Creating Issue:\n"createIssue"|{ProjectID}|{Summary}|{Description}')
		
		//TODO: jbbr.voIPSession()
		//============================================================

	}
}
