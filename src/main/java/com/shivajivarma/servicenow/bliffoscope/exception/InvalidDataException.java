package com.shivajivarma.servicenow.bliffoscope.exception;

public class InvalidDataException extends RuntimeException{
	private int code;
	public InvalidDataException(int code, String msg) {
		super(msg);
		this.code = code;
	}
}
