package com.apiTeste;

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


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.apiteste.domain.User;
import com.apiteste.repository.UserRepository;
import com.apiteste.resource.dto.UserDto;
import com.apiteste.service.exception.DataIntegrityViolationException;
import com.apiteste.service.exception.ObjectNotFoundException;
import com.apiteste.service.implement.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {
	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
	private static final String EMAI_JA_CADASTRADO_NO_SISTEMA = "Emai já cadastrado no sistema";
	private static final int INDEX = 0;
	private static final Integer ID = 1;
	private static final String PASSWORD = "123";
	private static final String EMAIL = "crrsj@hotmail.com";
	private static final String NAME = "Carlos";
	
	
	@InjectMocks
	private UserServiceImpl service;
	@Mock
	private UserRepository repository;
	@Mock
	private ModelMapper modelMapper;

	private User user;
	
	private UserDto userDto;
	
	private Optional<User>optionalUser;
	
	@BeforeEach   
	void setUp() {
	MockitoAnnotations.openMocks(this);
	startUser();
	}
	
   @Test
   void whenFindByIdThenReturnAnUserInstance() {
	  Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
	  User response = service.findById(ID);
	  assertNotNull(response);
	  assertEquals(User.class, response.getClass());
	  assertEquals(ID, response.getId());
	  assertEquals(NAME, response.getName());
	  assertEquals(EMAIL, response.getEmail());
   }
   @Test
   void whenfindAllThenReturnAnListOfUsers() {
	when(repository.findAll()).thenReturn(List.of(user));   
	List<User> response = service.findAll();
	
	assertNotNull(response);
	assertEquals(1, response.size());
	assertEquals(User.class, response.get(0).getClass());
	
	assertEquals(ID, response.get(INDEX).getId());
	assertEquals(NAME, response.get(INDEX).getName());
	assertEquals(EMAIL, response.get(INDEX).getEmail());
	assertEquals(PASSWORD, response.get(INDEX).getPassword());
   }
   @Test
   void whenCreateThenReturnSuccess() {
	when(repository.save(any())).thenReturn(user);
	User response = service.create(userDto);
	assertNotNull(response);
	assertEquals(User.class,response.getClass());
	assertEquals(ID, response.getId());
	assertEquals(NAME, response.getName());
	assertEquals(EMAIL, response.getEmail());
	assertEquals(PASSWORD, response.getPassword());
   }
   
   @Test
   void whenCreateReturnAnDataIntegrityViolationException() {
	when(repository.findByEmail(anyString())).thenReturn(optionalUser);
	try {
		optionalUser.get().setId(2);  
		service.create(userDto);
	} catch (Exception e) {
		assertEquals(DataIntegrityViolationException.class, e.getClass());
		assertEquals(EMAI_JA_CADASTRADO_NO_SISTEMA, e.getMessage());
	}
	
	   }

 
   @Test
   void whenUpidateThenReturnSuccess() {
	when(repository.save(any())).thenReturn(user);
	User response = service.updata(userDto);
	assertNotNull(response);
	assertEquals(User.class,response.getClass());
	assertEquals(ID, response.getId());
	assertEquals(NAME, response.getName());
	assertEquals(EMAIL, response.getEmail());
	assertEquals(PASSWORD, response.getPassword());
   }
  
   @Test
   void whenUpdateReturnAnDataIntegrityViolationException() {
	when(repository.findByEmail(anyString())).thenReturn(optionalUser);
	try {
		optionalUser.get().setId(2);  
		service.create(userDto);
	} catch (Exception e) {
		assertEquals(DataIntegrityViolationException.class, e.getClass());
		assertEquals(EMAI_JA_CADASTRADO_NO_SISTEMA, e.getMessage());
	}
	
	   }
   
   
   
   @Test
   void deleteWithSuccess () {
	   when(repository.findById(anyInt())).thenReturn(optionalUser);
	   doNothing().when(repository).deleteById(anyInt());
	   service.delete(ID);
	 verify(repository, times(1)).deleteById(anyInt());
   }
   @Test
   void deleteWithObjectNotFoundException() {
	   when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
	   try {
	   service.delete(ID);
	} catch (Exception e) {
		assertEquals(ObjectNotFoundException.class ,e.getClass());
		assertEquals(OBJETO_NAO_ENCONTRADO, e.getMessage());
	}
   }
   
   private void startUser() {
	   user = new User(ID,NAME,EMAIL,PASSWORD);
	   userDto = new UserDto(ID,NAME,EMAIL,PASSWORD);
	   optionalUser =  Optional.of(new User (ID,NAME,EMAIL,PASSWORD));
	   
   }
}
