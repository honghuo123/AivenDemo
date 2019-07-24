package aiven.demo.java;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class AivenDemo {
	private final String topic;
	private final Properties props;

	public AivenDemo(String brokers) {
		this.topic = "default";

		String serializer = StringSerializer.class.getName();
		String deserializer = StringDeserializer.class.getName();
		props = new Properties();
		props.put("bootstrap.servers", brokers);
		props.put("key.deserializer", deserializer);
		props.put("value.deserializer", deserializer);
		props.put("key.serializer", serializer);
		props.put("value.serializer", serializer);
		props.put("security.protocol", "SSL");
		props.put("ssl.endpoint.identification.algorithm", "");
		props.put("ssl.truststore.location", "/home/hongh/workspace/Aiven/keys/client.truststore.jks");
		props.put("ssl.truststore.password", "secret");
		props.put("ssl.keystore.type", "PKCS12");
		props.put("ssl.keystore.location", "/home/hongh/workspace/Aiven/keys/client.keystore.p12");
		props.put("ssl.keystore.password", "secret");
		props.put("ssl.key.password", "secret");
		props.put("group.id", "demo-group");
	}

	@SuppressWarnings("deprecation")
	public void consume() {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		long id = 0;
		consumer.subscribe(Arrays.asList(topic));

		// PostgreSQL
		try {

			System.out.println("Java JDBC PostgreSQL Example");

			// Load JDBC driver
			Class.forName("org.postgresql.Driver");

			Connection connection = DriverManager.getConnection("jdbc:postgresql://pg-207b2610-hong-9c10.aivencloud.com:16762/defaultdb?sslmode=require", "avnadmin",
					"l1ysowdbf4qj1dab");
			System.out.println("Connected to PostgreSQL database!");
			
			//Create table record
			String createTableSQL = "CREATE TABLE IF NOT EXISTS record (\"id\" serial PRIMARY KEY, \"offset\" VARCHAR (50) NOT NULL, \"key\" VARCHAR (50) NOT NULL, \"value\" VARCHAR (355) NOT NULL);";

			PreparedStatement pstmt = connection.prepareStatement(createTableSQL);
			pstmt.executeUpdate();
			
			for (int i=0;i<10;i++) {
				ConsumerRecords<String, String> records = consumer.poll(1000);
				
				for (ConsumerRecord<String, String> record : records) {
					System.out.printf("%s [%d] offset=%d, key=%s, value=\"%s\"\n", record.topic(), record.partition(),
							record.offset(), record.key(), record.value());

					String SQL = "insert into record (\"offset\", \"key\", \"value\") values (?,?,?);";

					pstmt = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

					pstmt.setString(1, ""+record.offset());
					pstmt.setString(2, record.key());
					pstmt.setString(3, record.value());

					int affectedRows = pstmt.executeUpdate();
					// check the affected rows
					if (affectedRows > 0) {
						// get the ID back
						try (ResultSet rs = pstmt.getGeneratedKeys()) {
							if (rs.next()) {
								id = rs.getLong(1);
								System.out.println("Record #"+id+" inserted");
							}
						} catch (SQLException ex) {
							System.out.println(ex.getMessage());
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBC driver not found.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}

	}

	public void produce() {
		Thread one = new Thread() {
			public void run() {
				try {
					Producer<String, String> producer = new KafkaProducer<>(props);

					for (int i = 0; i < 10; i++) {
						Date d = new Date();
						producer.send(new ProducerRecord<>(topic, Integer.toString(i), d.toString()));
						Thread.sleep(1000);

						System.out.println("Message " + i + " produced");
					}
				} catch (InterruptedException v) {
					System.out.println(v);
				}
			}
		};
		one.start();
	}

	public static void main(String[] args) throws InterruptedException {
		String brokers = "kafka-3a04cebb-hong-9c10.aivencloud.com:16764";
		
		System.out.println("Aiven Technical Exercise: Demo Kafka with PostgreSQL");
		AivenDemo c = new AivenDemo(brokers);
		c.produce();
		c.consume();

	}
}
