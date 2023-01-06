package com.example.vaadinui.service.imp;

import com.example.vaadinui.common.FindIWTRequest;
import com.example.vaadinui.dto.ImWithTagsDto;
import com.example.vaadinui.dto.TagDto;
import com.example.vaadinui.service.IWTService;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IWTServiceImp implements IWTService {
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    TagServiceImp tagService;

    @Override
    public List<ImWithTagsDto> getIwtTags(int id) {
        ResponseEntity<ImWithTagsDto[]> responseEntity = restTemplate.getForEntity("http://localhost:8081/iwt/tg/" + id, ImWithTagsDto[].class);
        ImWithTagsDto[] objects = responseEntity.getBody();

        return Arrays.stream(objects).collect(Collectors.toList());
    }

    @Override
    public List<TagDto> getIwtTagsName(int id) {
        List<TagDto> tags = new ArrayList<>();
        getIwtImages(id).forEach(imWithTagsDto -> tags.add(tagService.getTag(imWithTagsDto.getId_tg())));

        return tags;
    }

    @Override
    public List<ImWithTagsDto> getIwtImages(int id) {
        ResponseEntity<ImWithTagsDto[]> responseEntity = restTemplate.getForEntity("http://localhost:8081/iwt/im/" + id, ImWithTagsDto[].class);
        ImWithTagsDto[] objects = responseEntity.getBody();

        return Arrays.stream(objects).collect(Collectors.toList());
    }

    @Override
    public void updateIWT(int imageID, List<String> list) {
        restTemplate.put("http://localhost:8081/iwt/" + imageID, list, ResponseEntity.class);
    }

    @Override
    public void deleteTag(int id_im, int id_tg) {
        restTemplate = new RestTemplate(new CustomHttpComponentsClientHttpRequestFactory());
        FindIWTRequest findIWTRequest = new FindIWTRequest(id_im, id_tg);
        HttpEntity<FindIWTRequest> request = new HttpEntity<>(findIWTRequest);

        System.out.println(findIWTRequest);

        ResponseEntity<ImWithTagsDto> responseEntity = restTemplate.exchange("http://localhost:8081/iwt/oneOb", HttpMethod.GET, request, ImWithTagsDto.class);
        ImWithTagsDto imWithTagsDto = responseEntity.getBody();
        System.out.println(imWithTagsDto);

        restTemplate.delete("http://localhost:8081/iwt/" + imWithTagsDto.getUuid());
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
