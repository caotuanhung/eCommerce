package com.example.authcourse.controllers;

import com.example.authcourse.model.persistence.User;
import com.example.authcourse.model.requests.CreateUserRequest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Objects;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void createUserSuccess() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("hungct");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        Assertions.assertEquals("hungct", Objects.requireNonNull(responseEntity.getBody()).getUsername());
    }

    @Test
    public void createUserFailInvalidPassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("hungct1");
        createUserRequest.setPassword("123");
        createUserRequest.setConfirmPassword("123");
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void findUserByIdSuccess() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("hungct2");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> createdUserResponse = userController.createUser(createUserRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken("hungct2", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<User> responseEntity = userController.findById(Objects.requireNonNull(createdUserResponse.getBody()).getId());
        Assertions.assertEquals("hungct2", Objects.requireNonNull(responseEntity.getBody()).getUsername());
    }

    @Test
    public void findUserByIdFailNotFound() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("hungct2", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<User> responseEntity = userController.findById(-1L);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void findUserByIdFailForbidden() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("hungct3");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> createdUserResponse = userController.createUser(createUserRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken("dungdp", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<User> responseEntity = userController.findById(Objects.requireNonNull(createdUserResponse.getBody()).getId());
        Assertions.assertEquals(403, responseEntity.getStatusCodeValue());
    }

    @Test
    public void findByUsernameSuccess() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("hungct4");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> createdUserResponse = userController.createUser(createUserRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken("hungct4", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<User> responseEntity = userController.findByUserName("hungct4");
        Assertions.assertEquals("hungct4", Objects.requireNonNull(responseEntity.getBody()).getUsername());
    }

    @Test
    public void findByUsernameFailForbidden() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("hungct5");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> createdUserResponse = userController.createUser(createUserRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken("hungct4", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<User> responseEntity = userController.findByUserName("hungct5");
        Assertions.assertEquals(403, responseEntity.getStatusCodeValue());
    }

    @Test
    public void findByUsernameFailNotFound() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("hungct6");
        createUserRequest.setPassword("Pass.123");
        createUserRequest.setConfirmPassword("Pass.123");
        ResponseEntity<User> createdUserResponse = userController.createUser(createUserRequest);

        Authentication authentication = new UsernamePasswordAuthenticationToken("hungct4", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<User> responseEntity = userController.findByUserName("hungct4");
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
    }
}