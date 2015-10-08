package io.toro.integrate.xmpp

import io.toro.xmpp.connector.XmppConnector

import org.springframework.web.bind.annotation.ResponseBody

class XmppRegister {
	@ResponseBody
	public String SaveCredentials(String alias, String userName, String password) {
		XmppConnector.saveCredentials(alias, userName, password)
		'Ok, Xmpp Credentials has been Saved!'
	}
}
