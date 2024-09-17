package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.MessageDTO;
import com.example.diplomeBackend.models.Message;
import com.example.diplomeBackend.models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-17T14:31:46+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDTO toDTO(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setSenderId( messageSenderUserId( message ) );
        messageDTO.setReceiverId( messageReceiverUserId( message ) );
        messageDTO.setContent( message.getContent() );
        messageDTO.setTimestamp( message.getTimestamp() );
        messageDTO.setId( message.getId() );

        return messageDTO;
    }

    @Override
    public Message toEntity(MessageDTO messageDTO) {
        if ( messageDTO == null ) {
            return null;
        }

        Message message = new Message();

        message.setSender( messageDTOToUser( messageDTO ) );
        message.setReceiver( messageDTOToUser1( messageDTO ) );
        message.setContent( messageDTO.getContent() );
        message.setTimestamp( messageDTO.getTimestamp() );
        message.setId( messageDTO.getId() );

        return message;
    }

    private Long messageSenderUserId(Message message) {
        if ( message == null ) {
            return null;
        }
        User sender = message.getSender();
        if ( sender == null ) {
            return null;
        }
        Long userId = sender.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    private Long messageReceiverUserId(Message message) {
        if ( message == null ) {
            return null;
        }
        User receiver = message.getReceiver();
        if ( receiver == null ) {
            return null;
        }
        Long userId = receiver.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }

    protected User messageDTOToUser(MessageDTO messageDTO) {
        if ( messageDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( messageDTO.getSenderId() );

        return user;
    }

    protected User messageDTOToUser1(MessageDTO messageDTO) {
        if ( messageDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( messageDTO.getReceiverId() );

        return user;
    }
}
