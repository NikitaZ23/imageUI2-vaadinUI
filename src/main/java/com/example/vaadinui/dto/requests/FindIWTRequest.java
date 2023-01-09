package com.example.vaadinui.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindIWTRequest {
    int id_im;
    int id_tg;
}
