package org.aldoustv.first_lab.service;

import lombok.RequiredArgsConstructor;
import org.aldoustv.first_lab.dto.request.UserRequest;
import org.aldoustv.first_lab.dto.response.UserResponse;
import org.aldoustv.first_lab.entity.User;
import org.aldoustv.first_lab.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserResponse createUser(UserRequest userRequest){
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    private UserResponse toDto(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        return userResponse;
    }
}