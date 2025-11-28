package vti.dtn.department_service.dto;

import java.util.Date;

public class DepartmentDTO {
    private String name;
    private String type;
    private Date createdDate;

    public DepartmentDTO() {}

    public DepartmentDTO(String name, String type, Date createdDate) {
        this.name = name;
        this.type = type;
        this.createdDate = createdDate;
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
