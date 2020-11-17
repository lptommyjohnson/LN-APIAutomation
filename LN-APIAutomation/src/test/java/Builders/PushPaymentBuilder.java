package Builders;

import Utils.AcquirerDetails;
import Utils.ReusableStrings;
import Utils.TestConfig;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class PushPaymentBuilder {
    static String jsonObjValuesArr[]=new String[11];
    private static RequestSpecification requestSpec;
    private static Response responsePushPayment;
    public static JsonObject requestParamsQR = new JsonObject();
    static String actualType,actualErrorCode,actualErrorDescription;
    static String orderID,customerCurrencyCode,customerAmount,channelInfo,transactionTime;
    static int CBcustomerAmount;
    SoftAssert softAssert = new SoftAssert();

    public static void initializeGenerateDynamicQR(){
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }
    public static String retrieveOrderID() {
        orderID=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("order_id");//not null or selected payload?
        return orderID;
    }
    public static String retrieveTransactionTime() {
        transactionTime=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("created_at");//not null or selected payload?
        return transactionTime;
    }
    public static String retrieveChannelInfo() {
        channelInfo=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("qr_data");//not null or selected payload?
        return channelInfo;
    }
    public static String retrieveCurrencyCode() {
        customerCurrencyCode=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("qr_currency_code");//not null or selected payload?
        return customerCurrencyCode;
    }
    public static String retrieveAmount() {
        customerAmount=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("qr_amount");//not null or selected payload?
        return customerAmount;
    }
    public static String retrieveCBCurrencyCode() {
        customerCurrencyCode=QueryQRBuilder.getResponseQueryDynamicQR().then().extract().path("customer_currency_code");//not null or selected payload?
        return customerCurrencyCode;
    }
    public static int retrieveCBAmount() {
        CBcustomerAmount=QueryQRBuilder.getResponseQueryDynamicQR().then().extract().path("customer_amount");;//not null or selected payload?
        return CBcustomerAmount;
    }
    public static JsonObject initializePushPaymentRequestBody(String[] valuesArr)  {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("partner_id",valuesArr[0]);
        jsonObj.addProperty("transaction_datetime",valuesArr[1]);
        jsonObj.addProperty("payee",valuesArr[2]);
        jsonObj.addProperty("service_code",valuesArr[3]);
        jsonObj.addProperty("order_id",valuesArr[4]);
        jsonObj.addProperty("channel",valuesArr[5]);
        jsonObj.addProperty("channel_info",valuesArr[6]);
        jsonObj.addProperty("customer_currency_code",valuesArr[7]);
        jsonObj.addProperty("customer_amount",valuesArr[8]);
        jsonObj.addProperty("merchant_currency_code",valuesArr[9]);
        jsonObj.addProperty("merchant_amount",valuesArr[10]);
        return jsonObj;
    }
    public static void callPushPaymentAPI(String apiKey, String apiSecret,JsonObject requestBody) {

        requestSpec= TestConfig.createRequestSpecificationPOST_Issuer(apiKey,apiSecret,requestBody.toString());

        responsePushPayment = given().spec(requestSpec).body(requestBody).
                when().post(ReusableStrings.PUSH_PAYMENT);
        //System.out.println("Test here "+ responseGenerateDynamicQR);
        responsePushPayment.getBody().prettyPrint();
    }
    public static Response getResponsePushPayment() {

        return responsePushPayment;
    }

    public static void getErrorInformation() {
        actualType=responsePushPayment.then().extract().path("type");
        actualErrorCode=responsePushPayment.then().extract().path("error_code");
        actualErrorDescription=responsePushPayment.then().extract().path("error_description");
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
