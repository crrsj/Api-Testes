package com.apiteste.service.implement;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiteste.domain.User;
import com.apiteste.repository.UserRepository;
import com.apiteste.resource.dto.UserDto;
import com.apiteste.service.UserService;
import com.apiteste.service.exception.DataIntegrityViolationException;
import com.apiteste.service.exception.ObjectNotFoundException;
@Service
public class UserServiceImpl implements UserService {
   
	@Autowired
	private UserRepository repository;
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	@Override
	public List<User> findAll() {
		
		return repository.findAll();
	}

	@Override
	public User create(UserDto obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, User.class));
	}
	
	private void findByEmail(UserDto obj) {
		Optional<User> user = repository.findByEmail(obj.getEmail());
		if(user.isPresent() && !user.get().getId().equals(obj.getId())) {
			throw new DataIntegrityViolationException("Emai já cadastrado no sistema");
		}
	}

	@Override
	public User updata(UserDto obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, User.class));
			
		}

	@Override
	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
		
	}
	}
   

