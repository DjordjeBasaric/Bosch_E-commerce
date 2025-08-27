package com.e_commerce.bosch.repositories;

import com.e_commerce.bosch.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
