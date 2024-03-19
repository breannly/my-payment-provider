package com.example.mygatewayuserservice.exception.handler;

import com.example.mygatewayuserservice.exception.ApiException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class ErrorAttributes extends DefaultErrorAttributes {

    public ErrorAttributes() {
        super();
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        var errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        var error = getError(request);

        errorAttributes.put("status", determineHttpStatus(error).value());
        errorAttributes.put("errors", createErrorList(error));

        return errorAttributes;
    }

    private HttpStatus determineHttpStatus(Throwable error) {
        return error instanceof ApiException ? ((ApiException) error).getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private Map<String, Object> createErrorList(Throwable error) {
        var message = Optional.ofNullable(error.getMessage()).orElse(error.getClass().getName());
        var errorMap = createErrorMap(message);

        var errors = new HashMap<String, Object>();
        errors.put("errors", Collections.singletonList(errorMap));
        return errors;
    }

    private Map<String, Object> createErrorMap(String message) {
        var errorMap = new LinkedHashMap<String, Object>();
        errorMap.put("message", message);
        return errorMap;
    }
}
