package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MessageDTO;
import com.example.diplomeBackend.models.Message;
import com.example.diplomeBackend.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "sender.userId", target = "senderId")
    @Mapping(source = "receiver.userId", target = "receiverId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "timestamp", target = "timestamp")
    MessageDTO toDTO(Message message);

    @Mapping(source = "senderId", target = "sender.userId")
    @Mapping(source = "receiverId", target = "receiver.userId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "timestamp", target = "timestamp")
    Message toEntity(MessageDTO messageDTO);

    @Named("mapUserId")
    default User mapUserId(Long userId) {
        return new User(userId);
    }
}
