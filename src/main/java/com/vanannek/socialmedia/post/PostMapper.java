package com.vanannek.socialmedia.post;

import com.vanannek.socialmedia.postreaction.PostReaction;
import com.vanannek.socialmedia.postreaction.PostReactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "username", source = "user.username")
    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatusToString")
    PostDTO toDTO(Post post);

    @Named("mapStatusToString")
    default String mapStatusToString(Post.EStatus status) {
        return status.name();
    }

    List<PostDTO> toDTOs(List<Post> posts);

    Post toEntity(PostDTO postDTO);

    List<Post> toEntities(List<PostDTO> postDTOs);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "postId", source = "post.id")
    PostReactionDTO toReactionDTO(PostReaction postReaction);

    List<PostReactionDTO> toReactionDTOs(List<PostReaction> postReactions);

    @Named("toReactions")
    List<PostReaction> toReactions(List<PostReactionDTO> postReactionDTOs);

    PostReaction toReactionEntity(PostReactionDTO postReactionDTO);

    List<PostReaction> toReactionEntities(List<PostReactionDTO> postReactionDTOs);
}
