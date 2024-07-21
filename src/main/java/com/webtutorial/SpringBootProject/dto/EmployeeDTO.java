package com.webtutorial.SpringBootProject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.webtutorial.SpringBootProject.annotations.EmployeeRoleValidation;
import com.webtutorial.SpringBootProject.annotations.PrimeEmployeeAgeValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class EmployeeDTO {


    private Long id;

    @NotBlank(message = "Name of the employee cannot be blank")
    @Size(min = 3,max = 10,message = "Number of characters in name should in the range [3,10]")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be a valid email")
    private String email;



    @NotNull(message = "Age cannot be null")
    @Max(value = 80,message = "Age cannot be greater that 80")
    @Min(value = 18, message = "Age of employee cannot be less than 18")
    @PrimeEmployeeAgeValidation
    private Integer age;

    @PastOrPresent(message = "dateOfJoining field should be past or present not in the future")
    private LocalDate dateOfJoining;

    @Digits(integer = 6,fraction = 2,message = "salary can be in the format XXXXX.YY")
    @NotNull(message = "Salary cannot be null")
    @Positive(message = "Salary should be greater than 0")
    @DecimalMin(value = "1000.99")
    @DecimalMax(value = "1000000.99")
    private Double salary;

    @NotBlank(message = "Role of employee cannot be blank")
   // @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of employee can be ADMIN or USER")
    @EmployeeRoleValidation
    private String role;

    @AssertTrue(message = "Employee should be active")
    @JsonProperty("isActive")
    private Boolean isActive;



}
