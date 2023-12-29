package com.apiTeste;




import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.apiteste.domain.User;
import com.apiteste.resource.UserResource;
import com.apiteste.resource.dto.UserDto;
import com.apiteste.service.implement.UserServiceImpl;

@SpringBootTest
public class UserResourceTest {
	private static final int INDEX = 0;
	private static final Integer ID = 1;
	private static final String PASSWORD = "123";
	private static final String EMAIL = "crrsj@hotmail.com";
	private static final String NAME = "Carlos";
	
    private User user;
	
	private UserDto userDto;
	
	@InjectMocks
	private UserResource userResource;
	
	@Mock
	private UserServiceImpl service;
	
	@Mock
	private ModelMapper mapper;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		startUser() ;
	}
  @Test
  void whenFindByIdReturnSuccess() {
	  when(service.findById(Mockito.anyInt())).thenReturn(user);
	  when(mapper.map(any(), any())).thenReturn(userDto);
	  ResponseEntity<UserDto> response = userResource.findByid(ID);
	  assertNotNull(response);
	  assertNotNull(response.getBody());
	  assertEquals(ResponseEntity.class,response.getClass());	  
	  assertEquals(UserDto.class,response.getBody().getClass());
	  
		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(EMAIL, response.getBody().getEmail());
		assertEquals(PASSWORD, response.getBody().getPassword());
  
  }
  
  @Test
  void whenFindAllThenReturnAListOfUserDto() {
	 when(service.findAll()).thenReturn(List.of(user));
	 when(mapper.map(any(), any())).thenReturn(userDto);
	 ResponseEntity<List<UserDto>> response = userResource.findAll();
	 assertNotNull(response);
	 assertNotNull(response.getBody());
     assertEquals(HttpStatus.OK, response.getStatusCode()); 
	 assertEquals(ResponseEntity.class,response.getClass());	
	 assertEquals(UserDto.class,response.getBody().get(INDEX).getClass());	
	 assertEquals(ID, response.getBody().get(INDEX).getId());
	 assertEquals(NAME, response.getBody().get(INDEX).getName());
	 assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
	 assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
  }
  @Test
  void whenCreateThenReturnCreated() {
	  when(service.create(any())).thenReturn(user);
	  ResponseEntity<UserDto>response = userResource.create(userDto);	 
	  assertEquals(ResponseEntity.class, response.getClass());
	  assertEquals(HttpStatus.CREATED, response.getStatusCode());
	  assertNotNull(response.getHeaders().get("location"));
  }
  @Test
  void whenUpdateThenReturnSuccess() {
	 
	  when(service.updata(userDto)).thenReturn(user);
	  when(mapper.map(any(),any())).thenReturn(userDto);
	  ResponseEntity<UserDto> response = userResource.update(ID, userDto);
	  assertNotNull(response);
	  assertNotNull(response.getBody());
	  assertEquals(HttpStatus.OK, response.getStatusCode());
	  assertEquals(ID, response.getBody().getId());
	  assertEquals(NAME, response.getBody().getName());
	  assertEquals(EMAIL, response.getBody().getEmail());
  }
  @Test 
  void whenDeleteThenReturnSuccess() {
	  doNothing().when(service).delete(anyInt());
	  ResponseEntity<Void> response = userResource.delete(ID);
	  assertNotNull(response);
	  assertEquals(ResponseEntity.class, response.getClass());
	  assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	  verify(service, times(1)).delete(anyInt());
  }
  private void startUser() {
	   user = new User(ID,NAME,EMAIL,PASSWORD);
	   userDto = new UserDto(ID,NAME,EMAIL,PASSWORD);
	   
	   
  }
  
}
