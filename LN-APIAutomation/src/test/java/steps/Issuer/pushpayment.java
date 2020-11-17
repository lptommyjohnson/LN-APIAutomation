package steps.Issuer;

import Builders.GenerateQRBuilder;
import Builders.PushPaymentBuilder;
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

public class pushpayment {
    private JsonObject requestParamsQR = new JsonObject();
    private String jsonObjValuesArr[]=new String[11];
    private String payee,serviceCode,channel,merchantCurrencyCode,merchantAmount,orderID,customerCurrencyCode,customerAmount,channelInfo,transactionTime;

    private JsonObject PPrequestParamsQR = new JsonObject();
    private JsonObject QQRrequestParamsQR = new JsonObject();
    private String PPjsonObjValuesArr[]=new String[11];
    private String QQRjsonObjValuesArr[]=new String[3];
    //private String payee,serviceCode,channel,merchantCurrencyCode,merchantAmount,orderID,customerCurrencyCode,channelInfo,transactionTime;
    private String CBpartnerID,responseQR, createdTime;
    private int CBcustomerAmount;

    @Given("The dynamic QR has been generated")
    public void theDynamicQRHasBeenGenerated() {
        PushPaymentBuilder.initializeGenerateDynamicQR();
    }
    @When("The partner id is {string}")
    public void thePartnerIdIs(String partnerID) {
        jsonObjValuesArr[0]= partnerID;
    }
    @When("The mandatory fields are filled in correctly")
    public void theMandatoryFieldsAreFilledInCorrectly() {
        transactionTime = PushPaymentBuilder.retrieveTransactionTime();
        payee = ReusableStrings.SG_MERCHANT_PAYEE;
        serviceCode = ReusableStrings.SG_MERCHANT_SERVICECODE;
        orderID = PushPaymentBuilder.retrieveOrderID();
        channel = ReusableStrings.CHANNEL;
        channelInfo = PushPaymentBuilder.retrieveChannelInfo();
        customerCurrencyCode = PushPaymentBuilder.retrieveCurrencyCode();
        customerAmount = PushPaymentBuilder.retrieveAmount();
        merchantCurrencyCode = ReusableStrings.SG_MERCHANT_CURRENCYCODE;
        merchantAmount = ReusableStrings.SG_MERCHANT_AMOUNT;

        jsonObjValuesArr[1] = transactionTime;
        jsonObjValuesArr[2] = payee;
        jsonObjValuesArr[3] = serviceCode;
        jsonObjValuesArr[4] = orderID;
        jsonObjValuesArr[5] = channel;
        jsonObjValuesArr[6] = channelInfo;
        jsonObjValuesArr[7] = customerCurrencyCode;
        jsonObjValuesArr[8] = customerAmount;
        jsonObjValuesArr[9] = merchantCurrencyCode;
        jsonObjValuesArr[10] = merchantAmount;

    }
    @When("The push payment is initiated")
    public void thePushPaymentIsInitiated() {
        requestParamsQR = PushPaymentBuilder.initializePushPaymentRequestBody(jsonObjValuesArr);
        PushPaymentBuilder.callPushPaymentAPI(IssuerDetails.ISSUER_API_KEY,IssuerDetails.ISSUER_API_SECRET,requestParamsQR);

    }
    @Then("Payment is successfully made")
    public void paymentIsSuccessfullyMade() {
        String response_code;
        response_code=PushPaymentBuilder.getResponsePushPayment().then().extract().path("response_code");
        Assert.assertEquals(response_code,"0");

    }
    @Then("An error message should be displayed to tell the invalid partnerID`")
    public void anErrorMessageShouldBeDisplayedToTellTheInvalidPartnerID() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidPartnerID");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: pushPaymentWithInvalidPartnerID");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"partner_id"+" is invalid","Test Case: pushPaymentWithInvalidPartnerID");

    }

    @When("The transactionDateTime is {string}")
    public void theTransactionDateTimeIs(String transactionTime) {
        jsonObjValuesArr[0] = IssuerDetails.ISSUER_PARTNER_ID;
        jsonObjValuesArr[1] = transactionTime;
    }

    @Then("An error message should be displayed to tell the invalid transactionDateTime")
    public void anErrorMessageShouldBeDisplayedToTellTheInvalidTransactionDateTime() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidTransactionDateTime");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: pushPaymentWithInvalidTransactionDateTime");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"transaction_datetime"+" is invalid","Test Case: pushPaymentWithInvalidTransactionDateTime");

    }

    @When("The payee is {string}")
    public void thePayeeIs(String payee) {
        jsonObjValuesArr[0] = IssuerDetails.ISSUER_PARTNER_ID;
        jsonObjValuesArr[2] = payee;

    }

    @Then("An error message should be displayed to tell the invalid payee")
    public void anErrorMessageShouldBeDisplayedToTellTheInvalidPayee() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidPayee");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_PAYEE_CODE,"Test Case: pushPaymentWithInvalidPayee");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"The payee"+" is invalid","Test Case: pushPaymentWithInvalidPayee");

    }

    @When("The service code is {string}")
    public void theServiceCodeIs(String serviceCode) {
        jsonObjValuesArr[0] = IssuerDetails.ISSUER_PARTNER_ID;
        jsonObjValuesArr[3] = serviceCode;

    }

    @Then("An error message should be displayed to tell the invalid service code")
    public void anErrorMessageShouldBeDisplayedToTellTheInvalidServiceCode() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidServiceCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: pushPaymentWithInvalidServiceCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"service_code"+" is invalid","Test Case: pushPaymentWithInvalidServiceCode");

    }

    @When("The order ID is {string}")
    public void theOrderIDIs(String orderID) {
        jsonObjValuesArr[0] = IssuerDetails.ISSUER_PARTNER_ID;
        jsonObjValuesArr[4] = orderID;

    }

    @Then("An error message should be displayed to tell the invalid order ID")
    public void anErrorMessageShouldBeDisplayedToTellTheInvalidOrderID() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidOrderID");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_ORDERID_CODE,"Test Case: pushPaymentWithInvalidOrderID");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"Order cannot be found","Test Case: pushPaymentWithInvalidOrderID");

    }

    @When("The channel is {string}")
    public void theChannelIs(String channel) {
        jsonObjValuesArr[0] = IssuerDetails.ISSUER_PARTNER_ID;
        jsonObjValuesArr[5] = channel;

    }

    @Then("An error message should be displayed to tell the invalid channel")
    public void anErrorMessageShouldBeDisplayedToTellTheInvalidChannel() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidChannel");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: pushPaymentWithInvalidChannel");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"channels"+" is invalid","Test Case: pushPaymentWithInvalidChannel");

    }

    @When("The customer currency code is {string}")
    public void theCurrencyCodeIs(String customerCurrencyCode) {
        jsonObjValuesArr[0] = IssuerDetails.ISSUER_PARTNER_ID;
        jsonObjValuesArr[7] = customerCurrencyCode;


    }

    @Then("An error message should be displayed to tell the invalid customer currency code")
    public void anErrorMessageShouldBeDisplayedToTellTheInvalidCurrencyCode() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidCustomerCurrencyCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: pushPaymentWithInvalidCustomerCurrencyCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"customer_currency_code"+" is invalid","Test Case: pushPaymentWithInvalidCustomerCurrencyCode");

    }

    @When("The customer amount is {string}")
    public void theAmountIs(String customerAmount) {
        jsonObjValuesArr[0] = IssuerDetails.ISSUER_PARTNER_ID;
        jsonObjValuesArr[8] = customerAmount;

    }

    @Then("An error message should be displayed to tell the invalid customer amount")
    public void anErrorMessageShouldBeDisplayedToTellTheInvalidCustomerAmount() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidCustomerAmount");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: pushPaymentWithInvalidCustomerAmount");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"customer_amount"+" is invalid","Test Case: pushPaymentWithInvalidCustomerAmount");

    }

    @When("The merchant currency code is {string}")
    public void theMerchantCurrencyCodeIs(String merchantCurrencyCode) {
        jsonObjValuesArr[0] = IssuerDetails.ISSUER_PARTNER_ID;
        jsonObjValuesArr[9] = merchantCurrencyCode;

    }

    @Then("An error message should be displayed to tell the invalid merchant currency code")
    public void anErrorMessageShouldBeDisplayedToTellTheInvalidMerchantCurrencyCode() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidMerchantCurrencyCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: pushPaymentWithInvalidMerchantCurrencyCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"merchant_currency_code"+" is invalid","Test Case: pushPaymentWithInvalidMerchantCurrencyCode");

    }

    @When("The merchant amount is {string}")
    public void theMerchantAmountIs(String merchantAmount) {
        jsonObjValuesArr[0] = IssuerDetails.ISSUER_PARTNER_ID;
        jsonObjValuesArr[10] = merchantAmount;

    }

    @Then("An error message should be displayed to tell the invalid merchant amount")
    public void anErrorMessageShouldBeDisplayedToTellTheInvalidMerchantAmount() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidMerchantAmount");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: pushPaymentWithInvalidMerchantAmount");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"merchant_amount"+" is invalid","Test Case: pushPaymentWithInvalidMerchantAmount");

    }

    @Then("An error message should be displayed to tell the missing partnerID`")
    public void anErrorMessageShouldBeDisplayedToTellTheMissingPartnerID() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithMandatoryPartnerID");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: pushPaymentWithMandatoryPartnerID");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"partner_id"+" is mandatory","Test Case: pushPaymentWithMandatoryPartnerID");

    }

    @Then("An error message should be displayed to tell the missing transactionDateTime")
    public void anErrorMessageShouldBeDisplayedToTellTheMissingTransactionDateTime() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithMandatoryTransactionDateTime");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: pushPaymentWithMandatoryTransactionDateTime");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"transaction_datetime"+" is mandatory","Test Case: pushPaymentWithMandatoryTransactionDateTime");

    }

    @Then("An error message should be displayed to tell the missing payee")
    public void anErrorMessageShouldBeDisplayedToTellTheMissingPayee() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithMandatoryPayee");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: pushPaymentWithMandatoryPayee");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"payee"+" is mandatory","Test Case: pushPaymentWithMandatoryPayee");

    }

    @Then("An error message should be displayed to tell the missing service code")
    public void anErrorMessageShouldBeDisplayedToTellTheMissingServiceCode() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithMandatoryServiceCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: pushPaymentWithMandatoryServiceCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"service_code"+" is mandatory","Test Case: pushPaymentWithMandatoryServiceCode");

    }

    @Then("An error message should be displayed to tell the missing order ID")
    public void anErrorMessageShouldBeDisplayedToTellTheMissingOrderID() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithMandatoryOrderID");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: pushPaymentWithMandatoryOrderID");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"order_id"+" is mandatory","Test Case: pushPaymentWithMandatoryOrderID");

    }

    @Then("An error message should be displayed to tell the missing channel")
    public void anErrorMessageShouldBeDisplayedToTellTheMissingChannel() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithMandatoryChannel");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: pushPaymentWithMandatoryChannel");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"channel"+" is mandatory","Test Case: pushPaymentWithMandatoryChannel");

    }

    @Then("An error message should be displayed to tell the missing customer currency code")
    public void anErrorMessageShouldBeDisplayedToTellTheMissingCustomerCurrencyCode() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidCustomerCurrencyCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: pushPaymentWithInvalidCustomerCurrencyCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"customer_currency_code"+" is invalid","Test Case: pushPaymentWithInvalidCustomerCurrencyCode");

    }

    @Then("An error message should be displayed to tell the missing customer amount")
    public void anErrorMessageShouldBeDisplayedToTellTheMissingCustomerAmount() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithMandatoryCustomerAmount");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: pushPaymentWithMandatoryCustomerAmount");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"customer_amount"+" is mandatory","Test Case: pushPaymentWithMandatoryCustomerAmount");

    }

    @Then("An error message should be displayed to tell the missing merchant currency code")
    public void anErrorMessageShouldBeDisplayedToTellTheMissingMerchantCurrencyCode() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithMandatoryMerchantCurrencyCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: pushPaymentWithMandatoryMerchantCurrencyCode");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"merchant_currency_code"+" is mandatory","Test Case: pushPaymentWithMandatoryMerchantCurrencyCode");

    }

    @Then("An error message should be displayed to tell the missing merchant amount")
    public void anErrorMessageShouldBeDisplayedToTellTheMissingMerchantAmount() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithMandatoryMerchantAmount");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.MANDATORY_FIELD_MISSING_CODE,"Test Case: pushPaymentWithMandatoryMerchantAmount");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),"merchant_amount"+" is mandatory","Test Case: pushPaymentWithMandatoryMerchantAmount");

    }

    @When("The merchant amount is $ {string} when the transaction amount is $ {string}")
    public void theMerchantAmountIs$WhenTheTransactionAmountIs$(String merchantAmount, String transactionAmount) {
        jsonObjValuesArr[0] = IssuerDetails.ISSUER_PARTNER_ID;
        jsonObjValuesArr[10] = merchantAmount;
        transactionAmount = GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("amount");
    }

    @Then("An error message should be displayed to tell the merchant amount does not match the amount in the order")
    public void anErrorMessageShouldBeDisplayedToTellTheMerchantAmountDoesNotMatchTheAmountInTheOrder() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithLargerMerchantAmount");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.MERCHANT_AMOUNT_MISMATCH_TXN_AMOUNT_CODE,"Test Case: pushPaymentWithLargerMerchantAmount");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),ErrorCodes.MERCHANT_AMOUNT_MISMATCH_TXN_AMOUNT_DESCRIPTION,"Test Case: pushPaymentWithLargerMerchantAmount");

    }
    @Given("The dynamic QR has been generated successfully")
    public void theDynamicQRHasBeenGeneratedSuccessfully() {
        PushPaymentBuilder.initializeGenerateDynamicQR();
    }

    @And("The Query has been retrieved")
    public void theQueryHasBeenRetrieved() {
        CBpartnerID = IssuerDetails.CBIISUER_PARTNER_ID;
        responseQR = QueryQRBuilder.retrieveQRData();
        createdTime= QueryQRBuilder.retrieveDatetime();

        QQRjsonObjValuesArr[0] = CBpartnerID;
        QQRjsonObjValuesArr[1]=responseQR;
        QQRjsonObjValuesArr[2]= createdTime;
        QQRrequestParamsQR = QueryQRBuilder.initializeQueryQRRequestBody(QQRjsonObjValuesArr);
        //System.out.println(requestParamsQR);
        QueryQRBuilder.callQueryDynamicQRAPI(IssuerDetails.CBISSUER_API_KEY,IssuerDetails.CBISSUER_API_SECRET,QQRrequestParamsQR);

    }

    @When("The cross border mandatory fields are filled in correctly")
    public void theCrossBorderMandatoryFieldsAreFilledInCorrectly() {
        CBpartnerID = IssuerDetails.CBIISUER_PARTNER_ID;
        transactionTime = PushPaymentBuilder.retrieveTransactionTime();
        payee = ReusableStrings.SG_MERCHANT_PAYEE;
        serviceCode = ReusableStrings.SG_MERCHANT_SERVICECODE;
        orderID = PushPaymentBuilder.retrieveOrderID();
        channel = ReusableStrings.CHANNEL;
        channelInfo = PushPaymentBuilder.retrieveChannelInfo();
        customerCurrencyCode = PushPaymentBuilder.retrieveCBCurrencyCode();
        CBcustomerAmount = PushPaymentBuilder.retrieveCBAmount();
        merchantCurrencyCode = ReusableStrings.SG_MERCHANT_CURRENCYCODE;
        merchantAmount = ReusableStrings.SG_MERCHANT_AMOUNT;

        PPjsonObjValuesArr[0] = CBpartnerID;
        PPjsonObjValuesArr[1] = transactionTime;
        PPjsonObjValuesArr[2] = payee;
        PPjsonObjValuesArr[3] = serviceCode;
        PPjsonObjValuesArr[4] = orderID;
        PPjsonObjValuesArr[5] = channel;
        PPjsonObjValuesArr[6] = channelInfo;
        PPjsonObjValuesArr[7] = customerCurrencyCode;
        PPjsonObjValuesArr[8] = Integer.toString(CBcustomerAmount);
        PPjsonObjValuesArr[9] = merchantCurrencyCode;
        PPjsonObjValuesArr[10] = merchantAmount;
    }

    @When("The cross border push payment is initiated")
    public void theCrossBorderPushPaymentIsInitiated() {
        PPrequestParamsQR = PushPaymentBuilder.initializePushPaymentRequestBody(PPjsonObjValuesArr);
        PushPaymentBuilder.callPushPaymentAPI(IssuerDetails.CBISSUER_API_KEY,IssuerDetails.CBISSUER_API_SECRET,PPrequestParamsQR);

    }

    @Then("Cross border Payment is successfully made")
    public void crossBorderPaymentIsSuccessfullyMade() {
        String response_code;
        response_code=PushPaymentBuilder.getResponsePushPayment().then().extract().path("response_code");
        Assert.assertEquals(response_code,"0");

    }

    @When("The payment is initiated again with the same QR")
    public void thePaymentIsInitiatedAgainWithTheSameQR() {
        PPrequestParamsQR = PushPaymentBuilder.initializePushPaymentRequestBody(PPjsonObjValuesArr);
        PushPaymentBuilder.callPushPaymentAPI(IssuerDetails.CBISSUER_API_KEY,IssuerDetails.CBISSUER_API_SECRET,PPrequestParamsQR);

    }

    @Then("An error message to display that the order is not in a state that can be paid will appear")
    public void anErrorMessageToDisplayThatTheOrderIsNotInAStateThatCanBePaidWillAppear() {
        PushPaymentBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(PushPaymentBuilder.getActualType(), "error","Test Case: pushPaymentWithInvalidOrderState");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorCode(), ErrorCodes.PUSH_PAYMENT_INVALID_STATE_CODE,"Test Case: pushPaymentWithInvalidOrderState");
        Assert.assertEquals(PushPaymentBuilder.getActualErrorDescription(),ErrorCodes.PUSH_PAYMENT_INVALID_STATE_DESCRIPTION,"Test Case: pushPaymentWithInvalidOrderState");

    }
}
