package com.example.assignment.enums;

/**
 * Listing types of services bank is offering to customers
 * @author juilykumari
 *
 */
public enum ServiceType {
	ACCOUNT_CREATION("account_creation"),
	WITHDRAWL("withdrawl"),
	DEPOSIT("deposit");

    private final String text;

    /**
     * @param text
     */
    private ServiceType(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
