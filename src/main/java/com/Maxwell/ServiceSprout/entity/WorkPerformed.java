package com.Maxwell.ServiceSprout.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
public class WorkPerformed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String note;
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    @JsonIgnore
    private User technician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workorder_id")
    @JsonIgnore
    private WorkOrder workOrder;

    @Override
    public String toString() {
        return "WorkPerformed{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", timestamp=" + timestamp +
                ", technician=" + technician +
                ", workOrder=" + workOrder +
                '}';
    }
    
}
