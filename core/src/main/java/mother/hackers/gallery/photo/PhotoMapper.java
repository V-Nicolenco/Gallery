package mother.hackers.gallery.photo;

import mother.hackers.gallery.photo.dto.SavePhotoDto;
import mother.hackers.gallery.photo.dto.PhotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhotoMapper {

    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);

    Photo toEntity(SavePhotoDto dto);

    PhotoDto toDto(Photo photo);
}
