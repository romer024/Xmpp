package io.toro.integrate.xmpp

import io.toro.xmpp.connector.XmppConnector

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import com.toro.esb.core.api.APIResponse
class XmppAdapter {
	@ResponseBody
	@RequestMapping (value='{domain}/messages', method=[RequestMethod.POST], produces=[ 'application/json' ])
	def sendChat(
		String alias,
		String id,
		String message		
		){
		def con = new XmppConnector()
		con.contactXmpp(alias)
		con.sendMessage(id,message)
		return new APIResponse('Successs!')
	}
		
	@ResponseBody
	@RequestMapping (value='{domain}/messages', method=[RequestMethod.POST], produces=[ 'application/json' ])
	def statusUpdate(
		String alias,
		String status){
		def con = new XmppConnector()
		con.contactXmpp(alias)
		con.changeStatus(status)
		return new APIResponse('Successs!')
	}
}
