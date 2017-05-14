package com.zavalin.account.userdetails;

import com.zavalin.account.feignclients.AuthClient;
import com.zavalin.account.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthClient authClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authClient.loadUser(username);
        if (user == null) throw new UsernameNotFoundException("No such user: " + username);
        return user;
    }


}
