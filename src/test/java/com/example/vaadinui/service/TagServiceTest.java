package com.example.vaadinui.service;

import com.example.vaadinui.dto.TagDto;
import com.example.vaadinui.service.imp.TagServiceImp;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    TagServiceImp serviceImp;

    @Test
    @DisplayName("поиск тега по id")
    public void getTagTest() {
        TagDto tagDto = new TagDto(1, UUID.randomUUID(), "tag1", LocalDateTime.now(), LocalDateTime.now());

        Mockito
                .when(restTemplate.getForEntity("http://localhost:8081/tags/tg/1", TagDto.class))
                .thenReturn(new ResponseEntity<>(tagDto, HttpStatus.OK));

        TagDto tag = serviceImp.getTag(1);

        Assertions.assertEquals(tagDto, tag);
    }

    @Test
    @DisplayName("поиск тега по name")
    public void getTagNameTest() {
        TagDto tagDto = new TagDto(1, UUID.randomUUID(), "tag1", LocalDateTime.now(), LocalDateTime.now());

        Mockito
                .when(restTemplate.getForEntity("http://localhost:8081/tags/name/tag1", TagDto.class))
                .thenReturn(new ResponseEntity<>(tagDto, HttpStatus.OK));

        TagDto tag = serviceImp.getTag("tag1");

        Assertions.assertEquals(tagDto, tag);
    }

    @Test
    @DisplayName("поиск тегов")
    public void getTagsTest() {
        TagDto[] tagDto = new TagDto[2];
        tagDto[0] = new TagDto(1, UUID.randomUUID(), "tag1", LocalDateTime.now(), LocalDateTime.now());
        tagDto[1] = new TagDto(2, UUID.randomUUID(), "tag2", LocalDateTime.now(), LocalDateTime.now());

        Mockito
                .when(restTemplate.getForEntity("http://localhost:8081/tags", TagDto[].class))
                .thenReturn(new ResponseEntity<>(tagDto, HttpStatus.OK));

        List<TagDto> tags = serviceImp.getTags();

        Assertions.assertEquals(Arrays.stream(tagDto).collect(Collectors.toList()), tags);
    }
}
