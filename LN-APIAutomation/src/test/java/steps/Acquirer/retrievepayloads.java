package steps.Acquirer;

import Builders.RetrievePayloadsBuilder;
import Database.DatabaseHelper;
import Utils.AcquirerDetails;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.messages.internal.com.google.protobuf.Api;
import org.apiguardian.api.API;
import org.testng.Assert;
import org.testng.annotations.Test;
import Utils.ReusableStrings;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import Utils.TestConfig;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;


public class retrievepayloads {


    // Scenario 1
    @Given("I setup Acquirer Processor QR config")
    public void iSetupAcquirerProcessorQRConfig() {
        //Get the API key for 500012
        String APIkey = AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY;
        RetrievePayloadsBuilder.RetrieveQRPayloadTypesAPI(APIkey);

    }

    @Then("I should see all the supported QR payloads")
    public void iShouldSeeAllTheSupportedQRPayloads() throws Exception {
        ArrayList<String> payloads = DatabaseHelper.getPayloadCode(AcquirerDetails.ACQUIRER_PARTNER_ID);
        //payloads.forEach((n) -> System.out.println(n));
        boolean payloadChecker = false;
        int payLoadTypeSize=RetrievePayloadsBuilder.getResponseRetrievePayloadTypes().then().extract().path("payload_types.'size'");//number of payload types retrieve
        String payLoadCode,name,imageUrl;
        //assert a 'value' exists for each 'key'
        for(int i=0;i<payLoadTypeSize;i++) {
            payLoadCode=RetrievePayloadsBuilder.getResponseRetrievePayloadTypes().then().extract().path("payload_types["+i+"].'payload_code'");
            name=RetrievePayloadsBuilder.getResponseRetrievePayloadTypes().then().extract().path("payload_types["+i+"].'name'");
            imageUrl=RetrievePayloadsBuilder.getResponseRetrievePayloadTypes().then().extract().path("payload_types["+i+"].'image_url'");

            Assert.assertNotNull(payLoadCode);
            Assert.assertNotNull(name);
            Assert.assertNotNull(imageUrl);

            if(payloads.contains(payLoadCode)){
                payloadChecker = true;
            }

        }
        // Check all payloads match with database
        Assert.assertEquals(payloadChecker,true);
    }
   // Scenario 2
   @Given("I do not setup Acquirer Processor QR config")
   public void iDoNotSetupAcquirerProcessorQRConfig() {
       String APIkey = AcquirerDetails.EMPTY_PAYLOAD_APIKEY;
        RetrievePayloadsBuilder.RetrieveQRPayloadTypesAPI(APIkey);

   }

    @Then("I should not see any supported QR payloads")
    public void iShouldNotSeeAnySupportedQRPayloads() throws Exception {
        ArrayList<String> payloads = DatabaseHelper.getPayloadCode(AcquirerDetails.EMPTY_PAYLOAD_PARTNERID);
        //payloads.forEach((n) -> System.out.println(n));
        boolean payloadChecker = false;

        int payLoadTypeSize=RetrievePayloadsBuilder.getResponseRetrievePayloadTypes().then().extract().path("payload_types.'size'");//number of payload types retrieve
        String payLoadCode,name,imageUrl;

        //assert a 'value' exists for each 'key'
        for(int i=0;i<payLoadTypeSize;i++) {
            payLoadCode=RetrievePayloadsBuilder.getResponseRetrievePayloadTypes().then().extract().path("payload_types["+i+"].'payload_code'");
            name=RetrievePayloadsBuilder.getResponseRetrievePayloadTypes().then().extract().path("payload_types["+i+"].'name'");
            imageUrl=RetrievePayloadsBuilder.getResponseRetrievePayloadTypes().then().extract().path("payload_types["+i+"].'image_url'");

            if(payloads.contains(payLoadCode)){
                payloadChecker = true;
            }
            Assert.assertNull(payLoadCode);
            Assert.assertNull(name);
            Assert.assertNull(imageUrl);
        }
        Assert.assertFalse(payloadChecker);

    }

}
