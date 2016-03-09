package org.example.api;

import org.example.web.DefaultExceptionAttributes;
import org.example.web.ExceptionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by sergeikostin on 3/6/16.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // when exception is thrown from our mapped request controller method,
    // Spring searches for an Exception handler method configured to handle that specific Exception thrown, or any
    // Exception type in its class Hierarchy. It may return a Spring view name or ResponseEntity
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception, HttpServletRequest request){

        logger.error("> handleException");
        logger.error("- Exception: ", exception );

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);


        logger.error("< handleException");
        return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Map<String, Object>> handleNoResultException(NoResultException exception, HttpServletRequest request){

        logger.error("> handleNoResultException");

        ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();

        Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, HttpStatus.NOT_FOUND);


        logger.error("< handleNoResultException");
        return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.NOT_FOUND);

    }


}
