package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.InitialContext;

public class ConsumidorTest {

	public static void main(String[] args) {
		
		try {
			InitialContext context = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
			Connection connection = connectionFactory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination fila = (Destination) context.lookup("financeiro");
			MessageConsumer consumer = session.createConsumer(fila);
			
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					System.out.println(message);
					
				}
			});
			
			new Scanner(System.in).nextLine();
			
			session.close();
			connection.close();
			context.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
