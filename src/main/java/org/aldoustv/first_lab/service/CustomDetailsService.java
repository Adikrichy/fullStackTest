package org.aldoustv.first_lab.service;

import lombok.RequiredArgsConstructor;
import org.aldoustv.first_lab.entity.CustomDetails;
import org.aldoustv.first_lab.entity.User;
import org.aldoustv.first_lab.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword()
        );
    }
}