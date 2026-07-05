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

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def url = new URL("http://34.128.109.228:3000/send")
def conn = (HttpURLConnection) url.openConnection()
conn.setRequestMethod("POST")
conn.setRequestProperty("Content-Type", "application/json")
conn.setDoOutput(true)

// Kirim payload JSON
def payload = JsonOutput.toJson([
	nama: "Firmandhani",
	test: "Technical Test QA"
])
conn.getOutputStream().write(payload.getBytes("UTF-8"))

def responseCode = conn.getResponseCode()
println "API Response Code: " + responseCode

if (responseCode == 201) {
	def resText = conn.getInputStream().getText()
	println "API Response: " + resText
	def json = new JsonSlurper().parseText(resText)
	assert json.status == "success"
} else {
	println "Error: " + conn.getErrorStream().getText()
}
