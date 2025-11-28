package vti.dtn.department_service.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int totalMember;

    @CreationTimestamp
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private DepartmentType type;

    public DepartmentEntity() {}

    // GETTERS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalMember() {
        return totalMember;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public DepartmentType getType() {
        return type;
    }

    public enum DepartmentType {
        DEV, TEST, SCRUM_MASTER, PM
    }
}
