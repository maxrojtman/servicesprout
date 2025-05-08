package com.Maxwell.ServiceSprout.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "workorders")
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private String priority;

    private LocalDateTime createdAt;
    private LocalDateTime dueDate;


    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<WorkPerformed> logs = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "workorder_assignees",
        joinColumns = @JoinColumn(name = "workorder_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
@JsonIgnore
private Set<User> assignedUsers = new HashSet<>();

    public String toString() {
        return "WorkOrder{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", createdAt=" + createdAt +
                ", dueDate=" + dueDate +
                ", assignedUsers=" + assignedUsers +
                ", logs=" + logs +
                '}';
    }
}
