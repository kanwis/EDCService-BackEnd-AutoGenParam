package com.cs.BatchAutoGenerateParameter.constants;

import java.text.MessageFormat;

public enum ResponseCode {
	EXCEPTION("9999", "{0}"), SUCCESS("0001", "Success"), INVALID("0002", "{0} is invalid."),
	CONNECT("2000", "Unable to connect to the database."),
	UNAVAILABLE("2001", "The database service is currently unavailable."), IS_DUP("0005", "Duplicate MID or TID"),
	MERCHANT_NOTFOUND("0006", "Not found merchantId"), TERMINAL_NOTALLOW("0007", "Not allow first initial"),
	TERMINAL_NOTFOUND("0008", "Not found terminalId"),
	// terminalId
	VALIDATE_TERMINALID_REQUIRED("0013", "terminalId is required"),
	VALIDATE_TERMINALID_LENGTH("0014", "terminalId must be length equals 8"),
	VALIDATE_TERMINALID_NUMBER("0015", "terminalId must be a number"),

	// merchantId
	VALIDATE_MERCHANTID_REQUIRED("0016", "merchantId is required"),
	VALIDATE_MERCHANTID_LENGTH("0017", "merchantId must be length equals 15"),
	VALIDATE_MERCHANTID_NUMBER("0018", "merchantId must be a number"),

	// transactionId
	VALIDATE_TRANSACTION_ID_REQUIRED("0019", "transactionId is required"),
	VALIDATE_TRANSACTION_ID_LENGTH("0020", "transactionId maximum length is 26"),
	// terminalId
//		VALIDATE_TERMINALID_REQUIRED("0013", "terminalId is required"),
//		VALIDATE_TERMINALID_LENGTH("0014", "terminalId must be length equals 8"),
//		VALIDATE_TERMINALID_NUMBER("0015", "terminalId must be a number"),

	// terminalId
	VALIDATE_ENTERPRISEID_REQUIRED("0013", "enterpriseId is required"),
	VALIDATE_ENTERPRISEID_LENGTH("0014", "enterpriseId must be length equals 8"),
	VALIDATE_ENTERPRISEID_NUMBER("0015", "enterpriseId must be a number");

	private ResponseCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	private final String code;
	private final String message;

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String formatMessage(Object... args) {
		return MessageFormat.format(message, args);
	}
}
