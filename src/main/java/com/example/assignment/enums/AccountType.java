package com.example.assignment.enums;

/**
 * Listing type of account of customers in the bank branch
 * @author juilykumari
 *
 */
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
