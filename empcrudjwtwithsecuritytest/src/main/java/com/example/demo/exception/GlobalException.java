package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.dto.ResponseMessage;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(EmployeeIdNotFoundException.class)
	public ResponseEntity<ResponseMessage>  empNotAvailable(EmployeeIdNotFoundException e)
	{
		 return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
	}

	
	@ExceptionHandler(InvalidUserOrPasswordExcpetion.class)
	public ResponseEntity<ResponseMessage> userNotAvailable(
	        InvalidUserOrPasswordExcpetion e) {

	    return ResponseEntity.status(401)
	            .body(new ResponseMessage(e.getMessage()));
	}
	
	@ExceptionHandler(IllegalAccessException.class)
	public ResponseEntity<ResponseMessage>  illegalAcc(IllegalAccessException e)
	{
		 return ResponseEntity.status(500).body(new ResponseMessage("Access Err"+ e.getMessage()));
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseMessage>  illegalErr(IllegalArgumentException e)
	{
		 return ResponseEntity.status(500).body(new ResponseMessage("IllegalArgument :"+ e.getMessage()));
	}
	
	
	@ExceptionHandler(NoSuchFieldException.class)
	public ResponseEntity<ResponseMessage>  illegalErr(NoSuchFieldException e)
	{
		 return ResponseEntity.status(500).body(new ResponseMessage("No Fields :"+ e.getMessage()));
	}
	
}
