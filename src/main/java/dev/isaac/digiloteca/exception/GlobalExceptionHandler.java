package dev.isaac.digiloteca.exception;

import dev.isaac.digiloteca.dto.ErroResponse;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroResponse> tratarResponseStatusException(
            ResponseStatusException exception,
            HttpServletRequest request) {

        int status = exception.getStatusCode().value();

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                status,
                exception.getStatusCode().toString(),
                exception.getReason(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity
                .status(exception.getStatusCode())
                .body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarValidacao(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        Map<String, String> campos = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError ->
                        campos.put(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        )
                );

        ErroResponse erro = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Erro de validação.",
                request.getRequestURI(),
                campos
        );

        return ResponseEntity
                .badRequest()
                .body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
public ResponseEntity<ErroResponse> tratarJsonInvalido(
        HttpMessageNotReadableException exception,
        HttpServletRequest request) {

    ErroResponse erro = new ErroResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "JSON inválido ou valor informado não é aceito.",
            request.getRequestURI(),
            null
    );

    return ResponseEntity
            .badRequest()
            .body(erro);
}
}