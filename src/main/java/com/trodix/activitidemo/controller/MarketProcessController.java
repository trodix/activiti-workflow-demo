package com.trodix.activitidemo.controller;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.trodix.activitidemo.dto.request.StartProcessRequest;
import com.trodix.activitidemo.dto.response.ProcessDefinitionResponse;
import com.trodix.activitidemo.dto.response.ProcessInstanceResponse;

@RequestMapping("/api/process")
@RestController
public class MarketProcessController {

    private final ProcessRuntime processRuntime;

    public MarketProcessController(final ProcessRuntime processRuntime) {
        this.processRuntime = processRuntime;
    }


    @GetMapping("/definition")
    @RolesAllowed({"ROLE_ACTIVITI_USER", "ROLE_ACTIVITI_ADMIN"})
    public List<ProcessDefinitionResponse> listProcessDefinitions() {
        final Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));

        final List<ProcessDefinitionResponse> pdDtos = processDefinitionPage.getContent().stream()
                .map(pd -> new ProcessDefinitionResponse(pd.getId(), pd.getDescription(),
                        pd.getFormKey(), pd.getKey(),
                        pd.getName(), pd.getVersion()))
                .collect(Collectors.toList());

        return pdDtos;
    }

    @GetMapping("/instances")
    @RolesAllowed({"ROLE_ACTIVITI_USER", "ROLE_ACTIVITI_ADMIN"})
    public List<ProcessInstanceResponse> listProcessInstances() {
        final List<ProcessInstance> processInstances = processRuntime.processInstances(Pageable.of(0, 10)).getContent();

        final List<ProcessInstanceResponse> instanceDtos = processInstances.stream()
                .map(instance -> new ProcessInstanceResponse(instance.getId(), instance.getName(),
                        instance.getStartDate(), instance.getInitiator(), instance.getBusinessKey(), instance.getStatus().toString(),
                        instance.getProcessDefinitionId(), instance.getProcessDefinitionKey(),
                        processRuntime.processInstanceMeta(instance.getId()).getActiveActivitiesIds()))
                .collect(Collectors.toList());

        return instanceDtos;
    }

    /**
     * @See https://amydegregorio.com/2019/03/21/spring-boot-with-activiti/
     * @param startProcessRequest payload to start the process
     * @return The process instance
     */
    @PostMapping("/start")
    @RolesAllowed({"ROLE_ACTIVITI_USER", "ROLE_ACTIVITI_ADMIN"})
    public ProcessInstanceResponse startProcess(@RequestBody final StartProcessRequest startProcessRequest) {

        final StartProcessPayload payload = ProcessPayloadBuilder
                .start()
                .withProcessDefinitionId(startProcessRequest.getProcessDefinitionId())
                .withProcessDefinitionKey(startProcessRequest.getProcessDefinitionKey())
                .withName(String.format("Process (%s - %s)", startProcessRequest.getName(),
                        Instant.now().toString()))
                .withVariables(startProcessRequest.getVariables())
                .build();

        final ProcessInstance instance = processRuntime.start(payload);

        final ProcessInstanceResponse response =
                new ProcessInstanceResponse(instance.getId(), instance.getName(), instance.getStartDate(),
                        instance.getInitiator(), instance.getBusinessKey(),
                        instance.getStatus().toString(),
                        instance.getProcessDefinitionId(), instance.getProcessDefinitionKey(),
                        null);

        return response;
    }

}
