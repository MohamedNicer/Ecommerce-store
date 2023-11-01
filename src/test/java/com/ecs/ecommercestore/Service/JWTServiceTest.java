package com.ecs.ecommercestore.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.MissingClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Repository.LocalUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class JWTServiceTest {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private LocalUserRepository localUserRepository;
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Test
    public void testVerificationTokenNotUsableForLogin(){
        LocalUser user = localUserRepository.findUserByUsernameIgnoreCase("User1").get();
        String token = jwtService.generateVerificationJWT(user);
        Assertions.assertNull(jwtService.getUsername(token),"Verification Token Shouldn't Contain The User's UserName");
    }
    @Test
    public void testAuthTokenReturnsUsername(){
        LocalUser user = localUserRepository.findUserByUsernameIgnoreCase("User1").get();
        String token = jwtService.generateJWT(user);
        Assertions.assertNotNull(jwtService.getUsername(token),"Verification Token Should Contain The User's Username");
    }
    @Test
    public void testLoginJWTTokenNotGeneratedByUs(){
        String token = JWT.create().withClaim("USERNAME","User1").sign(Algorithm.HMAC256("FakeSecretKey"));
        Assertions.assertThrows(SignatureVerificationException.class, () -> jwtService.getUsername(token));
    }
    @Test
    public void testLoginJWTCorrectlySignedNoIssuer(){
        String token = JWT.create().withClaim("USERNAME","User1").sign(Algorithm.HMAC256(algorithmKey));
        Assertions.assertThrows(MissingClaimException.class, () -> jwtService.getUsername(token));
    }
    @Test
    public void testPasswordResetToken(){
        LocalUser user = localUserRepository.findUserByUsernameIgnoreCase("User1").get();
        String token = jwtService.generatePasswordResetJWT(user);
        Assertions.assertEquals(user.getEmail(),jwtService.getResetPasswordEmail(token),"The Two Emails Should Match");
    }
    @Test
    public void testResetPasswordJWTTokenNotGeneratedByUs(){
        String token = JWT.create().withClaim("RESET_PASSWORD_EMAIL","user1@mail.com").sign(Algorithm.HMAC256("FakeSecretKey"));
        Assertions.assertThrows(SignatureVerificationException.class, () -> jwtService.getResetPasswordEmail(token));
    }
    @Test
    public void testResetPasswordJWTCorrectlySignedNoIssuer(){
        String token = JWT.create().withClaim("RESET_PASSWORD_EMAIL","user1@mail.com").sign(Algorithm.HMAC256(algorithmKey));
        Assertions.assertThrows(MissingClaimException.class, () -> jwtService.getResetPasswordEmail(token));
    }
}
