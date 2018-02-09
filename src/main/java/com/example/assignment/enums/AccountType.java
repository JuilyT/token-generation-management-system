package com.example.assignment.enums;

public enum AccountType {
	PREMIUM(1),
	REGULAR(0);
    private final int code;
    /**
     * @param code
     */
    private AccountType(final int code) {
        this.code = code;
    }
	public int getCode() {
		return code;
	}
}
