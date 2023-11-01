package com.ecs.ecommercestore.Api.Controller.Security;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Repository.LocalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to provide spring with Authentication Principals for unit testing.
 * @author mohamednicer
 */
@Service
@Primary
public class JUnitUserDetailsService implements UserDetailsService {

    /** The Local User Repository. */
    @Autowired
    private LocalUserRepository localUserRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LocalUser> user = localUserRepository.findUserByUsernameIgnoreCase(username);
        if (user.isPresent()){
            return user.get();
        }
        return null;
    }
}
