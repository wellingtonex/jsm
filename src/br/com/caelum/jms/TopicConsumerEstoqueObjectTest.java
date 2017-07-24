package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.caelum.modelo.Pedido;

public class TopicConsumerEstoqueObjectTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		try {
			InitialContext context = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
			Connection connection = connectionFactory.createConnection();
			connection.setClientID("estoque");
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Topic topico =  (Topic) context.lookup("loja");
			MessageConsumer consumer = session.createDurableSubscriber(topico,"estoque.controle");
			
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					try {
						ObjectMessage objectMessage = (ObjectMessage) message;
						Pedido pedido = (Pedido) objectMessage.getObject();
						System.out.println(pedido);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
