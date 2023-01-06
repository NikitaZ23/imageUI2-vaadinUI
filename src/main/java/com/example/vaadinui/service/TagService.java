package com.example.vaadinui.service;

import com.example.vaadinui.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto getTag(int id);

    TagDto getTag(String name);

    List<TagDto> getTags();
}
