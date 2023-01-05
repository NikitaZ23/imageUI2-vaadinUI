package com.example.vaadinui.service;

import com.example.vaadinui.common.FindIWTRequest;
import com.example.vaadinui.dto.ImWithTagsDto;
import com.example.vaadinui.dto.ImageDto;
import com.example.vaadinui.dto.TagDto;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WebService {

    RestTemplate restTemplate;

    public WebService() {
        restTemplate = new RestTemplate();
    }

    @SneakyThrows
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

        ResponseEntity<ImageDto> response = restTemplate.exchange("http://localhost:8081/images/",
                HttpMethod.POST, requestEntity, ImageDto.class);

        file.delete();
        System.out.println("response status: " + response.getStatusCode());
        System.out.println("response body: " + response.getBody());
    }

    public List<ImageDto> getImages() {
        ResponseEntity<ImageDto[]> responseEntity = restTemplate.getForEntity("http://localhost:8081/images", ImageDto[].class);
        ImageDto[] objects = responseEntity.getBody();

        return Arrays.stream(objects).collect(Collectors.toList());
    }

    public ImageDto getImageName(String name){
        ResponseEntity<ImageDto> responseEntity = restTemplate.getForEntity("http://localhost:8081/images/im/" + name, ImageDto.class);

        return responseEntity.getBody();
    }

    public ImageDto getImage(int id) {
        ResponseEntity<ImageDto> responseEntity = restTemplate.getForEntity("http://localhost:8081/images/id/" + id, ImageDto.class);

        return responseEntity.getBody();
    }

    public List<ImWithTagsDto> getIwtTags(int id) {
        ResponseEntity<ImWithTagsDto[]> responseEntity = restTemplate.getForEntity("http://localhost:8081/iwt/tg/" + id, ImWithTagsDto[].class);
        ImWithTagsDto[] objects = responseEntity.getBody();

        return Arrays.stream(objects).collect(Collectors.toList());
    }

    public List<TagDto> getIwtTagsName(int id) {
        List<TagDto> tags = new ArrayList<>();
        getIwtImages(id).forEach(imWithTagsDto -> tags.add(getTag(imWithTagsDto.getId_tg())));

        return tags;
    }

    public List<ImWithTagsDto> getIwtImages(int id) {
        ResponseEntity<ImWithTagsDto[]> responseEntity = restTemplate.getForEntity("http://localhost:8081/iwt/im/" + id, ImWithTagsDto[].class);
        ImWithTagsDto[] objects = responseEntity.getBody();

        return Arrays.stream(objects).collect(Collectors.toList());
    }

    public void deleteTag(int id_im, int id_tg){
        restTemplate = new RestTemplate(new CustomHttpComponentsClientHttpRequestFactory());
        FindIWTRequest findIWTRequest = new FindIWTRequest(id_im, id_tg);
        HttpEntity<FindIWTRequest> request = new HttpEntity<>(findIWTRequest);

        System.out.println(findIWTRequest);

        ResponseEntity<ImWithTagsDto> responseEntity = restTemplate.exchange("http://localhost:8081/iwt/oneOb", HttpMethod.GET, request, ImWithTagsDto.class);
        ImWithTagsDto imWithTagsDto = responseEntity.getBody();
        System.out.println(imWithTagsDto);

        restTemplate.delete("http://localhost:8081/iwt/" + imWithTagsDto.getUuid());
    }

    public TagDto getTag(int id) {
        ResponseEntity<TagDto> responseEntity = restTemplate.getForEntity("http://localhost:8081/tags/tg/" + id, TagDto.class);

        return responseEntity.getBody();
    }

    public TagDto getTag(String name) {
        ResponseEntity<TagDto> responseEntity = restTemplate.getForEntity("http://localhost:8081/tags/name/" + name, TagDto.class);

        return responseEntity.getBody();
    }

    public List<TagDto> getTags() {
        ResponseEntity<TagDto[]> responseEntity = restTemplate.getForEntity("http://localhost:8081/tags", TagDto[].class);
        TagDto[] objects = responseEntity.getBody();

        return Arrays.stream(objects).collect(Collectors.toList());
    }

    private static final class CustomHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {

            if (HttpMethod.GET.equals(httpMethod)) {
                return new HttpEntityEnclosingGetRequestBase(uri);
            }
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }

    private static final class HttpEntityEnclosingGetRequestBase extends HttpEntityEnclosingRequestBase {

        public HttpEntityEnclosingGetRequestBase(final URI uri) {
            super.setURI(uri);
        }

        @Override
        public String getMethod() {
            return HttpMethod.GET.name();
        }
    }
}
