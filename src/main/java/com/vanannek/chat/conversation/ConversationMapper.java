package com.vanannek.chat.conversation;

import com.vanannek.chat.member.ConversationMember;
import com.vanannek.chat.member.ConversationMemberDTO;
import com.vanannek.chat.member.ConversationMemberMapper;
import com.vanannek.chat.message.ChatMessageDTO;
import com.vanannek.chat.message.MessageMapper;
import com.vanannek.chat.message.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
    ConversationMapper INSTANCE = Mappers.getMapper(ConversationMapper.class);

    @Mapping(target = "lastMessageDTO", expression = "java(findLastMessage(conversation))")
    @Mapping(target = "chatMessageDTOs", source = "chatMessages")
    @Mapping(target = "memberDTOs", source = "members", qualifiedByName = "toMemberDTOs")
    @Mapping(target = "unreadMessages", ignore = true)
    ConversationDTO toDTO(Conversation conversation);

    List<ConversationDTO> toDTOs(List<Conversation> conversations);

    @Named("toChatMessages")
    List<ChatMessageDTO> toChatMessages(List<ChatMessage> chatMessages);

    @Named("toMemberDTOs")
    default Set<ConversationMemberDTO> toMemberDTOs(Set<ConversationMember> members) {
        return members.stream()
                .map(ConversationMemberMapper.INSTANCE::toDTO)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "chatMessages", source = "chatMessageDTOs")
    @Mapping(target = "members", source = "memberDTOs", qualifiedByName = "toMembers")
    Conversation toEntity(ConversationDTO conversationDTO);

    List<Conversation> toEntities(List<ConversationDTO> conversationDTOs);

    @Named("toChatMessages")
    List<ChatMessage> toChatMessageDTOs(List<ChatMessageDTO> chatMessageDTOs);

    @Named("toMembers")
    default Set<ConversationMember> toMembers(Set<ConversationMemberDTO> memberDTOs) {
        return memberDTOs.stream()
                .map(ConversationMemberMapper.INSTANCE::toEntity)
                .collect(Collectors.toSet());
    }

    default ChatMessageDTO findLastMessage(Conversation conversation) {
        if (conversation == null || conversation.getChatMessages().isEmpty()) {
            return null;
        }

        return conversation.getChatMessages().stream()
                .max(Comparator.comparing(ChatMessage::getId))
                .map(MessageMapper.INSTANCE::toDTO)
                .orElse(null);
    }
}
