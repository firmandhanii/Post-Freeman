import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.ConsumerRecord
import java.util.Properties
import java.time.Duration

Properties props = new Properties()
props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "34.128.109.228:9092")
// Gunakan group ID unik agar selalu baca dari awal
props.put(ConsumerConfig.GROUP_ID_CONFIG, "katalon-group-" + System.currentTimeMillis())
props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)
consumer.subscribe(["uji-kemampuan-teknis"])

println "Membaca data dari Kafka..."
try {
	ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10))
	for (ConsumerRecord<String, String> record : records) {
		println "Message dari Kafka: " + record.value()
	}
} catch (Exception e) {
	println "[ERROR] Gagal: " + e.getMessage()
} finally {
	consumer.close()
}
