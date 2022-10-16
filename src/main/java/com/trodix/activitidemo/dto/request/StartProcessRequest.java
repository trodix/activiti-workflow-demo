package com.trodix.activitidemo.dto.request;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StartProcessRequest {

    private String processDefinitionId;
    private String processDefinitionKey;
    private String name;
    private Map<String, Object> variables;

}
