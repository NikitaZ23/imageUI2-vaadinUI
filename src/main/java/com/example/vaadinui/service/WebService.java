package com.example.vaadinui.service;

import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;

@NoArgsConstructor
public class WebService {

    @SneakyThrows
    public void createFile(MultiFileMemoryBuffer buffer, String fileName){
        InputStream inputStream = buffer.getInputStream(fileName);
        System.out.println(fileName);

        File file = new File(fileName);
        MultipartFile multipartFile =  new MockMultipartFile(fileName, buffer.getInputStream(fileName));
        FileUtils.copyInputStreamToFile(inputStream, file);
        //ImaggaVision imaggaVision = new ImaggaVision(file.getPath());

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        MultiValueMap<String, Object> body
//                = new LinkedMultiValueMap<>();
//        body.add("file", multipartFile);
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity
//                = new HttpEntity<>(body, headers);
//
//        String serverUrl = "http://localhost:8081/images";

//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate
//                .postForEntity(serverUrl, requestEntity, String.class);

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("user-file", new FileSystemResource(file));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);



        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/images/",
                HttpMethod.POST, requestEntity, String.class);
        file.delete();
        System.out.println("response status: " + response.getStatusCode());
        System.out.println("response body: " + response.getBody());


    }
}
