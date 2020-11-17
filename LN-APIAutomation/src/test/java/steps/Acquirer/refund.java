package steps.Acquirer;

import Builders.GenerateQRBuilder;
import Builders.PushPaymentBuilder;
import Builders.QueryQRBuilder;
import Builders.RefundAmountBuilder;
import Database.DatabaseHelper;
import Utils.AcquirerDetails;
import Utils.ErrorCodes;
import Utils.IssuerDetails;
import Utils.ReusableStrings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.DateUtil;
import org.testng.Assert;

import java.sql.Timestamp;
import java.util.Calendar;

public class refund {
    String transactionID, partnerID, currencyCode, reason,amount;
    private JsonObject requestParamsQR = new JsonObject();
    private String jsonObjValuesArr[]=new String[5];
    //private static Response responsePushPayment;

    //CB
    private JsonObject PPrequestParamsQR = new JsonObject();
    private JsonObject QQRrequestParamsQR = new JsonObject();
    private String PPjsonObjValuesArr[]=new String[11];
    private String QQRjsonObjValuesArr[]=new String[3];
    private String payee,serviceCode,channel,merchantCurrencyCode,merchantAmount,orderID,customerCurrencyCode,channelInfo,transactionTime;
    private String CBpartnerID,responseQR, createdTime;
    private int CBcustomerAmount;

    @Given("the QR has been generated")
    public void theQRHasBeenGenerated() {
        RefundAmountBuilder.initializeGenerateDynamicQR();
    }

    @And("the push payment has been made")
    public void thePushPaymentHasBeenMade() {
        RefundAmountBuilder.initializePushPayment();
    }

    @When("the refund details are filled in correctly")
    public void theRefundDetailsAreFilledInCorrectly() {
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode=RefundAmountBuilder.getCurrencyCode();
        amount = RefundAmountBuilder.getRefundAmount();
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @When("the refund amount is initiated")
    public void theRefundAmountIsInitiated() {
        requestParamsQR = RefundAmountBuilder.initializeRefundRequestBody(jsonObjValuesArr);
        RefundAmountBuilder.callRefundAmountAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    @Then("the full amount is refunded successfully")
    public void theFullAmountIsRefundedSuccessfully() {
        String response_code;
        response_code= RefundAmountBuilder.getRefundAmountResponse().then().extract().path("response_code");
        Assert.assertEquals(response_code,"0");

    }

    @When("the refund details are filled in correctly with partial amount {string}")
    public void theRefundDetailsAreFilledInCorrectlyWithPartialAmount(String amount) {
        // The transaction amount is $888.00
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode=RefundAmountBuilder.getCurrencyCode();
        amount = amount;
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("the partial amount is refunded successfully")
    public void thePartialAmountIsRefundedSuccessfully() {
        String response_code;
        response_code= RefundAmountBuilder.getRefundAmountResponse().then().extract().path("response_code");
        Assert.assertEquals(response_code,"0");

    }

    @And("the push payment has been made on the previous day")
    public void thePushPaymentHasBeenMadeOnThePreviousDay() throws Exception {
        RefundAmountBuilder.initializePushPayment();
        String transactionID = PushPaymentBuilder.getResponsePushPayment().then().extract().path("lqd_transaction_id");
        Timestamp txnTime = DatabaseHelper.getTxnTime(transactionID);
        //System.out.println("TestTime1: " + txnTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(txnTime);
        cal.add(Calendar.DAY_OF_WEEK,-1);
        txnTime.setTime(cal.getTime().getTime());
        txnTime = new Timestamp(cal.getTime().getTime());
        //System.out.println("TestTime2: "+ txnTime);
        DatabaseHelper.updateTxnTime(txnTime,transactionID);
    }

    @When("the refund details are filled in correctly with amount {string} larger than transaction amount")
    public void theRefundDetailsAreFilledInCorrectlyWithAmountLargerThanTransactionAmount(String amount) {
        // The transaction amount is $888.00
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode=RefundAmountBuilder.getCurrencyCode();
        amount = amount;
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("An error message should be displayed to tell that refund amount has exceeded the maximum amount refundable")
    public void anErrorMessageShouldBeDisplayedToTellThatRefundAmountHasExceededTheMaximumAmountRefundable() {
        RefundAmountBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(RefundAmountBuilder.getActualType(), "error","Test Case: refundAmountWithAmountExceedingTxnAmount");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorCode(), ErrorCodes.REFUND_AMOUNT_GREATER_THAN_TXN_AMOUNT_CODE,"Test Case: refundAmountWithAmountExceedingTxnAmount");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorDescription(),ErrorCodes.REFUND_AMOUNT_GREATER_THAN_TXN_AMOUNT_DESCRIPTION,"Test Case: refundAmountWithAmountExceedingTxnAmount");

    }

    @When("the refund amount is initiated again")
    public void theRefundAmountIsInitiatedAgain() {
        requestParamsQR = RefundAmountBuilder.initializeRefundRequestBody(jsonObjValuesArr);
        RefundAmountBuilder.callRefundAmountAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);

    }

    @When("the refund details are filled in correctly without partnerID")
    public void theRefundDetailsAreFilledInCorrectlyWithoutPartnerID() {
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = "";
        currencyCode=RefundAmountBuilder.getCurrencyCode();
        amount = RefundAmountBuilder.getRefundAmount();
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("An error message should be displayed to tell that partner ID is mandatory")
    public void anErrorMessageShouldBeDisplayedToTellThatPartnerIDIsMandatory() {
        RefundAmountBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(RefundAmountBuilder.getActualType(), "error","Test Case: refundAmountWithoutPartnerID");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorCode(), ErrorCodes.REFUND_AMOUNT_MISSING_STATE_CODE,"Test Case: refundAmountWithoutPartnerID");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorDescription(),"partner_id is mandatory","Test Case: refundAmountWithoutPartnerID");

    }

    @When("the refund details are filled in correctly without transaction ID")
    public void theRefundDetailsAreFilledInCorrectlyWithoutTransactionID() {
        transactionID = "";
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode=RefundAmountBuilder.getCurrencyCode();
        amount = RefundAmountBuilder.getRefundAmount();
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("An error message should be displayed to tell that transaction ID is mandatory")
    public void anErrorMessageShouldBeDisplayedToTellThatTransactionIDIsMandatory() {
        RefundAmountBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(RefundAmountBuilder.getActualType(), "error","Test Case: refundAmountWithoutTxnID");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorCode(), ErrorCodes.REFUND_AMOUNT_MISSING_STATE_CODE,"Test Case: refundAmountWithoutTxnID");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorDescription(),"lqd_transaction_id is mandatory","Test Case: refundAmountWithoutTxnID");

    }

    @When("the refund details are filled in correctly without currency code")
    public void theRefundDetailsAreFilledInCorrectlyWithoutCurrencyCode() {
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode="";
        amount = RefundAmountBuilder.getRefundAmount();
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("An error message should be displayed to tell that currency code is mandatory")
    public void anErrorMessageShouldBeDisplayedToTellThatCurrencyCodeIsMandatory() {
        RefundAmountBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(RefundAmountBuilder.getActualType(), "error","Test Case: refundAmountWithoutCurrencyCode");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorCode(), ErrorCodes.REFUND_AMOUNT_MISSING_STATE_CODE,"Test Case: refundAmountWithoutCurrencyCode");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorDescription(),"currency_code is mandatory","Test Case: refundAmountWithoutCurrencyCode");

    }

    @When("the refund details are filled in correctly without amount")
    public void theRefundDetailsAreFilledInCorrectlyWithoutAmount() {
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode=RefundAmountBuilder.getCurrencyCode();
        amount = "";
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("An error message should be displayed to tell that amount is mandatory")
    public void anErrorMessageShouldBeDisplayedToTellThatAmountIsMandatory() {
        RefundAmountBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(RefundAmountBuilder.getActualType(), "error","Test Case: refundAmountWithoutAmount");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorCode(), ErrorCodes.REFUND_AMOUNT_MISSING_STATE_CODE,"Test Case: refundAmountWithoutAmount");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorDescription(),"amount is mandatory","Test Case: refundAmountWithoutAmount");

    }

    @When("the refund details are filled in correctly with invalid transaction ID")
    public void theRefundDetailsAreFilledInCorrectlyWithInvalidTransactionID() {
        transactionID = "ABC";
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode=RefundAmountBuilder.getCurrencyCode();
        amount = RefundAmountBuilder.getRefundAmount();
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("An error message should be displayed to tell that invalid transaction ID")
    public void anErrorMessageShouldBeDisplayedToTellThatInvalidTransactionID() {
        RefundAmountBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(RefundAmountBuilder.getActualType(), "error","Test Case: refundAmountWithoutTxnID");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorCode(), ErrorCodes.REFUND_AMOUNT_INVALIDTXN_STATE_CODE,"Test Case: refundAmountWithoutTxnID");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorDescription(),"Transaction cannot be found","Test Case: refundAmountWithoutTxnID");

    }

    @When("the refund details are filled in correctly with invalid partner ID")
    public void theRefundDetailsAreFilledInCorrectlyWithInvalidPartnerID() {
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = "abc";
        currencyCode=RefundAmountBuilder.getCurrencyCode();
        amount = RefundAmountBuilder.getRefundAmount();
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("An error message should be displayed to tell that invalid partner ID")
    public void anErrorMessageShouldBeDisplayedToTellThatInvalidPartnerID() {
        RefundAmountBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(RefundAmountBuilder.getActualType(), "error","Test Case: refundAmountWithoutPartnerID");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: refundAmountWithoutPartnerID");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorDescription(),"partner_id is invalid","Test Case: refundAmountWithoutPartnerID");

    }

    @When("the refund details are filled in correctly with invalid currency code")
    public void theRefundDetailsAreFilledInCorrectlyWithInvalidCurrencyCode() {
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode="abc";
        amount = RefundAmountBuilder.getRefundAmount();
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("An error message should be displayed to tell that invalid currency code")
    public void anErrorMessageShouldBeDisplayedToTellThatInvalidCurrencyCode() {
        RefundAmountBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(RefundAmountBuilder.getActualType(), "error","Test Case: refundAmountWithoutCurrencyCode");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: refundAmountWithoutCurrencyCode");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorDescription(),"currency_code is invalid","Test Case: refundAmountWithoutCurrencyCode");

    }

    @When("the refund details are filled in correctly with invalid amount")
    public void theRefundDetailsAreFilledInCorrectlyWithInvalidAmount() {
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode=RefundAmountBuilder.getCurrencyCode();
        amount = "abc";
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("An error message should be displayed to tell that invalid amount")
    public void anErrorMessageShouldBeDisplayedToTellThatInvalidAmount() {
        RefundAmountBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(RefundAmountBuilder.getActualType(), "error","Test Case: refundAmountWithoutAmount");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorCode(), ErrorCodes.INVALID_FIELD_CODE,"Test Case: refundAmountWithoutAmount");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorDescription(),"amount is invalid","Test Case: refundAmountWithoutAmount");

    }

    @When("the refund details are filled in correctly with a different transaction currency code")
    public void theRefundDetailsAreFilledInCorrectlyWithADifferentTransactionCurrencyCode() {
        // TRANSACTION CURRENCY IS IN SGD
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode="HKD";
        amount = RefundAmountBuilder.getRefundAmount();
        reason = "";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @Then("An error message should be displayed to tell that refund currency must be the same as transaction currency")
    public void anErrorMessageShouldBeDisplayedToTellThatRefundCurrencyMustBeTheSameAsTransactionCurrency() {
        RefundAmountBuilder.getErrorInformation();
        //Assert
        Assert.assertEquals(RefundAmountBuilder.getActualType(), "error","Test Case: refundAmountWithDifferentCurrencyCode");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorCode(), ErrorCodes.REFUND_CURRENCY_MISMATCH_TXN_CURRENCY_CODE,"Test Case: refundAmountWithDifferentCurrencyCode");
        Assert.assertEquals(RefundAmountBuilder.getActualErrorDescription(),ErrorCodes.REFUND_CURRENCY_MISMATCH_TXN_CURRENCY_DESCRIPTION,"Test Case: refundAmountWithDifferentCurrencyCode");

    }

    @When("the refund details are filled in correctly with a reason")
    public void theRefundDetailsAreFilledInCorrectlyWithAReason() {
        transactionID = RefundAmountBuilder.getLiquidTransactionId();
        partnerID = AcquirerDetails.ACQUIRER_PARTNER_ID;
        currencyCode=RefundAmountBuilder.getCurrencyCode();
        amount = RefundAmountBuilder.getRefundAmount();
        reason = "Refund an amount due to wrong items";
        jsonObjValuesArr[0] = transactionID;
        jsonObjValuesArr[1] = partnerID;
        jsonObjValuesArr[2] = currencyCode;
        jsonObjValuesArr[3] = amount;
        jsonObjValuesArr[4] = reason;

    }

    @And("the QR has been queried")
    public void theQRHasBeenQueried() {
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

    @And("the cross border push payment has been made")
    public void theCrossBorderPushPaymentHasBeenMade() {
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
        PPrequestParamsQR = PushPaymentBuilder.initializePushPaymentRequestBody(PPjsonObjValuesArr);
        PushPaymentBuilder.callPushPaymentAPI(IssuerDetails.CBISSUER_API_KEY,IssuerDetails.CBISSUER_API_SECRET,PPrequestParamsQR);

    }
}

 