package com.example.vaadinui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    int id;
    UUID uuid;
    String name;
    LocalDateTime created;
    LocalDateTime modified;
}
