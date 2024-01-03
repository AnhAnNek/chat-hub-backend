package com.vanannek.socialmedia.comment;

import com.vanannek.socialmedia.commentreaction.CommentReaction;
import com.vanannek.socialmedia.commentreaction.CommentReactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "reactions", ignore = true)
    CommentDTO toDTO(Comment comment);

    List<CommentDTO> toDTOs(List<Comment> comments);

    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "reactions", ignore = true)
    Comment toEntity(CommentDTO commentDTO);

    List<Comment> toEntities(List<CommentDTO> commentDTOs);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentId", source = "comment.id")
    CommentReactionDTO toReactionDTO(CommentReaction commentReaction);

    List<CommentReactionDTO> toReactionDTOs(List<CommentReaction> commentReactions);

    @Named("toReactions")
    List<CommentReaction> toReactions(List<CommentReactionDTO> commentReactionDTOs);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comment", ignore = true)
    CommentReaction toReactionEntity(CommentReactionDTO commentReactionDTO);

    List<CommentReaction> toReactionEntities(List<CommentReactionDTO> commentReactionDTOs);
}