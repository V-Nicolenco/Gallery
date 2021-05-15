package mother.hackers.gallery.exceptions;

public class ImageUploadException extends RuntimeException {

    public ImageUploadException(String s) {
        super(s);
    }

    public ImageUploadException(String s, Exception e) {
        super(s, e);
    }
}
