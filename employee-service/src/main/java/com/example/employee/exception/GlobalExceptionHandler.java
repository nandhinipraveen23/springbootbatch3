package com.example.employee.exception;
import java.util.*; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import org.springframework.dao.DataIntegrityViolationException;
@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(InvalidCredentialsException.class) public ResponseEntity<Map<String,Object>> unauthorized(InvalidCredentialsException e){return response(HttpStatus.UNAUTHORIZED,e.getMessage());}
 @ExceptionHandler(ResourceNotFoundException.class) public ResponseEntity<Map<String,Object>> notFound(ResourceNotFoundException e){return response(HttpStatus.NOT_FOUND,e.getMessage());}
 @ExceptionHandler(IllegalArgumentException.class) public ResponseEntity<Map<String,Object>> badRequest(IllegalArgumentException e){return response(HttpStatus.BAD_REQUEST,e.getMessage());}
 @ExceptionHandler(DataIntegrityViolationException.class) public ResponseEntity<Map<String,Object>> conflict(Exception e){return response(HttpStatus.CONFLICT,"Email already exists or data violates a database constraint.");}
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException e){String msg=e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).findFirst().orElse("Invalid request");return response(HttpStatus.BAD_REQUEST,msg);}
 private ResponseEntity<Map<String,Object>> response(HttpStatus s,String message){Map<String,Object> body=new LinkedHashMap<>();body.put("status",s.value());body.put("error",s.getReasonPhrase());body.put("message",message);body.put("timestamp",java.time.Instant.now().toString());return ResponseEntity.status(s).body(body);}
}
