package com.legend.ws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import java.util.stream.Collectors;

import com.legend.ws.error.ApiError;
import com.legend.ws.shared.GenericMessage;

import jakarta.validation.Valid;

@RestController
public class UserController {

    /*
     * @CrossOrigin -> farklı portlarda çalışıldığı için bu annotation ile
     * requestleri desteklediğini belirtiyoruz
     * ama bu geçici çözüm. vite.config.js de api düzenleme yapıldıktan sonra bu
     * annotation'a gerek kalmaıyor
     */

    @Autowired
    UserService userService;

    @PostMapping("/api/v1/users")
    GenericMessage createUser(@Valid @RequestBody User user) {

        userService.save(user);
        return new GenericMessage("User is created");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation Error");
        apiError.setStatus(400);

        var validationErrors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors
                        .toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacing) -> existing));
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    ResponseEntity<ApiError> handleNotUniqueEmailEx(NotUniqueEmailException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation Error");
        apiError.setStatus(400);
        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put("email", "Email in use");
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

}
