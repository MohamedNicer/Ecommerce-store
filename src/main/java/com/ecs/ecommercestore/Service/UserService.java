package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Api.Model.LoginBody;
import com.ecs.ecommercestore.Api.Model.RegistrationBody;
import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Exception.UserAlreadyExistsException;
import com.ecs.ecommercestore.Repository.LocalUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private LocalUserRepository localUserRepository;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    public UserService(LocalUserRepository localUserRepository, EncryptionService encryptionService, JWTService jwtService){
        this.localUserRepository = localUserRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }
    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if (localUserRepository.findUserByUsernameIgnoreCase(registrationBody.getUsername()).isPresent() ||
        localUserRepository.findUserByEmailIgnoreCase(registrationBody.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setUsername(registrationBody.getUsername());
        return localUserRepository.save(user);
    }
    public String LoginUser(LoginBody loginBody){
        Optional<LocalUser> optionalLocalUser = localUserRepository.findUserByUsernameIgnoreCase(loginBody.getUsername());
        if(optionalLocalUser.isPresent()){
            LocalUser localUser = optionalLocalUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(), localUser.getPassword())){
                return jwtService.generateJWT(localUser);
            }
        }
        return null;
    }
}
