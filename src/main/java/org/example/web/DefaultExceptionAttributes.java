package org.example.web;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sergeikostin on 3/8/16.
 */
public class DefaultExceptionAttributes implements ExceptionAttributes {

    public static final String TIMESTAMP = "timestamp";
    public static final String STATUS = "status";
    public static final String ERROR = "error";
    public static final String EXCEPTION = "exception";
    public static final String MESSAGE = "message";
    public static final String PATH = "path";


    public void addHttpStatus(Map<String, Object> exceptionAttributes, HttpStatus httpStatus){
        exceptionAttributes.put(STATUS, httpStatus.value());
        exceptionAttributes.put(ERROR, httpStatus.getReasonPhrase());
    }

    public void addExceptionDetail(Map<String, Object> exceptionAttributes, Exception exception){
        exceptionAttributes.put(EXCEPTION, exception.getClass().getName());
        exceptionAttributes.put(MESSAGE, exception.getMessage());
    }

    public void addPath(Map<String, Object> exceptionAttributes, HttpServletRequest httpServletRequest){
        exceptionAttributes.put(PATH, httpServletRequest.getServletPath());
    }


    @Override
    public Map<String, Object> getExceptionAttributes(Exception exception, HttpServletRequest httpServletRequest, HttpStatus httpStatus) {
        Map<String, Object> exceptionAttributes = new LinkedHashMap<String, Object>();

        exceptionAttributes.put(TIMESTAMP, new Date());
        addHttpStatus(exceptionAttributes, httpStatus);
        addExceptionDetail(exceptionAttributes, exception);
        addPath(exceptionAttributes, httpServletRequest);

        return exceptionAttributes;
    }
}
