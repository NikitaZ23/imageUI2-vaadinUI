package com.example.vaadinui.service;

import com.example.vaadinui.dto.ImWithTagsDto;
import com.example.vaadinui.service.imp.IWTServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class IWTServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    IWTServiceImp serviceImp;

    @Test
    @DisplayName("поиск зависимостей тега и картинок")
    public void getIWTTgTest() {
        ImWithTagsDto[] imWithTagsDtos = new ImWithTagsDto[2];
        imWithTagsDtos[0] = new ImWithTagsDto(0, UUID.randomUUID(), 1, 1);
        imWithTagsDtos[1] = new ImWithTagsDto(1, UUID.randomUUID(), 2, 1);

        Mockito
                .when(restTemplate.getForEntity("http://localhost:8081/iwt/tg/1", ImWithTagsDto[].class))
                .thenReturn(new ResponseEntity<>(imWithTagsDtos, HttpStatus.OK));

        List<ImWithTagsDto> iwtTags = serviceImp.getIwtTags(1);

        Assertions.assertEquals(iwtTags, Arrays.stream(imWithTagsDtos).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("поиск зависимостей картинки и тегов")
    public void getIWTImTest() {
        ImWithTagsDto[] imWithTagsDtos = new ImWithTagsDto[2];
        imWithTagsDtos[0] = new ImWithTagsDto(0, UUID.randomUUID(), 1, 1);
        imWithTagsDtos[1] = new ImWithTagsDto(1, UUID.randomUUID(), 1, 2);

        Mockito
                .when(restTemplate.getForEntity("http://localhost:8081/iwt/im/1", ImWithTagsDto[].class))
                .thenReturn(new ResponseEntity<>(imWithTagsDtos, HttpStatus.OK));

        List<ImWithTagsDto> iwtTags = serviceImp.getIwtImages(1);

        Assertions.assertEquals(iwtTags, Arrays.stream(imWithTagsDtos).collect(Collectors.toList()));
    }
}
