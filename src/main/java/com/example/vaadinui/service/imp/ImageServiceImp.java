package com.example.vaadinui.service.imp;

import com.example.vaadinui.dto.ImageDto;
import com.example.vaadinui.service.ImageService;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImp implements ImageService {
    private static final String HTTP_LOCALHOST_8081_IMAGES = "http://localhost:8081/images";
    RestTemplate restTemplate = new RestTemplate();

    @SneakyThrows
    @Override
    public void createFile(MultiFileMemoryBuffer buffer, String fileName) {
        InputStream inputStream = buffer.getInputStream(fileName);
        System.out.println(fileName);

        File file = new File("temp/" + fileName);
        FileUtils.copyInputStreamToFile(inputStream, file);

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("file", new FileSystemResource(file));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        ResponseEntity<ImageDto> response = restTemplate.exchange(HTTP_LOCALHOST_8081_IMAGES + "/",
                HttpMethod.POST, requestEntity, ImageDto.class);

        file.delete();
        System.out.println("response status: " + response.getStatusCode());
        System.out.println("response body: " + response.getBody());
    }
    @Override
    public List<ImageDto> getImages() {
        ResponseEntity<ImageDto[]> responseEntity = restTemplate.getForEntity(HTTP_LOCALHOST_8081_IMAGES, ImageDto[].class);
        ImageDto[] objects = responseEntity.getBody();

        return Arrays.stream(objects).collect(Collectors.toList());
    }
    @Override
    public ImageDto getImageName(String name) {
        ResponseEntity<ImageDto> responseEntity = restTemplate.getForEntity(HTTP_LOCALHOST_8081_IMAGES + "/im/" + name, ImageDto.class);

        return responseEntity.getBody();
    }
    @Override
    public ImageDto getImage(int id) {
        ResponseEntity<ImageDto> responseEntity = restTemplate.getForEntity(HTTP_LOCALHOST_8081_IMAGES + "/id/" + id, ImageDto.class);

        return responseEntity.getBody();
    }
}
