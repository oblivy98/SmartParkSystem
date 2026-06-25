package com.exam.smartpark.security.service;

import com.exam.smartpark.user.entity.SmartParkUser;
import com.exam.smartpark.user.repository.SmartParkUserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SmartParkUserRepository smartParkUserRepository;

    public CustomUserDetailsService(SmartParkUserRepository smartParkUserRepository) {
        this.smartParkUserRepository = smartParkUserRepository;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        SmartParkUser user = smartParkUserRepository.findByUsername(username)
                                                    .orElseThrow(() -> new RuntimeException("Username not found"));

        return User .builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
    }
}
