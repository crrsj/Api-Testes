package com.apiteste.service;

import java.util.List;

import com.apiteste.domain.User;
import com.apiteste.resource.dto.UserDto;

public interface UserService {
User findById(Integer id);
List<User> findAll();
User create (UserDto obj);
User updata(UserDto obj);
void delete(Integer id);
}
