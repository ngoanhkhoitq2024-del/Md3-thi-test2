package re.edu.md3thitest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import re.edu.md3thitest.dto.EmployeeCreateDTO;
import re.edu.md3thitest.dto.EmployeeResponseDTO;
import re.edu.md3thitest.dto.EmployeeUpdateDTO;
import re.edu.md3thitest.dto.PageResponseDto;
import re.edu.md3thitest.entity.TypeDepartment;
import re.edu.md3thitest.service.EmployeeService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDTO response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok((response));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @Valid @RequestBody EmployeeCreateDTO dto) {
        EmployeeResponseDTO response = employeeService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
        @PathVariable Long id, @Valid @RequestBody EmployeeUpdateDTO dto) {
        EmployeeResponseDTO response = employeeService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.detele(id);
        return ResponseEntity.noContent().build();
    }

    // 5. Tìm + lọc + phân trang + sắp xếp
    @GetMapping
    public ResponseEntity<PageResponseDto   <EmployeeResponseDTO>> searchEmployees(
        @RequestParam(required = false)
        String name,
        @RequestParam(required = false)
        TypeDepartment department,
        @RequestParam(required = false)
        BigDecimal minSalary,
        @RequestParam(required = false)
        BigDecimal maxSalary,
        @RequestParam(defaultValue = "0")
        int page,
        @RequestParam(defaultValue = "10")
        int size,
        @RequestParam(defaultValue = "id")
        String sortBy,
        @RequestParam(defaultValue = "asc")
        String sortDir
    ) {
        PageResponseDto<EmployeeResponseDTO> response = employeeService
                .search(name, department, minSalary, maxSalary,
                page, size, sortBy, sortDir);
        return ResponseEntity.ok(response);
    }
}
