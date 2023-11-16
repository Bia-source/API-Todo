package br.com.beatriz.todo.repositories;

import br.com.beatriz.todo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface UserModelRepository  extends  JpaRepository<UserModel, UUID>{

    UserModel findByUsername(String username);
}