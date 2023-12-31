package com.vanannek.mapper;

import com.vanannek.dto.NotificationDTO;
import com.vanannek.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "username", source = "user.username")
    NotificationDTO toDTO(Notification notification);

    List<NotificationDTO> toDTOs(List<Notification> notifications);

    Notification toEntity(NotificationDTO notificationDTO);

    List<Notification> toEntities(List<NotificationDTO> notificationDTOs);
}
