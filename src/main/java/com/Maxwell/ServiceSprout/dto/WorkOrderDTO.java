package com.Maxwell.ServiceSprout.dto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkOrderDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;


    private Set<Long> assignedUserIds = new HashSet<>(); 
    private List<WorkPerformedDTO> logs = new ArrayList<>(); 
}