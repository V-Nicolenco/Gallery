package mother.hackers.gallery.utils;

import mother.hackers.gallery.exceptions.ImageUploadException;
import mother.hackers.gallery.photo.ImageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class MultipartFileUtils {

    private MultipartFileUtils() {
    }

    public static ImageData getData(MultipartFile file) {
        try {
            ImageData data = new ImageData();
            data.setData(file.getBytes());
            data.setType(file.getContentType());
            return data;
        } catch (IOException e) {
            throw new ImageUploadException("Couldn't upload image on the server!", e);
        }
    }
}
