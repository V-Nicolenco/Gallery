package mother.hackers.gallery.comment;

import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment toEntity(CreateCommentDto dto);

    @Mapping(source = "author.id", target = "authorId")
    CommentDto toDto(Comment comment);

    @Mapping(source = "author.id", target = "authorId")
    List<CommentDto> toDto(List<Comment> comments);
}
