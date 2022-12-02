package com.bianchini.apicrudclient.resources.exceptions;

import com.bianchini.apicrudclient.services.exceptions.ClientNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

/*
Controlador rest que gerencia a requisição quando recebe uma exception.
 */

@ControllerAdvice                   //Annotation que define que é um recurso para quando chegar uma exception
public class ResourceExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)                    //Annotation para o método gerenciar uma exception e assim dar uma resposta apropriada.
    public ResponseEntity<StandardError> clientNotFound(ClientNotFoundException e, HttpServletRequest req){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Client not found");
        err.setMessage(e.getMessage());
        err.setPath(req.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
