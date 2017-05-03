package com.epam.apartment.exception;

public class EmailExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a <code>EmailExistsException</code> with the specified detail
	 * message.
	 *
	 * @param message
	 *            the detail message.
	 */
	public EmailExistsException(String message) {
		super(message);
	}

	/**
	 * Constructs a <code>EmailExistsException</code> with the specified detail
	 * message and caught exception.
	 *
	 * @param message
	 *            the detail message.
	 * @param e
	 *            is thrown exception
	 */
	public EmailExistsException(String message, Exception e) {
		super(message, e);
	}

	/**
	 * Constructs a <code>EmailExistsException</code> with caught exception.
	 *
	 * @param e
	 *            is thrown exception
	 */
	public EmailExistsException(Exception e) {
		super(e);
	}

}