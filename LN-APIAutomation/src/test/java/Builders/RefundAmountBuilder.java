package Builders;

import Utils.AcquirerDetails;
import Utils.IssuerDetails;
import Utils.ReusableStrings;
import Utils.TestConfig;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RefundAmountBuilder {
    public static JsonObject requestParamsQR = new JsonObject();
    public static JsonObject PPRequestParamsQR = new JsonObject();
    public static String jsonObjValuesArr[]=new String[11];
    public static String PPJsonObjValuesArr[]=new String[11];
    static String partnerID,payee,serviceCode,channel,merchantCurrencyCode,merchantAmount,orderID,customerCurrencyCode,customerAmount,channelInfo,transactionTime;
    static String actualType,actualErrorCode,actualErrorDescription;
    private static RequestSpecification requestSpec;
    private static Response responseRefundAmount;
    static String lqd_transaction_id,currencyCode,refundAmount;
    public static void initializeGenerateDynamicQR(){
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }
    public static JsonObject callPushPaymentRequestBody(){
        partnerID = IssuerDetails.ISSUER_PARTNER_ID;
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

        PPJsonObjValuesArr[0] = partnerID;
        PPJsonObjValuesArr[1] = transactionTime;
        PPJsonObjValuesArr[2] = payee;
        PPJsonObjValuesArr[3] = serviceCode;
        PPJsonObjValuesArr[4] = orderID;
        PPJsonObjValuesArr[5] = channel;
        PPJsonObjValuesArr[6] = channelInfo;
        PPJsonObjValuesArr[7] = customerCurrencyCode;
        PPJsonObjValuesArr[8] = customerAmount;
        PPJsonObjValuesArr[9] = merchantCurrencyCode;
        PPJsonObjValuesArr[10] = merchantAmount;
        PPRequestParamsQR = PushPaymentBuilder.initializePushPaymentRequestBody(PPJsonObjValuesArr);
        return  PPRequestParamsQR;

    }
    public  static void initializePushPayment(){
        partnerID = IssuerDetails.ISSUER_PARTNER_ID;
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

        PPJsonObjValuesArr[0] = partnerID;
        PPJsonObjValuesArr[1] = transactionTime;
        PPJsonObjValuesArr[2] = payee;
        PPJsonObjValuesArr[3] = serviceCode;
        PPJsonObjValuesArr[4] = orderID;
        PPJsonObjValuesArr[5] = channel;
        PPJsonObjValuesArr[6] = channelInfo;
        PPJsonObjValuesArr[7] = customerCurrencyCode;
        PPJsonObjValuesArr[8] = customerAmount;
        PPJsonObjValuesArr[9] = merchantCurrencyCode;
        PPJsonObjValuesArr[10] = merchantAmount;

        PPRequestParamsQR = PushPaymentBuilder.initializePushPaymentRequestBody(PPJsonObjValuesArr);
        PushPaymentBuilder.callPushPaymentAPI(IssuerDetails.ISSUER_API_KEY,IssuerDetails.ISSUER_API_SECRET,PPRequestParamsQR);

    }
    public static Response getRefundAmountResponse(){
        return responseRefundAmount;
    }
    public static  String getLiquidTransactionId() {
        lqd_transaction_id = PushPaymentBuilder.getResponsePushPayment().then().extract().path("lqd_transaction_id");
        return lqd_transaction_id;
    }
    public static  String getCurrencyCode() {
        currencyCode = PushPaymentBuilder.getResponsePushPayment().then().extract().path("merchant_currency_code");
        return currencyCode;
    }
    public static String getRefundAmount() {
        refundAmount = Float.toString(PushPaymentBuilder.getResponsePushPayment().then().extract().path("merchant_amount"));
        return refundAmount;
    }
    public static JsonObject initializeRefundRequestBody(String[] valuesArr)  {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("lqd_transaction_id",valuesArr[0]);
        jsonObj.addProperty("partner_id",valuesArr[1]);
        jsonObj.addProperty("currency_code",valuesArr[2]);
        jsonObj.addProperty("amount",valuesArr[3]);
        jsonObj.addProperty("reason",valuesArr[4]);
        return jsonObj;
    }
    public static void callRefundAmountAPI(String apiKey, String apiSecret,JsonObject requestBody) {

        requestSpec= TestConfig.createRequestSpecificationPOST(apiKey,apiSecret,requestBody.toString());

        responseRefundAmount = given().spec(requestSpec).body(requestBody).
                when().post(ReusableStrings.REFUND_AMOUNT);
        //System.out.println("Test here "+ responseGenerateDynamicQR);
        responseRefundAmount.getBody().prettyPrint();
    }

    public static void getErrorInformation() {
        actualType=responseRefundAmount.then().extract().path("type");
        actualErrorCode=responseRefundAmount.then().extract().path("error_code");
        actualErrorDescription=responseRefundAmount.then().extract().path("error_description");
    }
    public static String getActualType() {
        return actualType;
    }
    public static String getActualErrorCode() {
        return actualErrorCode;
    }
    public static String getActualErrorDescription() {
        return actualErrorDescription;
    }


}

