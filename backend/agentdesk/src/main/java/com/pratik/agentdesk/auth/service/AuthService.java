package com.pratik.agentdesk.auth.service;

import com.pratik.agentdesk.auth.dto.RegisterRequest;
import com.pratik.agentdesk.user.entity.User;
import com.pratik.agentdesk.user.repository.UserRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void register(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setRole("USER");
        userRepository.save(user);
    }
}
