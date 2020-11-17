package Utils;
import Builders.RequestSignatureBuilder;
import Utils.ReusableStrings;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
public class TestConfig {
    private static RequestSpecification requestSpecGET,requestSpecPOSTAcquirer,requestSpecPOSTIssuer;

    //For GET requests. Request header dont require 'Content-Type'
    public static RequestSpecification createRequestSpecificationGET(String apiKey) {
        requestSpecGET = new RequestSpecBuilder().setBaseUri(AcquirerDetails.HOST_URL)
                .addHeader("Liquid-Api-Key", apiKey).build();

        return requestSpecGET;
    }

    //For POST requests (Acquirer)
    public static RequestSpecification createRequestSpecificationPOST(String apiKey,String apiSecret,String requestBody){

        //NOTE: API Key will not contain payee in future implementation!!

        String requestSignature;
        try {

            requestSignature = RequestSignatureBuilder.generateRequestSignature(apiSecret,requestBody);
            requestSpecPOSTAcquirer = new RequestSpecBuilder().setBaseUri(AcquirerDetails.HOST_URL)
                    .addHeader("Liquid-Api-Key", apiKey)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Signature",requestSignature).build();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return requestSpecPOSTAcquirer;
    }

    //For POST requests (Issuer)
    public static RequestSpecification createRequestSpecificationPOST_Issuer(String apiKey,String apiSecret,String requestBody){

        //NOTE: API Key will not contain payee in future implementation!!

        String requestSignature;
        try {
            //System.out.println(apiKey);
            //System.out.println(apiSecret);
            requestSignature = RequestSignatureBuilder.generateRequestSignature(apiSecret,requestBody);

            //System.out.println(requestSignature);
            requestSpecPOSTIssuer = new RequestSpecBuilder().setBaseUri(IssuerDetails.ISSUER_HOST_URL)
                    .addHeader("Liquid-Api-Key", apiKey)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Signature",requestSignature).build();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return requestSpecPOSTIssuer;
    }
    //For POST requests with missing header
    public static RequestSpecification createRequestSpecificationPOSTWithMissingHeader(String apiKey,String apiSecret,String reqestBody,String header){

        //NOTE: API Key will not contain payee in future implementation!!

        String requestSignature;
        try {
            requestSignature = RequestSignatureBuilder.generateRequestSignature(apiSecret,reqestBody);

            if(header.equals("Liquid-Api-Key")) {
                requestSpecPOSTAcquirer = new RequestSpecBuilder().setBaseUri(AcquirerDetails.HOST_URL)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Signature",requestSignature).build();
            }else if (header.equals("Signature")) {
                requestSpecPOSTAcquirer = new RequestSpecBuilder().setBaseUri(AcquirerDetails.HOST_URL)
                        .addHeader("Liquid-Api-Key", apiKey)
                        .addHeader("Content-Type", "application/json").build();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return requestSpecPOSTAcquirer;
    }
}
