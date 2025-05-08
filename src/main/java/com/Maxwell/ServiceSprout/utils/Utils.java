package com.Maxwell.ServiceSprout.utils;

import java.security.SecureRandom;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.Maxwell.ServiceSprout.dto.UserDTO;
import com.Maxwell.ServiceSprout.dto.WorkOrderDTO;
import com.Maxwell.ServiceSprout.dto.WorkPerformedDTO;
import com.Maxwell.ServiceSprout.entity.User;
import com.Maxwell.ServiceSprout.entity.WorkOrder;
import com.Maxwell.ServiceSprout.entity.WorkPerformed;

public class Utils {
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SecureRandom = new SecureRandom();

    public static String generateRandomAlphanumericString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = SecureRandom.nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(index));
        }
        return sb.toString();
    }

    public static UserDTO mapUserEntityToDTO(User user, boolean includeWorkOrderIds) {
        if (user == null)
            return null;

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setRole(user.getRole());

        if (includeWorkOrderIds && user.getAssignedWorkOrders() != null) {
            Set<Long> workOrderIds = user.getAssignedWorkOrders().stream()
                    .map(WorkOrder::getId)
                    .collect(Collectors.toSet());
            dto.setAssignedWorkOrderIds(workOrderIds);
        }
        return dto;
    }
    public static UserDTO mapUserEntityToDTO(User user) {
        return mapUserEntityToDTO(user, false);
    }

    public static WorkOrderDTO mapWorkOrderEntityToDTO(WorkOrder workOrder) {
        if (workOrder == null)
            return null;

        WorkOrderDTO dto = new WorkOrderDTO();
        dto.setId(workOrder.getId());
        dto.setTitle(workOrder.getTitle());
        dto.setDescription(workOrder.getDescription());
        dto.setStatus(workOrder.getStatus());
        dto.setPriority(workOrder.getPriority());
        dto.setCreatedAt(workOrder.getCreatedAt());
        dto.setDueDate(workOrder.getDueDate());

        Set<Long> userIds = workOrder.getAssignedUsers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        dto.setAssignedUserIds(userIds);

        List<WorkPerformedDTO> logDTOs = workOrder.getLogs().stream()
                .map(Utils::mapWorkPerformedEntityToDTO)
                .collect(Collectors.toList());
        dto.setLogs(logDTOs);

        return dto;
    }

    public static WorkPerformedDTO mapWorkPerformedEntityToDTO(WorkPerformed workPerformed) {
        if (workPerformed == null)
            return null;

        WorkPerformedDTO dto = new WorkPerformedDTO();
        dto.setId(workPerformed.getId());
        dto.setNote(workPerformed.getNote());
        dto.setTimestamp(workPerformed.getTimestamp());

        dto.setTechnicianId(workPerformed.getTechnician() != null ? workPerformed.getTechnician().getId() : null);
        dto.setWorkOrderId(workPerformed.getWorkOrder() != null ? workPerformed.getWorkOrder().getId() : null);

        return dto;
    }
    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList) {
        return userList.stream().map(Utils::mapUserEntityToDTO).collect(Collectors.toList());
    }



}
