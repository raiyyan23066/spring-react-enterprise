package com.app.backend.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourseNotFoundException extends RuntimeException {
     public ResourseNotFoundException(String msg){
         super(msg);
     }

     public ResourseNotFoundException(String resourseName,String feildName,Object feildValue){
         super(String.format("%s not found with %s : ' %s ' ",resourseName,feildName,feildValue));
     }
}
