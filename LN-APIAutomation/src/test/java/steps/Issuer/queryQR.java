package steps.Issuer;

import Builders.QueryQRBuilder;
import Utils.ErrorCodes;
import Utils.IssuerDetails;
import Utils.ReusableStrings;
import com.google.gson.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import static Builders.QueryQRBuilder.initializeQueryQRRequestBody;
import static Builders.QueryQRBuilder.retrieveQRData;

public class queryQR {
    private JsonObject requestParamsQR = new JsonObject();
    private String jsonObjValuesArr[]=new String[3];
    private String partnerID,responseQRData,createdTime;

    public static void processQuery(){

    }
    @Given("the issuer processing ID is {string}")
    public void theIssuerProcessingIDIs(String partnerID) {
        QueryQRBuilder.initializeGenerateDynamicQR();
        jsonObjValuesArr[0]= partnerID;

    }

    @When("the QR data is retrieved from the generated Dynamic QR")
    public void theQRDataIsRetrievedFromTheGeneratedDynamicQR() {
        responseQRData = QueryQRBuilder.retrieveQRData();
        createdTime= QueryQRBuilder.retrieveDatetime();
        //System.out.println(responseQRData);

        jsonObjValuesArr[1]=responseQRData;
        jsonObjValuesArr[2]= createdTime;
        requestParamsQR = QueryQRBuilder.initializeQueryQRRequestBody(jsonObjValuesArr);
        //System.out.println(requestParamsQR);
        QueryQRBuilder.callQueryDynamicQRAPI(IssuerDetails.ISSUER_API_KEY,IssuerDetails.ISSUER_API_SECRET,requestParamsQR);

    }

    @Then("QR has been queried successfully")
    public void qrHasBeenQueriedSuccessfully() {
        String response_code;
        response_code=QueryQRBuilder.getResponseQueryDynamicQR().then().extract().path("response_code");
        Assert.assertEquals(response_code,"0");
    }
    @Given("the issuer processing ID {string} does not exists")
    public void theIssuerProcessingIDDoesNotExists(String partnerID) {
        QueryQRBuilder.initializeGenerateDynamicQR();
        jsonObjValuesArr[0]= partnerID;
    }
    @Then("the error message telling that the partner ID is invalid will appear")
    public void theErrorMessageTellingThatThePartnerIDIsInvalidWillAppear() {
        QueryQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(QueryQRBuilder.getActualType(), "error","Test Case: queryDynamicQRWithInvalidPartnerID");
        Assert.assertEquals(QueryQRBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: queryDynamicQRWithInvalidPartnerID");
        Assert.assertEquals(QueryQRBuilder.getActualErrorDescription(),"partner_id"+" is invalid","Test Case: queryDynamicQRWithInvalidPartnerID");
    }


    @Given("the qrPayload {string} does not exists")
    public void theQrPayloadDoesNotExists(String qrPayload) {
        QueryQRBuilder.initializeGenerateDynamicQR();

        partnerID = IssuerDetails.ISSUER_PARTNER_ID;
        responseQRData = qrPayload;
        createdTime= QueryQRBuilder.retrieveDatetime();
        //System.out.println(responseQRData);

        jsonObjValuesArr[0]= partnerID;
        jsonObjValuesArr[1]= responseQRData;
        jsonObjValuesArr[2]= createdTime;
        requestParamsQR = QueryQRBuilder.initializeQueryQRRequestBody(jsonObjValuesArr);
        System.out.println(requestParamsQR);
        QueryQRBuilder.callQueryDynamicQRAPI(IssuerDetails.ISSUER_API_KEY,IssuerDetails.ISSUER_API_SECRET,requestParamsQR);

    }

    @Then("the error message telling that the QR Payload is invalid will appear")
    public void theErrorMessageTellingThatTheQRPayloadIsInvalidWillAppear() {
        String response_code;
        response_code=QueryQRBuilder.getResponseQueryDynamicQR().then().extract().path("response_code");
        Assert.assertEquals(response_code,"3");

    }

    @Given("the datetime {string} is in the wrong format")
    public void theDatetimeIsInTheWrongFormat(String datetime) {
        QueryQRBuilder.initializeGenerateDynamicQR();

        partnerID = IssuerDetails.ISSUER_PARTNER_ID;
        responseQRData = QueryQRBuilder.retrieveQRData();
        createdTime= datetime;
        //System.out.println(responseQRData);

        jsonObjValuesArr[0]= partnerID;
        jsonObjValuesArr[1]= responseQRData;
        jsonObjValuesArr[2]= createdTime;
        requestParamsQR = QueryQRBuilder.initializeQueryQRRequestBody(jsonObjValuesArr);
        System.out.println(requestParamsQR);
        QueryQRBuilder.callQueryDynamicQRAPI(IssuerDetails.ISSUER_API_KEY,IssuerDetails.ISSUER_API_SECRET,requestParamsQR);

    }

    @Then("the error message telling that the datetime is invalid will appear")
    public void theErrorMessageTellingThatTheDatetimeIsInvalidWillAppear() {
        QueryQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(QueryQRBuilder.getActualType(), "error","Test Case: queryDynamicQRWithInvalidDateTime");
        Assert.assertEquals(QueryQRBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: queryDynamicQRWithInvalidDateTime");
        Assert.assertEquals(QueryQRBuilder.getActualErrorDescription(),"datetime"+" is invalid","Test Case: queryDynamicQRWithInvalidDateTime");

    }

    @Given("the issuer processing ID is unknown")
    public void theIssuerProcessingIDIsUnknown() {
        QueryQRBuilder.initializeGenerateDynamicQR();
        partnerID = "";
        responseQRData = QueryQRBuilder.retrieveQRData();
        createdTime= QueryQRBuilder.retrieveDatetime();
        //System.out.println(responseQRData);

        jsonObjValuesArr[0]= partnerID;
        jsonObjValuesArr[1]= responseQRData;
        jsonObjValuesArr[2]= createdTime;
        requestParamsQR = QueryQRBuilder.initializeQueryQRRequestBody(jsonObjValuesArr);
        System.out.println(requestParamsQR);
        QueryQRBuilder.callQueryDynamicQRAPI(IssuerDetails.ISSUER_API_KEY,IssuerDetails.ISSUER_API_SECRET,requestParamsQR);

    }

    @Then("the error message telling that the partner ID is missing will appear")
    public void theErrorMessageTellingThatThePartnerIDIsMissingWillAppear() {
        QueryQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(QueryQRBuilder.getActualType(), "error","Test Case: queryDynamicQRWithMandatoryPartnerID");
        Assert.assertEquals(QueryQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: queryDynamicQRWithMandatoryPartnerID");
        Assert.assertEquals(QueryQRBuilder.getActualErrorDescription(),"partner_id"+" is mandatory","Test Case: queryDynamicQRWithMandatoryPartnerID");

    }

    @Given("the QR Payload is unknown")
    public void theQRPayloadIsUnknown() {
        QueryQRBuilder.initializeGenerateDynamicQR();
        partnerID = IssuerDetails.ISSUER_PARTNER_ID;
        responseQRData = "";
        createdTime= QueryQRBuilder.retrieveDatetime();
        //System.out.println(responseQRData);

        jsonObjValuesArr[0]= partnerID;
        jsonObjValuesArr[1]= responseQRData;
        jsonObjValuesArr[2]= createdTime;
        requestParamsQR = QueryQRBuilder.initializeQueryQRRequestBody(jsonObjValuesArr);
        System.out.println(requestParamsQR);
        QueryQRBuilder.callQueryDynamicQRAPI(IssuerDetails.ISSUER_API_KEY,IssuerDetails.ISSUER_API_SECRET,requestParamsQR);

    }

    @Then("the error message telling that the QR Payload is missing will appear")
    public void theErrorMessageTellingThatTheQRPayloadIsMissingWillAppear() {
        QueryQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(QueryQRBuilder.getActualType(), "error","Test Case: queryDynamicQRWithMandatoryQRPayload");
        Assert.assertEquals(QueryQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: queryDynamicQRWithMandatoryQRPayload");
        Assert.assertEquals(QueryQRBuilder.getActualErrorDescription(),"qr_payload"+" is mandatory","Test Case: queryDynamicQRWithMandatoryQRPayload");

    }

    @Given("the datetime is unknown")
    public void theDatetimeIsUnknown() {
        QueryQRBuilder.initializeGenerateDynamicQR();
        partnerID = IssuerDetails.ISSUER_PARTNER_ID;
        responseQRData = QueryQRBuilder.retrieveQRData();
        createdTime= "";
        //System.out.println(responseQRData);

        jsonObjValuesArr[0]= partnerID;
        jsonObjValuesArr[1]= responseQRData;
        jsonObjValuesArr[2]= createdTime;
        requestParamsQR = QueryQRBuilder.initializeQueryQRRequestBody(jsonObjValuesArr);
        System.out.println(requestParamsQR);
        QueryQRBuilder.callQueryDynamicQRAPI(IssuerDetails.ISSUER_API_KEY,IssuerDetails.ISSUER_API_SECRET,requestParamsQR);

    }

    @Then("the error message telling that the datetime is missing will appear")
    public void theErrorMessageTellingThatTheDatetimeIsMissingWillAppear() {
        QueryQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(QueryQRBuilder.getActualType(), "error","Test Case: queryDynamicQRWithMandatoryDateTime");
        Assert.assertEquals(QueryQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: queryDynamicQRWithMandatoryDateTime");
        Assert.assertEquals(QueryQRBuilder.getActualErrorDescription(),"datetime"+" is mandatory","Test Case: queryDynamicQRWithMandatoryDateTime");

    }
}
