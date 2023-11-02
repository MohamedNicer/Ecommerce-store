package com.ecs.ecommercestore.Api.Security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Repository.LocalUserRepository;
import com.ecs.ecommercestore.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Filter for decoding a JWT in the Authorization header and loading the user
 * object into the authentication context.
 * @author mohamednicer
 */
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    /** The JWT Service. */
    private JWTService jwtService;

    /** The Local User Repo. */
    private LocalUserRepository localUserRepository;

    /**
     * Constructor for spring injection.
     * @param jwtService
     * @param localUserRepository
     */
    public JWTRequestFilter(JWTService jwtService, LocalUserRepository localUserRepository) {
        this.jwtService = jwtService;
        this.localUserRepository = localUserRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            try {
                String username = jwtService.getUsername(token);
                Optional<LocalUser> localUser = localUserRepository.findUserByUsernameIgnoreCase(username);
                if(localUser.isPresent()){
                    LocalUser user = localUser.get();
                    if (user.isEmailVerified()) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (JWTDecodeException e){
            }
        }
        filterChain.doFilter(request,response);
    }
}
