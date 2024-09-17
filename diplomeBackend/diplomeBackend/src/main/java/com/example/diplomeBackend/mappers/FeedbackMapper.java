package com.example.diplomeBackend.mappers;

import com.example.diplomeBackend.dto.FeedbackDTO;
import com.example.diplomeBackend.models.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mappings({
            @Mapping(source = "user.userId", target = "userId"),
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "movie.movieId", target = "movieId"),
            @Mapping(source = "feedbackText", target = "feedbackText"),
            @Mapping(source = "feedbackRating", target = "feedbackRating"),
    })
    FeedbackDTO toDTO(Feedback feedback);

    @Mappings({
            @Mapping(source = "userId", target = "user.userId"),
            @Mapping(source = "username", target = "user.username"),
            @Mapping(source = "movieId", target = "movie.movieId"),
            @Mapping(source = "feedbackText", target = "feedbackText"),
            @Mapping(source = "feedbackRating", target = "feedbackRating"),
    })
    Feedback toEntity(FeedbackDTO feedbackDTO);
}
