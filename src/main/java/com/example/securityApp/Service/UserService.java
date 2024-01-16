package com.example.securityApp.Service;

import com.example.securityApp.Dto.UserRegistrationRequest;
import com.example.securityApp.Dto.UserUpdateRequest;
import com.example.securityApp.Repository.UserRepository;
import com.example.securityApp.model.AuthenticationRequest;
import com.example.securityApp.model.AuthenticationResponse;
import com.example.securityApp.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public User saveUser(UserRegistrationRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());

        return userRepository.save(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User updateUser(int id, UserUpdateRequest updateRequest){
        User toUpdate = new User();
        toUpdate.setUsername(updateRequest.getUsername());
        toUpdate.setPassword(updateRequest.getPassword());
        toUpdate.setRole(updateRequest.getRole());
        return userRepository.save(toUpdate);
    }

    public AuthenticationResponse userLogin (@RequestBody AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));
        User user = userRepository.findByUsername(authenticationRequest.getUsername());
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    public User getUserById(int id){
        return userRepository.findById(id).get();
    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }
}
