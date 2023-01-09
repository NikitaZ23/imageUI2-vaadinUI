package com.example.vaadinui.service;

import com.example.vaadinui.dto.ImageDto;
import com.example.vaadinui.service.imp.ImageServiceImp;
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
public class ImageServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    ImageServiceImp serviceImp;

//    @Test
//    @DisplayName("создание картинки")
//    public void createImageTest() {
//        ImageDto imageDto = new ImageDto(1, UUID.randomUUID(), "1", LocalDateTime.now(), LocalDateTime.now());
//
//        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
//        //bodyMap.add("file", new FileSystemResource(file));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
//
//        Mockito
//                .when(restTemplate.exchange("http://localhost:8081/images/",
//                        HttpMethod.POST, requestEntity, ImageDto.class))
//                .thenReturn(new ResponseEntity<>(imageDto, HttpStatus.CREATED));
//
//        ImageDto image = serviceImp.createFile(new MultiFileMemoryBuffer(), "1");
//
//        Assertions.assertEquals(image, imageDto);
//    }

    @Test
    @DisplayName("поиск картинки по id")
    public void getImageTest() {
        ImageDto imageDto = new ImageDto(1, UUID.randomUUID(), "1", LocalDateTime.now(), LocalDateTime.now());

        Mockito
                .when(restTemplate.getForEntity("http://localhost:8081/images/id/1", ImageDto.class))
                .thenReturn(new ResponseEntity<>(imageDto, HttpStatus.OK));

        ImageDto image = serviceImp.getImage(1);

        Assertions.assertEquals(image, imageDto);
    }

    @Test
    @DisplayName("поиск картинки по имени")
    public void getImageNameTest() {
        ImageDto imageDto = new ImageDto(1, UUID.randomUUID(), "1", LocalDateTime.now(), LocalDateTime.now());

        Mockito
                .when(restTemplate.getForEntity("http://localhost:8081/images/im/1", ImageDto.class))
                .thenReturn(new ResponseEntity<>(imageDto, HttpStatus.OK));

        ImageDto image = serviceImp.getImageName("1");

        Assertions.assertEquals(image, imageDto);
    }

    @Test
    @DisplayName("поиск картинок")
    public void getImagesTest() {
        ImageDto[] imageDto = new ImageDto[2];
        imageDto[0] = new ImageDto(1, UUID.randomUUID(), "tag1", LocalDateTime.now(), LocalDateTime.now());
        imageDto[1] = new ImageDto(2, UUID.randomUUID(), "tag2", LocalDateTime.now(), LocalDateTime.now());

        Mockito
                .when(restTemplate.getForEntity("http://localhost:8081/images", ImageDto[].class))
                .thenReturn(new ResponseEntity<>(imageDto, HttpStatus.OK));

        List<ImageDto> images = serviceImp.getImages();

        Assertions.assertEquals(Arrays.stream(imageDto).collect(Collectors.toList()), images);
    }
}
