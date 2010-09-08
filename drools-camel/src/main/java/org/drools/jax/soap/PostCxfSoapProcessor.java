/**
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * 
 */
package org.drools.jax.soap;

import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPMessage;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.drools.core.util.StringUtils;

public class PostCxfSoapProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		InputStream is = (InputStream) exchange.getIn().getBody();
		if (is != null) {
			SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
			SOAPBody body = soapMessage.getSOAPPart().getEnvelope().getBody();
			QName payloadName = new QName("http://soap.jax.drools.org/", "executeResponse", "ns1");
			SOAPBodyElement payload = body.addBodyElement(payloadName);
			payload.addChildElement("responseType");
			payload.addTextNode(StringUtils.toString( is ));
			exchange.getOut().setBody( soapMessage );
		}
	}        

}