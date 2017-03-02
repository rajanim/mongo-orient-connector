/**
 * 
 */
package com.bio.cip.indexer.util;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;

import com.bio.cip.constants.ApplicationConstants;

/**
 * @author sachchidanand.singh
 * 
 */
public class MQUtil {

	private static final ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ApplicationConstants.ACTIVE_MQ_URL);

	public static void pushMsg(String msg, String qName) {
		Connection connection = null;
		try {
			connection = factory.createConnection();
			// System.out.println("created connection '" + qName);
			connection.start();
			// System.out.println("started connection '" + qName);
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			if (StringUtils.isNotBlank(qName)) {
				 Destination destination = session.createQueue(qName);
				   MessageProducer producer = session.createProducer(destination);
	                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				//MessageProducer producer = session.createProducer(session.createQueue(qName));

				TextMessage message = session.createTextMessage(msg);
				// System.out.println("sending mesage'");
				producer.send(message);
				System.out.println("Sent message  '" + qName + " : " + message.getText() + "'");
			}
			session.close();

		} catch (JMSException jmsEx) {
			throw new RuntimeException("Failed to post jms msg " + jmsEx);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ignoreEx) {
				}
			}
		}
	}

}
