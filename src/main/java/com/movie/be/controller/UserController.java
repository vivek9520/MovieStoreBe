package com.movie.be.controller;

import com.movie.be.model.User;
import com.movie.be.repository.UserRepository;
import com.movie.be.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user){
        Query query = new Query();

        query.addCriteria(Criteria.where("email").is(user.getEmail()));
        List<User> userObj = userRepository.findUserByEmail(user.getEmail());
            if(userObj.size()>0){
                    return  new ResponseEntity<>("Already registered",HttpStatus.NOT_ACCEPTABLE);
            }
            else {
                try {
                    userRepository.save(user);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        List<User> userObj = userRepository.findUserByEmail(user.getEmail());
        if(userObj.size()>0){

            if(!user.getPassword().equals(userObj.get(0).getPassword())){
                return new ResponseEntity<>("username or password wrong",HttpStatus.NOT_ACCEPTABLE);
            }
            else {

                String token = jwtUtils.generateJwt(user);

                Map<String,Object> data = new HashMap<>();
                data.put("accessToken",token);

                return new ResponseEntity<>(data, HttpStatus.OK);
            }
        }
        else {

            return  new ResponseEntity<>("Account not found ",HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
