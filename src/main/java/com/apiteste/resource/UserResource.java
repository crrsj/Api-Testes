package com.apiteste.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.apiteste.resource.dto.UserDto;
import com.apiteste.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ModelMapper mapper;
	
    @GetMapping(value = "/{id}")
	public ResponseEntity<UserDto>findByid(@PathVariable Integer id){
		return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDto.class));
	}
    
    @GetMapping
    public ResponseEntity<List<UserDto>>findAll(){     
    return ResponseEntity.ok().body(service.findAll().stream().map(x -> mapper.map(x, UserDto.class)) .collect(Collectors.toList()));
    }
    
    @PostMapping
    public ResponseEntity<UserDto>create(@RequestBody UserDto obj){     
     URI  uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(service.create(obj).getId()).toUri();
     return ResponseEntity.created(uri).build();
    }
    @PutMapping(value = "{id}")
    public ResponseEntity<UserDto>update(@PathVariable Integer id,@RequestBody UserDto obj){
    	obj.setId(id);    	 
    	return ResponseEntity.ok().body(mapper.map(service.updata(obj), UserDto.class));
    	
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable Integer id){
    	service.delete(id);
    	return ResponseEntity.noContent().build();
    }
    
}
