package za.co.valr.valrtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFound extends Exception {
    public NotFound(String s) {
        super(s);
    }
}
