package steps.Acquirer;

import Builders.GenerateQRBuilder;
import Utils.AcquirerDetails;
import Utils.ErrorCodes;
import Utils.ReusableStrings;
import Utils.TestConfig;
import com.google.gson.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class generateDynamicQR {
    private static RequestSpecification requestSpec;
    private JsonObject requestParamsQR = new JsonObject();
    private String jsonObjValuesArr[]=new String[12];


    // Acquirer is able to generate the dynamic QR
    @Given("I provide the correct test data to the endpoint")
    public void iProvideTheCorrectTestDataToTheEndpoint() {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);

    }
    @Then("Dynamic QR is generated successfully")
    public void dynamicQRIsGeneratedSuccessfully() {
        String responseOrderID,responseOrderRef,responseCurrencyCode,responseAmount,responseQRData,responseQRCurrencyCode,responseQRAmount,responseQRMerchantCurrencyCode,responseQRMerchantAmount,responseUpdated,responseCreated;

        responseOrderID=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("order_id");

        responseOrderRef=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("order_ref");//request body
        responseCurrencyCode=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("currency_code");//request body
        responseAmount=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("amount");//request body
        responseQRData=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("qr_data");//not null or selected payload?
        responseQRCurrencyCode=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("qr_currency_code");//cc in QR
        responseQRAmount=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("qr_amount");//amt in qr
        responseUpdated=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("updated_at");
        responseCreated=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("created_at");

        Assert.assertNotNull(responseOrderID,"Test Case: generateDynamicQRWithValidAPIKey order_id");
        Assert.assertNotEquals(responseOrderID, "", "Test Case: generateDynamicQRWithValidAPIKey order_id");

        Assert.assertEquals(responseOrderRef,ReusableStrings.SG_MERCHANT_ORDERREF,"Test Case: generateDynamicQRWithValidAPIKey  order_ref");

        Assert.assertEquals(responseCurrencyCode,ReusableStrings.SG_MERCHANT_CURRENCYCODE,"Test Case: generateDynamicQRWithValidAPIKey currency_code");

        Assert.assertEquals(responseAmount,ReusableStrings.SG_MERCHANT_AMOUNT,"Test Case: generateDynamicQRWithValidAPIKey amount");

        //qr cc
        String qrDataCurrencyNumber=GenerateQRBuilder.decodeQRData(responseQRData,"53","03");
        String qrDataCurrencyCode="";

        if(qrDataCurrencyNumber.equals("702"))
            qrDataCurrencyCode="SGD";

        Assert.assertEquals(qrDataCurrencyCode, responseQRCurrencyCode,"Test Case: generateDynamicQRWithValidAPIKeyqr_currency_code");

        //qr amt
        String currencyLength;

        if(ReusableStrings.SG_MERCHANT_AMOUNT.length() <= 9)
            currencyLength="0"+Integer.toString(ReusableStrings.SG_MERCHANT_AMOUNT.length());
        else
            currencyLength=Integer.toString(ReusableStrings.SG_MERCHANT_AMOUNT.length());

        String qrDataAmount=GenerateQRBuilder.decodeQRData(responseQRData,"54",currencyLength);
        Assert.assertEquals(qrDataAmount, responseAmount,"For "+ReusableStrings.GENERATE_DYNAMIC_QR+" qr_amount");

        Assert.assertNotNull(responseUpdated);
        Assert.assertNotEquals(responseUpdated, "", "Test Case: generateDynamicQRWithValidAPIKey,"+ReusableStrings.GENERATE_DYNAMIC_QR+" updated_at not expected to be empty");

        Assert.assertNotNull(responseCreated);
        Assert.assertNotEquals(responseCreated, "", "For "+ReusableStrings.GENERATE_DYNAMIC_QR+" created_at not expected to be empty");
    }
    String responseOrderID, responseQRData;
    @Given("I provide {string} as the merchant name for the first time")
    public void iProvideAsTheMerchantNameForTheFirstTime(String merchantName) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("merchant_name", merchantName);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
        responseOrderID=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("order_id");
        responseQRData=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("qr_data");//not null or selected payload?

    }
    String responseOrderID1, responseQRData1;
    @And("I provide {string} as the merchant name for the second time")
    public void iProvideAsTheMerchantNameForTheSecondTime(String merchantName) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("merchant_name", merchantName);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
        responseOrderID1=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("order_id");
        responseQRData1=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("qr_data");//not null or selected payload?

    }

    @Then("the QR details will be updated")
    public void theQRDetailsWillBeUpdated() {
        Assert.assertNotEquals(responseOrderID,responseOrderID1);
        Assert.assertNotEquals(responseQRData, responseQRData1);
    }


    // Generate a dynamic QR with invalid payload code will trigger an error message

    @Given("Given that the acquirer do not accept {string} as payload code")
    public void givenThatTheAcquirerDoNotAcceptAsPayloadCode(String payloadCode) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("payload_code", payloadCode);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);

    }
    @Then("An error message stating that payload code is invalid will appear")
    public void anErrorMessageStatingThatPayloadCodeIsInvalidWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithInvalidPayloadCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: generateDynamicQRWithInvalidPayloadCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"payload_code"+" is invalid","Test Case: generateDynamicQRWithInvalidPayloadCode");
    }
    // Generate a dynamic QR with invalid partner id will trigger an error message

    @Given("The partner id {string} does not exists")
    public void thePartnerIdDoesNotExists(String partner_id) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("partner_id", partner_id);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }
    @Then("An error message stating that partner ID  is invalid will appear")
    public void anErrorMessageStatingThatPartnerIDIsInvalidWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithInvalidPartnerID");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: generateDynamicQRWithInvalidPartnerID");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"partner_id"+" is invalid","Test Case: generateDynamicQRWithInvalidPartnerID");
    }

    // Generate a dynamic QR with invalid payee will trigger an error message
    @Given("The payee {string} does not exists")
    public void thePayeeDoesNotExists(String payee) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("payee", payee);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("An error message stating that payee is invalid will appear")
    public void anErrorMessageStatingThatPayeeIsInvalidWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithInvalidPayee");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.INVALID_PAYEE_CODE,"Test Case: generateDynamicQRWithInvalidPayee");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"The payee"+" is invalid","Test Case: generateDynamicQRWithInvalidPayee");
    }
    // Generate a dynamic QR with invalid currency code will trigger an error message
    @Given("The currency code {string} does not exists")
    public void theCurrencyCodeDoesNotExists(String currencyCode) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("currency_code", currencyCode);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);    }

    @Then("An error message stating that currency code is invalid will appear")
    public void anErrorMessageStatingThatCurrencyCodeIsInvalidWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithInvalidCurrencyCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: generateDynamicQRWithInvalidCurrencyCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"currency_code"+" is invalid","Test Case: generateDynamicQRWithInvalidCurrencyCode");

    }

    @Given("The amount {string} is not in the right format")
    public void theAmountIsNotInTheRightFormat(String amount) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("amount", amount);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("An error message stating that amount is invalid will appear")
    public void anErrorMessageStatingThatAmountIsInvalidWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithInvalidAmount");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: generateDynamicQRWithInvalidAmount");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"amount"+" is invalid","Test Case: generateDynamicQRWithInvalidAmount");
    }

    @Given("The service code {string} is not in the right format")
    public void theServiceCodeIsNotInTheRightFormat(String serviceCode) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("service_code", serviceCode);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("An error message stating that service code is invalid will appear")
    public void anErrorMessageStatingThatServiceCodeIsInvalidWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithInvalidServiceCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: generateDynamicQRWithInvalidServiceCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"service_code"+" is invalid","Test Case: generateDynamicQRWithInvalidServiceCode");

    }

    @Given("The merchant country code {string} is not in the right format")
    public void theMerchantCountryCodeIsNotInTheRightFormat(String merchantCountryCode) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("merchant_country_code", merchantCountryCode);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }
    @Then("An error message stating that merchant country code is invalid will appear")
    public void anErrorMessageStatingThatMerchantCountryCodeIsInvalidWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithInvalidMerchantCountryCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: generateDynamicQRWithInvalidMerchantCountryCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"merchant_country_code"+" is invalid","Test Case: generateDynamicQRWithInvalidMerchantCountryCode");

    }

    @Given("The mcc {string} does not exists")
    public void theMccDoesNotExists(String mcc) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("mcc", mcc);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }
    @Then("An error message stating that mcc is invalid will appear")
    public void anErrorMessageStatingThatMccIsInvalidWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithInvalidMerchantCountryMcc");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: generateDynamicQRWithInvalidMcc");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"mcc"+" is invalid","Test Case: generateDynamicQRWithInvalidMcc");

    }

    @Given("Given that the payload code is empty")
    public void givenThatThePayloadCodeIsEmpty() {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("payload_code", "");
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("An error message stating that payload code is mandatory will appear")
    public void anErrorMessageStatingThatPayloadCodeIsMandatoryWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithMandatoryPayloadCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: generateDynamicQRWithMandatoryPayloadCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"payload_code"+" is mandatory","Test Case: generateDynamicQRWithMandatoryPayloadCode");

    }

    @Given("Given that the partner ID is empty")
    public void givenThatThePartnerIDIsEmpty() {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("partner_id", "");
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("An error message stating that partner ID is mandatory will appear")
    public void anErrorMessageStatingThatPartnerIDIsMandatoryWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithMandatoryPartnerID");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: generateDynamicQRWithMandatoryPartnerID");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"partner_id"+" is mandatory","Test Case: generateDynamicQRWithMandatoryPartnerID");

    }

    @Given("Given that the payee is empty")
    public void givenThatThePayeeIsEmpty() {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("payee", "");
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }
    @Then("An error message stating that payee is mandatory will appear")
    public void anErrorMessageStatingThatPayeeIsMandatoryWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithMandatoryPayee");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: generateDynamicQRWithMandatoryPayee");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"payee"+" is mandatory","Test Case: generateDynamicQRWithMandatoryPayee");

    }
    @Given("Given that the currency code is empty")
    public void givenThatTheCurrencyCodeIsEmpty() {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("currency_code", "");
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("An error message stating that currency code is mandatory will appear")
    public void anErrorMessageStatingThatCurrencyCodeIsMandatoryWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithMandatoryCurrencyCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: generateDynamicQRWithMandatoryCurrencyCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"currency_code"+" is mandatory","Test Case: generateDynamicQRWithMandatoryCurrencyCode");

    }

    @Given("Given that the amount is empty")
    public void givenThatTheAmountIsEmpty() {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("amount", "");
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("An error message stating that amount is mandatory will appear")
    public void anErrorMessageStatingThatAmountIsMandatoryWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithMandatoryAmount");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: generateDynamicQRWithMandatoryAmount");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"amount"+" is mandatory","Test Case: generateDynamicQRWithMandatoryAmount");

    }

    @Given("Given that the service code is empty")
    public void givenThatTheServiceCodeIsEmpty() {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("service_code", "");
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("An error message stating that service code is mandatory will appear")
    public void anErrorMessageStatingThatServiceCodeIsMandatoryWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithMandatoryServiceCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: generateDynamicQRWithMandatoryServiceCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"service_code"+" is mandatory","Test Case: generateDynamicQRWithMandatoryServiceCode");

    }

    @Given("Given that the merchant country code is empty")
    public void givenThatTheMerchantCountryCodeIsEmpty() {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("merchant_country_code", "");
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("An error message stating that merchant country code is mandatory will appear")
    public void anErrorMessageStatingThatMerchantCountryCodeIsMandatoryWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithMandatoryMerchantCountryCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: generateDynamicQRWithMandatoryMerchantCountryCode");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"merchant_country_code"+" is mandatory","Test Case: generateDynamicQRWithMandatoryMerchantCountryCode");

    }

    @Given("Given that the mcc is empty")
    public void givenThatTheMccIsEmpty() {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("mcc", "");
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("An error message stating that mcc is mandatory will appear")
    public void anErrorMessageStatingThatMccIsMandatoryWillAppear() {
        GenerateQRBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(GenerateQRBuilder.getActualType(), "error","Test Case: generateDynamicQRWithMandatoryMcc");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: generateDynamicQRWithMandatoryMcc");
        Assert.assertEquals(GenerateQRBuilder.getActualErrorDescription(),"mcc"+" is mandatory","Test Case: generateDynamicQRWithMandatoryMcc");
    }

    @Given("I provide {string} as the merchant country code when it is located in SG")
    public void iProvideAsTheMerchantCountryCodeWhenItIsLocatedInSG(String MerchantCountryCode) {
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        requestParamsQR.addProperty("merchant_country_code", MerchantCountryCode);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }
}
