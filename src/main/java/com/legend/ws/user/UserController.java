package com.legend.ws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
/*import org.springframework.web.bind.annotation.CrossOrigin;*/
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.legend.ws.error.ApiError;
import com.legend.ws.shared.GenericMessage;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

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
    ResponseEntity<?> createUser(@Valid @RequestBody User user) {
       
        Map<String, String> validationErrors = new HashMap<>();
        if (user.getUsername() == null || user.getUsername().isEmpty()) {

            validationErrors.put("username", "username can not be empty");

        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            validationErrors.put("email", "email can not be empty");
        }

        if (validationErrors.size() > 0) {
            apiError.setValidationErrors(validationErrors);
            return ResponseEntity.badRequest().body(apiError);
        }

        userService.save(user);
        return ResponseEntity.ok(new GenericMessage("User is created"));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiError handleMethodArgNotValidEx(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation Error");
        apiError.setStatus(400);
        Map<String, String> validationErrors = new HashMap<>();
        for(var fieldError : exception.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());

        }
        apiError.setValidationErrors(validationErrors);
        return apiError
    }
}
