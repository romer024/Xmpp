package io.toro.xmpp.connector

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

class ZendeskDummyConnector {
	
	static def createTicket(String[] a){
			def client = new HTTPBuilder( 'https://toroiohelp.zendesk.com/api/v2/tickets.json' )
			client.request( Method.POST, ContentType.JSON ){
				headers.'Authorization' = 'Bearer cd5e1e8cf2b632dc59010f7f2a553a9c00042c643afcf4324a8e0324833f0871'
				body = [ticket:[subject:a[1], comment:[body:a[2]]]]
				}
		}

}
