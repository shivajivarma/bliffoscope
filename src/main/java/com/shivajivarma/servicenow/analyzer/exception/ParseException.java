package com.shivajivarma.servicenow.analyzer.exception;

public class ParseException extends RuntimeException{
	private int code;
	public ParseException(int code, String msg) {
		super(msg);
		this.code = code;
	}
}
