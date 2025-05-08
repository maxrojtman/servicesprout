package com.Maxwell.ServiceSprout.dto;

import java.util.List;
import java.util.Set;

import com.Maxwell.ServiceSprout.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response{
    private int statusCode;
    private String message;// This can hold WorkOrderDTO, WorkPerformedDTO, List<WorkOrderDTO>, etc.
    private String token;
    private String expirationTime;
    private UserDTO user;
    private String role;
    private Set<WorkOrderDTO> workOrderList;
    private List<WorkPerformedDTO> workPerformedList;
    private List<UserDTO> userList;
}


