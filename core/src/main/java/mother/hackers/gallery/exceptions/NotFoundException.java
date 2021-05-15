package mother.hackers.gallery.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String s) {
        super(s);
    }

    public NotFoundException(String s, Exception e) {
        super(s, e);
    }
}
