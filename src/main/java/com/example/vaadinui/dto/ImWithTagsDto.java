package com.example.vaadinui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImWithTagsDto {
    int id;
    UUID uuid;
    int id_im;
    int id_tg;
}
