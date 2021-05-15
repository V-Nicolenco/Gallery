package mother.hackers.gallery.comment;

import mother.hackers.gallery.comment.dto.CommentDto;
import mother.hackers.gallery.comment.dto.CreateCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment toEntity(CreateCommentDto dto);

    CommentDto toDto(Comment comment);

    List<CommentDto> toDto(List<Comment> comments);
}
