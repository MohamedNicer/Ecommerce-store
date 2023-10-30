package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Api.Model.LoginBody;
import com.ecs.ecommercestore.Api.Model.RegistrationBody;
import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.VerificationToken;
import com.ecs.ecommercestore.Exception.EmailFailureException;
import com.ecs.ecommercestore.Exception.UserAlreadyExistsException;
import com.ecs.ecommercestore.Repository.LocalUserRepository;
import com.ecs.ecommercestore.Repository.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {
    private LocalUserRepository localUserRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private EncryptionService encryptionService;
    private JWTService jwtService;
    private EmailService emailService;
    public UserService(LocalUserRepository localUserRepository, VerificationTokenRepository verificationTokenRepository, EncryptionService encryptionService, JWTService jwtService, EmailService emailService){
        this.localUserRepository = localUserRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }
    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {
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
        VerificationToken verificationToken = creteVerificationToken(user);
        emailService.sendVerificationToken(verificationToken);
        return localUserRepository.save(user);
    }
    public VerificationToken creteVerificationToken(LocalUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJWT(user));
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
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
