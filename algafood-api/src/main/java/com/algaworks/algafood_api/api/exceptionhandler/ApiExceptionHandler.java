package com.algaworks.algafood_api.api.exceptionhandler;

import com.algaworks.algafood_api.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood_api.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood_api.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

        if(ex instanceof MethodArgumentTypeMismatchException){
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, webRequest);
        }

        return super.handleTypeMismatch(ex, headers, status, webRequest);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers,
                                                                    HttpStatus status, WebRequest webRequest){

        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
        String detail = String.format("O parâmetro '%s' recebeu o valor '%s', que é um tipo inválido. Corrija e informa um valor" +
                "compatível com o tipo '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return this.handleExceptionInternal(ex, problem, headers, status, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente!", ex.getRequestURL());

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return this.handleExceptionInternal(ex, problem, headers, status, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if(rootCause instanceof InvalidFormatException){
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, webRequest);
        } else if(rootCause instanceof PropertyBindingException){
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, webRequest);
        }

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREESIVEL;
        String detail = "O corpo da requisição está inválido! Por favor verifique a sintaxe";

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return super.handleExceptionInternal(ex, problem, headers,  status, webRequest);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest){

        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREESIVEL;
        String detail = String.format("A propriedade %s recebeu o valor '%s', que é de um tipo inválido. Corrija e informe " +
                "um valor compatível com o tipo '%s'", path, ex.getValue(), ex.getTargetType().getSimpleName());
        Problem problem = this.createProblemBuilder(status, problemType, detail).build();

        return this.handleExceptionInternal(ex, problem, headers, status, webRequest);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
                                                                       HttpStatus status, WebRequest webRequest){

        String path = joinPath(ex.getPath());
        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREESIVEL;
        String detail = String.format("Erro ao inserir dados na propriedade '%s'! Ela não existe no contexto da classe '%s'." +
                                    " Corrija ou remova e tente novamente",
                                    path, ex.getReferringClass().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return this.handleExceptionInternal(ex, problem, headers, status, webRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest webRequest){

        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir," +
                "entre em contato com o administrador do sistema.";
        Problem problem = createProblemBuilder(status, problemType, detail).build();

        ex.printStackTrace();
        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest webRequest){

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;

        Problem problem = this.createProblemBuilder(status, problemType, ex.getMessage()).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest webRequest){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;

        Problem problem = this.createProblemBuilder(status, problemType, ex.getMessage()).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, webRequest);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest webRequest){

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;

        Problem problem = this.createProblemBuilder(status, problemType, ex.getMessage()).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(body == null){
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value()) //value -> irá retornar o número inteiro desse status ex: 404
                    .build();
        } else if(body instanceof String){
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

    private String joinPath(List<Reference> references){
        return references.stream().map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

}
