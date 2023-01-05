package com.example.vaadinui.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ImageFull {
    String name;
    List<String> tags;
}
