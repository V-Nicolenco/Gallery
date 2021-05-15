package mother.hackers.gallery.photo.dto;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class ImageData {

    @Id
    @GeneratedValue
    private long id;
    private byte[] data;
    private String type;
}
