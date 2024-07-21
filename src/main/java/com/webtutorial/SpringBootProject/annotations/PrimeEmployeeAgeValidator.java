package com.webtutorial.SpringBootProject.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeEmployeeAgeValidator implements ConstraintValidator<PrimeEmployeeAgeValidation,Integer> {

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext constraintValidatorContext) {

        for(int i=2; i<=age/2; i++){
            if(age%i==0) {
                return false;
            }
        }

        return  true;
    }
}
