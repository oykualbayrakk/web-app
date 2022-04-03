package com.company.web.app.ws.exceptions;

public class EmployeeServiceException extends RuntimeException{
	private static final long serialVersionUID = -4503746456016073130L;
	
	public EmployeeServiceException(String message)
	{
		super(message);
	}
}
