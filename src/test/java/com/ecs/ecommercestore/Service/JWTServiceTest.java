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
    public void testJWTTokenNotGeneratedByUs(){
        String token = JWT.create().withClaim("USERNAME","User1").sign(Algorithm.HMAC256("FakeSecretKey"));
        Assertions.assertThrows(SignatureVerificationException.class, () -> jwtService.getUsername(token));
    }
    @Test
    public void testJWTCorrectlySignedNoIssuer(){
        String token = JWT.create().withClaim("USERNAME","User1").sign(Algorithm.HMAC256(algorithmKey));
        Assertions.assertThrows(MissingClaimException.class, () -> jwtService.getUsername(token));
    }
}
