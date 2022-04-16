package mother.hackers.gallery.post;

import mother.hackers.gallery.post.dto.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "author.id", target = "authorId")
    PostDto toDto(Post post);
}
