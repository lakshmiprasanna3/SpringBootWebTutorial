package com.webtutorial.SpringBootProject.controllers;

import com.webtutorial.SpringBootProject.dto.EmployeeDTO;
import com.webtutorial.SpringBootProject.entities.EmployeeEntity;
import com.webtutorial.SpringBootProject.exceptions.ResourceNotFoundException;
import com.webtutorial.SpringBootProject.repositories.EmployeeRepository;
import com.webtutorial.SpringBootProject.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RequestMapping("/employees")
@RestController
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{employeeId}")
   public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId){

        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(employeeId);
      return  employeeDTO
              .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
              .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id "+employeeId));
             // .orElse(ResponseEntity.notFound().build());

    }

   @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age,@RequestParam(required = false) String sortBy){

     return ResponseEntity.ok(employeeService.getAllEmployees());
   }

   @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){

       EmployeeDTO savedEmployee =  employeeService.createEmployee(inputEmployee);
       return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);


   }


   @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO,@PathVariable Long employeeId){

        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId,employeeDTO));


   }

   @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@PathVariable Long employeeId, @RequestBody Map<String,Object> updates){

       EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId,updates);
       if(employeeDTO==null){
          return ResponseEntity.notFound().build();
       }

       return ResponseEntity.ok(employeeDTO);
   }

   @DeleteMapping("/{employeeId}")
   public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){

       boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
       if(!gotDeleted) return ResponseEntity.notFound().build();
       return ResponseEntity.ok(true);


   }
}
