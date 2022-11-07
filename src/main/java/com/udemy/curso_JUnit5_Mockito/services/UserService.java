package com.udemy.curso_JUnit5_Mockito.services;

import java.util.List;

import com.udemy.curso_JUnit5_Mockito.domain.User;
import com.udemy.curso_JUnit5_Mockito.domain.dto.UserDTO;

public interface UserService {
	
	User findById(Integer id);
	List<User> findAll();
	User create(UserDTO obj);
	User update(UserDTO obj);
	void delete(Integer id);
}
