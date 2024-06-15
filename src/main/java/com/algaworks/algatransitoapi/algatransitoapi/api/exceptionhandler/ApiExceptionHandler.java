package com.algaworks.algatransitoapi.algatransitoapi.api.exceptionhandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algatransitoapi.algatransitoapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algatransitoapi.algatransitoapi.domain.exception.NegocioException;

import lombok.AllArgsConstructor;

//responsável por capturar exceções globais
@RestControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
    
    private final MessageSource messageSource;
    
    @ExceptionHandler(NegocioException.class)
    public ProblemDetail handleNegocio(NegocioException e){
        //instancia um problem detail informamos qual é o status HTTP
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        //construímos o problem detail para retornar para o usuário
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("https://algatransito.com/erros/regra-de-negocio"));
        return problemDetail;
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ProblemDetail handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e){
        //instancia um problem detail informamos qual é o status HTTP
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        //construímos o problem detail para retornar para o usuário
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("https://algatransito.com/erros/recurso-nao-encontrado"));
        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Recurso está em uso");
        problemDetail.setType(URI.create("https://algatransito.com/erros/recurso-em-uso"));
        return problemDetail;
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("Um ou mais campos estão inválidos");
        problemDetail.setType(URI.create("https://algatransito.com/erros/campos-invalidos"));
        
        //cria-se um map uma estrutura de dados de chave-valor
        //O BindingResult contém os resultados da validação de um formulário ou de um objeto de comando, incluindo quaisquer erros de validação.
        //getAllErrors() Este é um método do BindingResult que retorna uma lista de todos os erros de validação que ocorreram durante a validação. 
        Map<String, String> fields = ex.getBindingResult().getAllErrors()
                //.stream() no final da linha de código, você estará convertendo a lista de erros de validação para um fluxo (stream) de elementos.
                .stream()
                //Collectors.toMap(...) é um coletor que coleta os elementos de um fluxo em um mapa.
                //objectError -> ((FieldError) objectError).getField(): Esse lambda é usado para definir as chaves do mapa. Ele extrai o nome do campo que falhou na validação 
                //de cada objeto de erro. Para isso, ele realiza um cast para FieldError e, em seguida, obtém o nome do campo através do método getField().
                //Na validação de objetos em Spring MVC, os erros de validação são representados por objetos do tipo ObjectError. 
                //No entanto, quando ocorre um erro de validação de um campo específico, o Spring MVC retorna um FieldError, que é uma subclasse de ObjectError. O FieldError contém informações específicas sobre o campo que falhou na validação, como o nome do campo e o valor rejeitado
                .collect(Collectors.toMap(objectError -> ((FieldError) objectError).getField(),
                //Este lambda é usado para definir os valores do mapa. Ele obtém a mensagem de erro associada a cada objeto de erro usando um MessageSource 
                //(um mecanismo para obter mensagens internacionalizadas em Java), utilizando o LocaleContextHolder para obter o local atual.
                    objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale())));
        
        problemDetail.setProperty("fields", fields);
        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

}
