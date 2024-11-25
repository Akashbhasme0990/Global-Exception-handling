package com.CRUD.Operations.Repository;

import com.CRUD.Operations.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByUserName(String userName);
    List<User> findByEmail(String email);
    List<User> findByPhone(Long phone);
    List<User> findByAge(int age);
}
