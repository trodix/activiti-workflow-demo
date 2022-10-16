package com.trodix.activitidemo.dto.response;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessInstanceResponse {

    private String id;
    private String name;
    private Date startDate;
    private String initiator;
    private String businessKey;
    private String status;
    private String processDefinitionId;
    private String processDefinitionKey;
    private List<String> activeActivitiesIds;
}
