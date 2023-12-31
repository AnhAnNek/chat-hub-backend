package com.vanannek.socialmedia.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "parentId", source = "parent.id", defaultExpression = "java(0L)")
    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "username", source = "user.username")
    CommentDTO toDTO(Comment comment);

    List<CommentDTO> toDTOs(List<Comment> comments);

    Comment toEntity(CommentDTO commentDTO);

    List<Comment> toEntities(List<CommentDTO> commentDTOs);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentId", source = "comment.id")
    CommentReactionDTO toReactionDTO(CommentReaction commentReaction);

    List<CommentReactionDTO> toReactionDTOs(List<CommentReaction> commentReactions);

    @Named("toReactions")
    List<CommentReaction> toReactions(List<CommentReactionDTO> commentReactionDTOs);

    CommentReaction toReactionEntity(CommentReactionDTO commentReactionDTO);

    List<CommentReaction> toReactionEntities(List<CommentReactionDTO> commentReactionDTOs);
}