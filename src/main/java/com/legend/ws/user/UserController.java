package com.legend.ws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
/*import org.springframework.web.bind.annotation.CrossOrigin;*/
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.legend.ws.error.ApiError;
import com.legend.ws.shared.GenericMessage;

import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

   /* @CrossOrigin -> farklı portlarda çalışıldığı için bu annotation ile requestleri desteklediğini belirtiyoruz 
   ama bu geçici çözüm. vite.config.js de api düzenleme yapıldıktan sonra bu annotation'a gerek kalmaıyor
    */

    @Autowired
    UserService userService;

    @PostMapping("/api/v1/users")
    ResponseEntity<?> createUser(@RequestBody User user) {

        if(user.getUsername()==null  || user.getUsername().isEmpty() ){
              ApiError apiError=new ApiError();
              apiError.setPath("/api/v1/users");
              apiError.setMessage("Validation Error");
              apiError.setStatus(400);
              Map<String,String> validationErrors=new HashMap<>();
              validationErrors.put("username","username can not be empty");
              apiError.setValidationErrors(validationErrors);
              return ResponseEntity.badRequest().body(apiError);
        }


        userService.save(user);
        return ResponseEntity.ok(new GenericMessage("User is created"));
    }
}
