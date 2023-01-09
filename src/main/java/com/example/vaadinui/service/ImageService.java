package com.example.vaadinui.service;

import com.example.vaadinui.dto.ImageDto;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;

import java.util.List;

public interface ImageService {
    ImageDto createFile(MultiFileMemoryBuffer buffer, String fileName);

    List<ImageDto> getImages();

    ImageDto getImageName(String name);

    ImageDto getImage(int id);
}
