package re.edu.md3thitest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import re.edu.md3thitest.entity.Employee;
import re.edu.md3thitest.entity.TypeDepartment;

import java.math.BigDecimal;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmployeeCode(String employeeCode);
    boolean existsByEmail(String email);

    @Query("""
        SELECT e FROM Employee e
        WHERE LOWER(e.fullName) LIKE LOWER(concat('%', :name ,'%'))
            AND (:department IS NULL OR e.department = :department)
            AND (:minSalary IS NULL OR e.salary >= :minSalary)
            AND (:maxSalary IS NULL OR e.salary <= :maxSalary)
""")
    Page<Employee> searchEmployees(
            @Param("name") String name,
            @Param("department") TypeDepartment department,
            @Param("minSalary") BigDecimal minSalary,
            @Param("maxSalary") BigDecimal maxSalary,
            Pageable pageable
            );
}
