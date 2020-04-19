package com.neu.kanbanproject.ppmtool.services;


import com.neu.kanbanproject.ppmtool.domain.User;
import com.neu.kanbanproject.ppmtool.exceptions.UserNameAlreadyExistsException;
import com.neu.kanbanproject.ppmtool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

  public User saveUser(User newUser)
  {
     try{
    newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
    newUser.setUsername(newUser.getUsername());
    return userRepository.save(newUser);
     }catch(Exception e){
    throw new UserNameAlreadyExistsException("Username" + newUser.getUsername()+"already exists!!" );
     }
  }


}
