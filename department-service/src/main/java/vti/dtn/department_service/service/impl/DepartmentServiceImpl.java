package vti.dtn.department_service.service.impl;

import org.springframework.stereotype.Service;
import vti.dtn.department_service.dto.DepartmentDTO;
import vti.dtn.department_service.entity.DepartmentEntity;
import vti.dtn.department_service.repository.DepartmentRepository;
import vti.dtn.department_service.service.DepartmentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> entities = departmentRepository.findAll();

        return entities.stream()
                .map(dept -> new DepartmentDTO(
                        dept.getName(),
                        dept.getType().toString(),
                        dept.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
