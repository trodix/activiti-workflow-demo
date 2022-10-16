package com.trodix.activitidemo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessDefinitionResponse {

    private String id;
    private String description;
    private String formKey;
    private String key;
    private String name;
    private int version;

}
