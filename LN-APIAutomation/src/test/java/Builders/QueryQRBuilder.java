package Builders;

import Utils.AcquirerDetails;
import Utils.ReusableStrings;
import Utils.TestConfig;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class QueryQRBuilder {
    static String jsonObjValuesArr[]=new String[12];
    static String jsonObjValuesArr1[]=new String[3];
    private static RequestSpecification requestSpec;
    private static Response responseQueryDynamicQR;
    public static JsonObject requestParamsQR = new JsonObject();
    static String actualType,actualErrorCode,actualErrorDescription;
    static String responseQRData,createdTime;
    SoftAssert softAssert = new SoftAssert();

    public static void initializeGenerateDynamicQR(){
        jsonObjValuesArr= GenerateQRBuilder.createSGMerchant();
        requestParamsQR=GenerateQRBuilder.initializeGenerateQRRequestBody(jsonObjValuesArr);
        GenerateQRBuilder.callGenerateDynamicQRAPI(AcquirerDetails.LQ_ACQ_DEV_APP_API_KEY,AcquirerDetails.LQ_ACQ_DEV_APP_API_SECRET,requestParamsQR);
    }

    public static String retrieveQRData() {
        responseQRData=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("qr_data");//not null or selected payload?
        return responseQRData;
    }
    public static String retrieveDatetime(){
        createdTime=GenerateQRBuilder.getResponseGenerateDynamicQR().then().extract().path("created_at");
        return createdTime;
    }
    public static JsonObject initializeQueryQRRequestBody(String[] valuesArr)  {
        JsonObject jsonObj = new JsonObject();

        jsonObj.addProperty("partner_id",valuesArr[0]);
        jsonObj.addProperty("qr_payload",valuesArr[1]);
        jsonObj.addProperty("datetime",valuesArr[2]);

        return jsonObj;
    }
    public static void callQueryDynamicQRAPI(String apiKey, String apiSecret,JsonObject requestBody) {

        requestSpec= TestConfig.createRequestSpecificationPOST_Issuer(apiKey,apiSecret,requestBody.toString());

        responseQueryDynamicQR = given().spec(requestSpec).body(requestBody).
                when().post(ReusableStrings.QUERY_DYNAMIC_QR);
        //System.out.println("Test here "+ responseGenerateDynamicQR);
        responseQueryDynamicQR.getBody().prettyPrint();
    }
    public static Response getResponseQueryDynamicQR() {

        return responseQueryDynamicQR;
    }

    public static void getErrorInformation() {
        actualType=responseQueryDynamicQR.then().extract().path("type");
        actualErrorCode=responseQueryDynamicQR.then().extract().path("error_code");
        actualErrorDescription=responseQueryDynamicQR.then().extract().path("error_description");
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
