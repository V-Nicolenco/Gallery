package mother.hackers.gallery.photo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavePhotoDto {

    private String data;
    private String description;
    private boolean isOpen;
}
