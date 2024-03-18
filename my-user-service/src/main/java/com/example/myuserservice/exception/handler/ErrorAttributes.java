package com.example.myuserservice.exception.handler;

import com.example.mypaymentprovider.api.individual.model.Status;
import com.example.myuserservice.exception.ApiException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class ErrorAttributes extends DefaultErrorAttributes {

    public ErrorAttributes() {
        super();
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        var errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        var error = getError(request);

        errorAttributes.put("http_status", determineHttpStatus(error).value());
        errorAttributes.put("response_status", determineResponseStatus(error).name());
        errorAttributes.put("description", error.getMessage());

        return errorAttributes;
    }

    private HttpStatus determineHttpStatus(Throwable error) {
        return error instanceof ApiException ? ((ApiException) error).getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private Status determineResponseStatus(Throwable error) {
        return error instanceof ApiException ? ((ApiException) error).getResponseStatus() : Status.ERROR;
    }
}
