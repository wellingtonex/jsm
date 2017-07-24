package br.com.caelum.jms;

import java.io.File;
import java.io.StringWriter;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

public class TopicProducerObjetTest {

	public static void main(String[] args) {
		
		try {
			InitialContext context = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
			Connection connection = connectionFactory.createConnection();
			connection.start();
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination topico = (Destination) context.lookup("loja");
			MessageProducer producer = session.createProducer(topico);
			
			Pedido pedido = new PedidoFactory().geraPedidoComValores();
//			StringWriter xml = new StringWriter();
//			JAXB.marshal(pedido, xml);
//			System.out.println(xml.toString());
			Message messagem = session.createObjectMessage(pedido);
			producer.send(messagem );
			
			session.close();
			connection.close();
			context.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
