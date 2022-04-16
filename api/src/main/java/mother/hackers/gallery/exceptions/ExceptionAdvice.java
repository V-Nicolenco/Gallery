package mother.hackers.gallery.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionAdvice {

    Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto handle(NotFoundException e) {
        String message = e.getMessage();
        logger.error(message, e);
        return new ErrorDto(message);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ErrorDto handle(ForbiddenException e) {
        String message = e.getMessage();
        logger.error(message, e);
        return new ErrorDto(message);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorDto handle(IllegalArgumentException e) {
        String message = e.getMessage();
        logger.warn(message, e);
        return new ErrorDto(message);
    }
}
