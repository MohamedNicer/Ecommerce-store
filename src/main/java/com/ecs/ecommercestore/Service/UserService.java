package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Api.Model.RegistrationBody;
import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Exception.UserAlreadyExistsException;
import com.ecs.ecommercestore.Repository.LocalUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    LocalUserRepository localUserRepository;
    public UserService(LocalUserRepository localUserRepository){
        this.localUserRepository = localUserRepository;
    }
    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserRepository.findLocalUserByUsernameIgnoreCase(registrationBody.getUsername()).isPresent() ||
        localUserRepository.findLocalUserByEmailIgnoreCase(registrationBody.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setPassword(registrationBody.getPassword()); //Should be encrypted
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUsername());
        return localUserRepository.save(user);
    }
}
