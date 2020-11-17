package Builders;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class RequestSignatureBuilder {
    public static String generateRequestSignature(String apiSecret,String json) throws Exception{
        int timeStamp =  (int) (System.currentTimeMillis() / 1000L);
        String requestBodyWihTimeStamp=getRawStringFromJson(json, timeStamp);
        //System.out.println(requestBodyWihTimeStamp);
        //step4
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256");
        //System.out.println(secret_key);
        sha256_HMAC.init(secret_key);

        String hash = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(requestBodyWihTimeStamp.getBytes()));
        //System.out.println(hash);
        String requestSignature="t="+timeStamp+","+hash;

        return requestSignature;
    }

    private static String getRawStringFromJson(String json, int timeStamp){
        String finalString;
        StringBuilder rawString = new StringBuilder();

        //Steps from 1.2 of LiquidNET Acquirer Specification
        //step 1
        finalString=json.replaceAll("\"", "").replaceAll("\\_", "").replaceAll(" ", "").replaceAll("-", "").replaceAll("\\+", "");
        System.out.println("step 1: " + finalString);
        //step 2
        finalString=finalString.toUpperCase();

        //System.out.println("step 2: "+ finalString);
        //step 3
        finalString=finalString+":"+timeStamp;
        //System.out.println("step 3: "+ finalString);
        return finalString;
    }
}
