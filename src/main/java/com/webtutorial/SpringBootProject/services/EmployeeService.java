package com.webtutorial.SpringBootProject.services;

import com.webtutorial.SpringBootProject.dto.EmployeeDTO;
import com.webtutorial.SpringBootProject.entities.EmployeeEntity;
import com.webtutorial.SpringBootProject.exceptions.ResourceNotFoundException;
import com.webtutorial.SpringBootProject.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long employeeId) {

        return employeeRepository.findById(employeeId)
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {

       List<EmployeeEntity> employeeEntities = employeeRepository.findAll();


       return employeeEntities
               .stream()
               .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
               .collect(Collectors.toList());

    }

    public EmployeeDTO createEmployee(EmployeeDTO inputEmployee) {

        //convert dto to entity since this is save method
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity employeeEntity =  employeeRepository.save(toSaveEntity);
        //convert entity to dto
        return modelMapper.map(employeeEntity, EmployeeDTO.class);

    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {

       isExistsByEmployeeId(employeeId);

        //convert dto to entity
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO,EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        //convert entity to dto
        return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long employeeId) {
         employeeRepository.existsById(employeeId);
        employeeRepository.deleteById(employeeId);

        return  true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {

        isExistsByEmployeeId(employeeId);

        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        updates.forEach((field,value)->{

            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class,field);

            fieldToBeUpdated.setAccessible(true);

            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);

        });


      return  modelMapper.map( employeeRepository.save(employeeEntity),EmployeeDTO.class);

    }

    public void  isExistsByEmployeeId(Long employeeId){

        boolean isExists =  employeeRepository.existsById(employeeId);

        if(!isExists) throw new ResourceNotFoundException("Resource not found with id "+employeeId);
    }
}
