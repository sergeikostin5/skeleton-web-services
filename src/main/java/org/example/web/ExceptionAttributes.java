package org.example.web;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by sergeikostin on 3/8/16.
 */
public interface ExceptionAttributes {

    Map<String, Object> getExceptionAttributes(Exception exception,
                                               HttpServletRequest httpServletRequest, HttpStatus httpStatus);

}
