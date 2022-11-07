package com.udemy.curso_JUnit5_Mockito.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.udemy.curso_JUnit5_Mockito.domain.User;
import com.udemy.curso_JUnit5_Mockito.domain.dto.UserDTO;
import com.udemy.curso_JUnit5_Mockito.repositories.UserRepository;
import com.udemy.curso_JUnit5_Mockito.services.exceptions.DataIntegrityViolationException;
import com.udemy.curso_JUnit5_Mockito.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class UserServiceImplTest {

	private static final String PASSWORD = "1234";
	private static final String EMAIL = "rafael@mail.com";
	private static final String NAME = "Rafael";
	private static final Integer ID = 1;

	@InjectMocks
	private UserServiceImpl service;

	@Mock
	private UserRepository repository;

	@Mock
	private ModelMapper mapper;

	private User user;
	private UserDTO userDTO;
	private Optional<User> optionalUser;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void findByIdTest() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);

		User response = service.findById(ID);

		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getNome());
		assertEquals(EMAIL, response.getEmail());

	}

	@Test
	void findByIdReturnObjectNotFoundExceptionTest() {
		when(repository.findById(anyInt()))
				.thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

		try {
			service.findById(ID);
		} catch (Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals("Objeto não encontrado", ex.getMessage());
		}
	}

	@Test
	void findAllTest() {
		when(repository.findAll()).thenReturn(List.of(user));

		List<User> response = service.findAll();

		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(User.class, response.get(0).getClass());

		assertEquals(ID, response.get(0).getId());
		assertEquals(NAME, response.get(0).getNome());
		assertEquals(EMAIL, response.get(0).getEmail());
		assertEquals(PASSWORD, response.get(0).getSenha());
	}

	@Test
	void createTest() {
		Mockito.when(repository.save(any())).thenReturn(user);

		User response = service.create(userDTO);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(User.class, response.getClass());
		Assertions.assertEquals(ID, response.getId());
		Assertions.assertEquals(NAME, response.getNome());
		Assertions.assertEquals(EMAIL, response.getEmail());
		Assertions.assertEquals(PASSWORD, response.getSenha());
	}

	@Test
	void createReturnDataIntegrityViolationExceptionTest() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);

		try {
			optionalUser.get().setId(2);
			service.create(userDTO);
		} catch (Exception ex) {
			assertEquals(DataIntegrityViolationException.class, ex.getClass());
			assertEquals("E-mail já cadastrado", ex.getMessage());
		}
	}

	@Test
	void updateTest() {
		when(repository.save(any())).thenReturn(user);

		User response = service.update(userDTO);

		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getNome());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PASSWORD, response.getSenha());
	}
	
	@Test
	void updateReturnDataIntegrityViolationExceptionTest() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);

		try {
			optionalUser.get().setId(2);
			service.create(userDTO);
		} catch (Exception ex) {
			assertEquals(DataIntegrityViolationException.class, ex.getClass());
			assertEquals("E-mail já cadastrado", ex.getMessage());
		}
	}

	@Test
	void deleteTest() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);
		doNothing().when(repository).deleteById(anyInt());
		service.delete(ID);
		verify(repository, times(1)).deleteById(anyInt());
	}
	
	@Test
	void deleteWithObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
		try {
			service.delete(ID);
		}
		catch (Exception ex) {
			assertEquals(ObjectNotFoundException.class, ex.getClass());
			assertEquals("Objeto não encontrado", ex.getMessage());
		}
	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}
}
