package re.edu.md3thitest.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import re.edu.md3thitest.entity.StatusEmployee;
import re.edu.md3thitest.entity.TypeDepartment;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class EmployeeUpdateDTO {
    @NotBlank
    @Size(min = 3, max = 50, message = "Họ và tên nhân viên (3 - 50 ký tự)")
    private String fullName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại 10 chữ số (bắt đầu bằng số 0)")
    private String phone;

    @NotNull
    private TypeDepartment department;

    private String position;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Mức lương cơ bản (VNĐ) phải > 0")
    private BigDecimal salary;

    @NotNull
    @PastOrPresent(message = "Ngày vào làm không được ở tương lai")
    private LocalDate hireDate;
    @NotNull
    private StatusEmployee status;
}
