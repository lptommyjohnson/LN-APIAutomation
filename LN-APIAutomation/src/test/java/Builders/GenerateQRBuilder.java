package Builders;

import Utils.ReusableStrings;
import Utils.TestConfig;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class GenerateQRBuilder {
    static String jsonObjValuesArr[]=new String[12];
    private static RequestSpecification requestSpec;
    private static Response responseGenerateDynamicQR;
    private JsonObject requestParamsQR = new JsonObject();

    static String actualType,actualErrorCode,actualErrorDescription;
    SoftAssert softAssert = new SoftAssert();

    public static String[] createSGMerchant() {
        //used in Test: validatePaymentNotificationResponse in PaymentNotification
        jsonObjValuesArr[0]= ReusableStrings.SG_MERCHANT_PAYLOADCODE;
        jsonObjValuesArr[1]=ReusableStrings.SG_MERCHANT_PARTNERID;
        jsonObjValuesArr[2]=ReusableStrings.SG_MERCHANT_ORDERREF;
        jsonObjValuesArr[3]=ReusableStrings.SG_MERCHANT_PAYEE;
        jsonObjValuesArr[4]=ReusableStrings.SG_MERCHANT_CURRENCYCODE;
        jsonObjValuesArr[5]=ReusableStrings.SG_MERCHANT_AMOUNT;
        jsonObjValuesArr[6]=ReusableStrings.SG_MERCHANT_SERVICECODE;
        jsonObjValuesArr[7]=ReusableStrings.SG_MERCHANT_MERCHANTNAME;
        jsonObjValuesArr[8]=ReusableStrings.SG_MERCHANT_MERCHANTCITY;
        jsonObjValuesArr[9]=ReusableStrings.SG_MERCHANT_MERCHANTCOUNTRYCODE;
        jsonObjValuesArr[10]=ReusableStrings.SG_MERCHANT_MCC;
        jsonObjValuesArr[11]=ReusableStrings.SG_MERCHANT_POSTALCODE;

        return jsonObjValuesArr;
    }
    public static JsonObject initializeGenerateQRRequestBody(String[] valuesArr) {
        JsonObject jsonObj = new JsonObject();

        //request body
        jsonObj.addProperty("payload_code",valuesArr[0]);
        jsonObj.addProperty("partner_id",valuesArr[1]);
        jsonObj.addProperty("order_ref", valuesArr[2]);
        jsonObj.addProperty("payee", valuesArr[3]);
        jsonObj.addProperty("currency_code", valuesArr[4]);
        jsonObj.addProperty("amount", valuesArr[5]);
        jsonObj.addProperty("service_code", valuesArr[6]);
        jsonObj.addProperty("merchant_name", valuesArr[7]);
        jsonObj.addProperty("merchant_city", valuesArr[8]);
        jsonObj.addProperty("merchant_country_code", valuesArr[9]);
        jsonObj.addProperty("mcc", valuesArr[10]);
        jsonObj.addProperty("postal_code", valuesArr[11]);

        return jsonObj;
    }

    public static void callGenerateDynamicQRAPI(String apiKey, String apiSecret,JsonObject requestBody) {

        requestSpec= TestConfig.createRequestSpecificationPOST(apiKey,apiSecret,requestBody.toString());
        responseGenerateDynamicQR = given().spec(requestSpec).body(requestBody).
                when().post(ReusableStrings.GENERATE_DYNAMIC_QR);
        //System.out.println("Test here "+ responseGenerateDynamicQR);
        responseGenerateDynamicQR.getBody().prettyPrint();
    }
    public static Response getResponseGenerateDynamicQR() {
        return responseGenerateDynamicQR;
    }
    public static String decodeQRData(String qrData, String tag, String length) {
        String value="";
        String tagLength=tag+length;

        if(qrData.contains(tagLength)) {
            int startIndex=qrData.indexOf(tagLength)+3+1;//3 = remaining chars of tagLength, 1 = go next char

            int endIndex=startIndex+Integer.parseInt(length);
            value=qrData.substring(startIndex, endIndex);
        }
        else
            value="TagLength of "+tagLength+" not found in QR data";

        return value;
    }
    public static void getErrorInformation() {
        actualType=responseGenerateDynamicQR.then().extract().path("type");
        actualErrorCode=responseGenerateDynamicQR.then().extract().path("error_code");
        actualErrorDescription=responseGenerateDynamicQR.then().extract().path("error_description");
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
