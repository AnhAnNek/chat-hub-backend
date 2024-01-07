package com.vanannek.chat.message;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "senderUsername", source = "sender.username")
    @Mapping(target = "conversationId", source = "conversation.id")
    ChatMessageDTO toDTO(ChatMessage chatMessage);

    List<ChatMessageDTO> toDTOs(List<ChatMessage> chatMessages);

    ChatMessage toEntity(ChatMessageDTO ChatMessageDTO);

    List<ChatMessage> toEntities(List<ChatMessageDTO> chatMessageDTOs);
}
