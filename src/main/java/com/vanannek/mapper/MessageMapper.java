package com.vanannek.mapper;

import com.vanannek.dto.ChatMessageDTO;
import com.vanannek.entity.ChatMessage;
import com.vanannek.entity.Conversation;
import com.vanannek.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "senderUsername", source = "sender.username")
    @Mapping(target = "conversationId", source = "conversation.id")
    ChatMessageDTO toDTO(ChatMessage chatMessage);

    List<ChatMessageDTO> toDTOs(List<ChatMessage> chatMessages);

    @Mapping(target = "sender.username", source = "senderUsername")
    @Mapping(target = "conversation.id", source = "conversationId")
    ChatMessage toEntity(ChatMessageDTO ChatMessageDTO);

    List<ChatMessage> toEntities(List<ChatMessageDTO> chatMessageDTOs);
}
