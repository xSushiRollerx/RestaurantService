package com.xsushirollx.sushibyte.restaurantservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.restaurantservice.model.User;


@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

}
