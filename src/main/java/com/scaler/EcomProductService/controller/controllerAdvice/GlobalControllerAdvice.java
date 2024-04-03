package com.scaler.EcomProductService.controller.controllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scaler.EcomProductService.dto.ErrorResponseDTO;
import com.scaler.EcomProductService.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.net.http.HttpResponse;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundException(Exception ex){
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setMessageCode(404);
        return new ResponseEntity<>(errorResponse ,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<ErrorResponseDTO> handleHTTPClientErrorException(Exception ex) {
        String exceptionMessage = ex.getMessage().substring(7,ex.getMessage().length()-1);
        Gson gson = new Gson();
        ErrorResponseDTO errorResponse = gson.fromJson(exceptionMessage, ErrorResponseDTO.class);
        return new ResponseEntity<>(errorResponse , HttpStatus.valueOf(errorResponse.getMessageCode()));
    }
}
