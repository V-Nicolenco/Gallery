package mother.hackers.gallery.photo;

import mother.hackers.gallery.photo.dto.PhotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhotoMapper {

    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);

    @Mapping(source = "author.id", target = "authorId")
    PhotoDto toDto(Photo photo);
}
