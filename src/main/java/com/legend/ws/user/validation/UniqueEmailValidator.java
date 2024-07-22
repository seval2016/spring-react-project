package com.legend.ws.user.validation;

import com.legend.ws.user.User;
import com.legend.ws.user.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String>{

    @Autowired
    UserRepository userRepository; //kullanıcıdan gelen email'i db'deki ile karşılaştırabilmek için UserRepository ile bağlantı kurulacak

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

    User inDB=userRepository.findByEmail(value);

        return inDB==null;
    }
    
}
