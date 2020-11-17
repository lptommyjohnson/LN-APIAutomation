package Builders;

import Utils.ReusableStrings;
import Utils.TestConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RetrievePayloadsBuilder {
    private static RequestSpecification requestSpec;
    private static Response responseGetQRList;
    String actualType,actualErrorCode,actualErrorDescription;

    public static void RetrieveQRPayloadTypesAPI(String APIkey) {

        requestSpec= TestConfig.createRequestSpecificationGET(APIkey);
        responseGetQRList=given().spec(requestSpec).when().get(ReusableStrings.RETRIEVE_QR_PAYLOAD_TYPES_ENDPOINT);
        responseGetQRList.getBody().prettyPrint();

    }
    public static Response getResponseRetrievePayloadTypes() {
        return responseGetQRList;
    }

}
