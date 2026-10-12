package com.example.department.exception;
import java.util.*; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import org.springframework.dao.DataIntegrityViolationException;
@RestControllerAdvice public class GlobalExceptionHandler {
 @ExceptionHandler(ResourceNotFoundException.class) public ResponseEntity<Map<String,Object>> notFound(ResourceNotFoundException e){return make(HttpStatus.NOT_FOUND,e.getMessage());}
 @ExceptionHandler(IllegalArgumentException.class) public ResponseEntity<Map<String,Object>> bad(IllegalArgumentException e){return make(HttpStatus.BAD_REQUEST,e.getMessage());}
 @ExceptionHandler(DataIntegrityViolationException.class) public ResponseEntity<Map<String,Object>> conflict(Exception e){return make(HttpStatus.CONFLICT,"Department name already exists or data violates a constraint.");}
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException e){return make(HttpStatus.BAD_REQUEST,e.getBindingResult().getFieldErrors().stream().map(x->x.getField()+": "+x.getDefaultMessage()).findFirst().orElse("Invalid request"));}
 private ResponseEntity<Map<String,Object>> make(HttpStatus s,String m){Map<String,Object>b=new LinkedHashMap<>();b.put("status",s.value());b.put("message",m);b.put("timestamp",java.time.Instant.now().toString());return ResponseEntity.status(s).body(b);}
}
