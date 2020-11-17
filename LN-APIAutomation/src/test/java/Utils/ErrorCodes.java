package Utils;

public interface ErrorCodes {
    String API_KEY_INVALID_ERROR_DESCRIPTION="A valid API key must be presented in the HTTP request header (Liquid-Api-Key)";
    String API_KEY_INVALID_ERROR_CODE="00400001";

    String REQUEST_HEADER_INVALID_SIGNATURE_DESCRIPTION="Invalid signature";
    String REQUEST_HEADER_NO_SIGNATURE_DESCRIPTION="No Signature header";
    String REQUEST_HEADER_INVALID_SIGNATURE_CODE="00400002";

    String MANDATORY_FIELD_MISSING_CODE="00400005";

    String INVALID_FIELD_CODE="00400006";

    String INVALID_CURRENCY_PAIR__DESCRIPTION="Invalid currency pair";
    String INVALID_CURRENCY_PAIR_CODE="00400015";

    String INVALID_ORDERID_CODE="00404001";

    String UNAUTHORIZED_ACCESS_CODE="00403004";
    String UNAUTHORIZED_ACCESS_DESCRIPTION="Unauthorized access";

    String FX_RATE_ISSUE_CODE="00402003";
    String FX_RATE_ISSUE_DESCRIPTION="There is an issue with FX rate";

    String INVALID_PAYEE_CODE="00400008";
    String INVALID_PAYEE_DESCRIPTION="The payee is invalid";

    String VOID_CURRENCY_MISMATCH_TXN_CURRENCY_CODE="20402003";
    String VOID_CURRENCY_MISMATCH_TXN_CURRENCY_DESCRIPTION="void currency must be the same as original transaction currency";

    String PUSH_PAYMENT_INVALID_STATE_CODE="10402002";
    String PUSH_PAYMENT_INVALID_STATE_DESCRIPTION="The order is not in a state that can be paid";

    String VOID_AMOUNT_MISMATCH_TXN_AMOUNT_CODE="20402004";
    String VOID_AMOUNT_MISMATCH_TXN_AMOUNT_DESCRIPTION="void amount must be the same as original transaction amount";

    String REFUND_PAYMENT_INVALID_STATE_CODE="20402002";
    String REFUND_PAYMENT_INVALID_STATE_DESCRIPTION="transaction cannot be refunded at this state";

    String REFUND_AMOUNT_GREATER_THAN_TXN_AMOUNT_CODE="20402006";
    String REFUND_AMOUNT_GREATER_THAN_TXN_AMOUNT_DESCRIPTION="Refund amount has exceeded the maximum amount refundable";

    String REFUND_AMOUNT_MISSING_STATE_CODE="00400005";
    String REFUND_AMOUNT_INVALIDTXN_STATE_CODE="20404001";

    String REFUND_CURRENCY_MISMATCH_TXN_CURRENCY_CODE="20402005";
    String REFUND_CURRENCY_MISMATCH_TXN_CURRENCY_DESCRIPTION="Refund currency must be the same as original transaction currency";

    String MERCHANT_AMOUNT_MISMATCH_TXN_AMOUNT_CODE = "00400013";
    String MERCHANT_AMOUNT_MISMATCH_TXN_AMOUNT_DESCRIPTION = "merchant_amount does not match the amount in the order";


}
