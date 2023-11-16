package br.com.beatriz.todo.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.beatriz.todo.models.UserModel;
import br.com.beatriz.todo.repositories.UserModelRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserModelRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity getUser(){
        return ResponseEntity.status(HttpStatus.OK).body(this.userRepository.findAll());
    }

    @GetMapping("/username")
    public ResponseEntity getUserByUsername(@RequestBody UserModel user){
        return ResponseEntity.status(HttpStatus.OK).body(this.userRepository.findByUsername(user.getUsername()));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var alreadyExist = this.userRepository.findByUsername(userModel.getUsername());
        if(alreadyExist != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existe");
        }
        var passwordHash = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHash);
        var newUser = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/id")
    public ResponseEntity deleteUserById(HttpServletRequest request){
        this.userRepository.deleteById((UUID) request.getAttribute("idUser"));
        return ResponseEntity.status(HttpStatus.OK).body("delete");
    }
}