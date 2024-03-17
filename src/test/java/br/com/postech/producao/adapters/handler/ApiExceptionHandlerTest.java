package br.com.postech.producao.adapters.handler;

import br.com.postech.producao.business.exceptions.BadRequestException;
import br.com.postech.producao.business.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ApiExceptionHandlerTest {

    @InjectMocks
    private ApiExceptionHandler apiExceptionHandler;

    @Test
    public void testHandleBadRequestException() {
        BadRequestException exception = new BadRequestException("Bad request message");
        ResponseEntity<ApiExceptionHandler.ExceptionResponse> responseEntity = apiExceptionHandler.handleTo(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Bad request message", responseEntity.getBody().getErrorMessage());
        assertEquals(ApiExceptionHandler.ErrorType.VALIDATION_FAILURE, responseEntity.getBody().getErrorType());
    }

    @Test
    public void testHandleNotFoundException() {
        NotFoundException exception = new NotFoundException("Not found message");
        ResponseEntity<ApiExceptionHandler.ExceptionResponse> responseEntity = apiExceptionHandler.handleTo(exception);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Not found message", responseEntity.getBody().getErrorMessage());
        assertEquals(ApiExceptionHandler.ErrorType.RESOURCE_NOT_FOUND, responseEntity.getBody().getErrorType());
    }

    @Test
    public void testHandleDataIntegrityViolationException() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Data integrity violation message");
        ResponseEntity<ApiExceptionHandler.ExceptionResponse> responseEntity = apiExceptionHandler.handleTo(exception);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals("Data integrity violation message", responseEntity.getBody().getErrorMessage());
        assertEquals(ApiExceptionHandler.ErrorType.PROCESS_FAILURE, responseEntity.getBody().getErrorType());
    }

    @Test
    public void testHandleGenericException() {
        Exception exception = new Exception("Generic error message");
        ResponseEntity<ApiExceptionHandler.ExceptionResponse> responseEntity = apiExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Erro inesperado encontrado no servidor durante o processamento da solicitação", responseEntity.getBody().getErrorMessage());
        assertEquals(ApiExceptionHandler.ErrorType.GENERIC_SERVER_ERROR, responseEntity.getBody().getErrorType());
    }

}