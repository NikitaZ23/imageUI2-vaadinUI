package com.example.vaadinui.service;

import com.example.vaadinui.dto.ImWithTagsDto;
import com.example.vaadinui.dto.TagDto;

import java.util.List;

public interface IWTService {
    List<ImWithTagsDto> getIwtTags(int id);

    List<TagDto> getIwtTagsName(int id);

    List<ImWithTagsDto> getIwtImages(int id);

    void updateIWT(int imageID, List<String> list);

    void deleteTag(int id_im, int id_tg);
}
