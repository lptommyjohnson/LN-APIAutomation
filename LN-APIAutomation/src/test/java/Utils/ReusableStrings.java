package Utils;

import groovy.lang.GString;

public interface ReusableStrings {

    //Endpoints
    String RETRIEVE_QR_PAYLOAD_TYPES_ENDPOINT="v1/liquidnet/acquirer/qr/payloadtypes";
    String GENERATE_DYNAMIC_QR="v1/liquidnet/acquirer/qr";
    String QUERY_DYNAMIC_QR ="/v1/liquidnet/issuer/queryqr";
    String PUSH_PAYMENT ="/v1/liquidnet/issuer/payments";
    String REFUND_AMOUNT="/v1/liquidnet/acquirer/payments/refund";

    //Generate Dynamic QR: SG Merchant
    String SG_MERCHANT_PAYLOADCODE="LIQUID";
    String SG_MERCHANT_PARTNERID="500012";
    String SG_MERCHANT_ORDERREF="2019071018061601678097486";
    String SG_MERCHANT_PAYEE="400012100000111";
    String SG_MERCHANT_CURRENCYCODE="SGD";
    String SG_MERCHANT_AMOUNT="888.00";
    String SG_MERCHANT_SERVICECODE="13";
    String SG_MERCHANT_MERCHANTNAME="Singapore Merchant";
    String SG_MERCHANT_MERCHANTCITY="Singapore";
    String SG_MERCHANT_MERCHANTCOUNTRYCODE="SG";
    String SG_MERCHANT_MCC="5422";
    String SG_MERCHANT_POSTALCODE="123456";

    // Push payment: Necessary details
    String CHANNEL = "00";
}


