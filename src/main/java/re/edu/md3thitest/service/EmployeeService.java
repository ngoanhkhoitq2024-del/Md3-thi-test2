package re.edu.md3thitest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import re.edu.md3thitest.dto.EmployeeCreateDTO;
import re.edu.md3thitest.dto.EmployeeResponseDTO;
import re.edu.md3thitest.dto.EmployeeUpdateDTO;
import re.edu.md3thitest.dto.PageResponseDto;
import re.edu.md3thitest.entity.Employee;
import re.edu.md3thitest.entity.TypeDepartment;
import re.edu.md3thitest.exception.DuplicateResourceException;
import re.edu.md3thitest.exception.ResourceNotFoundException;
import re.edu.md3thitest.repository.IEmployeeRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {
    private final IEmployeeRepository iEmployeeRepository;

    // 1. Lấy nhân viên theo id
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = iEmployeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy nhân viên với ID: " + id));
        return new EmployeeResponseDTO(employee);
    }

    // 2. Thêm mới
    public EmployeeResponseDTO create(EmployeeCreateDTO request) {
        // ghi log
        log.info("Bắt đầu thêm nhân viên: employeeCode={}", request.getEmployeeCode());

        if (iEmployeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new DuplicateResourceException("Mã nhân viên đã tồn tại");
        }
        if (iEmployeeRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email đã tồn tại");
        }
        Employee employee = new Employee();
        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());
        employee.setHireDate(request.getHireDate());
        employee.setStatus(request.getStatus());

        Employee saveEmployee = iEmployeeRepository.save(employee);
        log.info("Thêm nhân viên với id: {} thành công - EmployeeCode: {}",
                saveEmployee.getId(), saveEmployee.getEmployeeCode());
        return new EmployeeResponseDTO(saveEmployee);
    }

    // 3. Cập nhật nhân viên
    public EmployeeResponseDTO update(Long id, EmployeeUpdateDTO dto) {
        log.info("Bắt đầu cập nhật nhân viên: id={}", id);

        Employee employee = iEmployeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy nhân viên với ID: " + id));

        // Kiểm tra email trùng
        if (!employee.getEmail().equals(dto.getEmail()) &&
                iEmployeeRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email đã tồn tại: " + dto.getEmail());
        }
        employee.setFullName(dto.getFullName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setDepartment(dto.getDepartment());
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setHireDate(dto.getHireDate());

        Employee saveUpdate = iEmployeeRepository.save(employee);
        log.info("Cập nhật nhân viên với id {} thành công", saveUpdate.getId());
        return new EmployeeResponseDTO(saveUpdate);
    }

    // 4. Xóa nhân viên
    public void detele(Long id) {
        log.info("Bắt đầu xóa nhân viên có id: {}", id);
        iEmployeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Không tìm thấy nhân viên với ID: " + id)
        );
        iEmployeeRepository.deleteById(id);
        log.info("Xóa nhân viên có id: {} thành công", id);
    }

    // Sắp xếp
    public PageResponseDto<EmployeeResponseDTO> search(
            String name, TypeDepartment department, BigDecimal minSalary, BigDecimal maxSalary,
            int page, int size, String sortBy, String sortDir
    ) {
        Sort sort;
        if (sortDir.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        if (name == null) {
            name = "";
        }
        Page<Employee> employees = iEmployeeRepository.searchEmployees(
                name, department, minSalary, maxSalary, pageable);
        List<EmployeeResponseDTO> data = employees.map(EmployeeResponseDTO::new).getContent();
        return new PageResponseDto<>(
                data, employees.getNumber(), employees.getSize(),
                employees.getTotalElements(), employees.getTotalPages()
        );
    }
}
