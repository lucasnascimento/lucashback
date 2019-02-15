package ln.lucashback.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DiscoNotFoundException extends RuntimeException {
    public DiscoNotFoundException(String id) {
        super(id + " - not found.");
    }

}
