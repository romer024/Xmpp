package io.toro.xmpp.connector;


import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener
import org.jivesoftware.smack.packet.Message;

public class MyNewMessageListener implements ChatMessageListener {
				@Override
				public void processMessage(Chat chat, Message message) {
						  if( message.getBody() =~ /\\help\szendesk/)
						 {
							 def jbbr = new XmppConnector()
							 jbbr.setupConnection('ChatBot','localhost', 5222)
							 jbbr.login('moderator','Toro_dev')
							 jbbr.sendMessage('raizel@john-garcia.toro.dev','Follow this schema for Creating Zendesk Ticket:\n"!createdTicket"|{Subject}|{Details}')
							 jbbr.connection.disconnect()
						 }
						 if( message.getBody() =~ /\!/){
							 def cred = message.getBody().split(/\|/)
							 def jira = new ZendeskDummyConnector()
							 jira.createTicket(cred)
						 }
						 else
						 println message.getBody()
				 }	
 }

