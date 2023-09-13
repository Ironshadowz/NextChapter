package MiniProject.NextChapter_back.Config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import MiniProject.NextChapter_back.Exceptions.AppException;
import MiniProject.NextChapter_back.Records.ErrorDto;

@ControllerAdvice
public class RestExceptionHandler 
{    
    @ExceptionHandler(value = { AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) 
    {
        return ResponseEntity.status(ex.getHttpStatus())
                            .body(new ErrorDto(ex.getMessage()));
    }
}