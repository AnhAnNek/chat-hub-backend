package com.vanannek.chat.member;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ConversationMemberMapper {
    ConversationMemberMapper INSTANCE = Mappers.getMapper(ConversationMemberMapper.class);

    @Mapping(target = "memberUsername", source = "member.username")
    @Mapping(target = "conversationId", source = "conversation.id")
    ConversationMemberDTO toDTO(ConversationMember conversationMember);

    Set<ConversationMemberDTO> toDTOs(Set<ConversationMember> members);

    ConversationMember toEntity(ConversationMemberDTO ChatMessageDTO);

    Set<ConversationMember> toEntities(Set<ConversationMemberDTO> memberDTOs);
}
