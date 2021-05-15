package mother.hackers.gallery.exceptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String s) {
        super(s);
    }

    public ForbiddenException(String s, Exception e) {
        super(s, e);
    }
}