package com.thienloc.springboot.lab;

import com.thienloc.springboot.lab.entity.User;
import com.thienloc.springboot.lab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setPassword("123456");
        testUser.setFullname("Test User");
        testUser.setEmail("test@gmail.com");
        testUser.setAdmin(false);
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(testUser);
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fullname").value("Test User"));
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullname").value("Test User"));
    }

    @Test
    void testGetUserByIdNotFound() throws Exception {
        when(userService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.create(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"123456\",\"fullname\":\"Test User\",\"email\":\"test@gmail.com\",\"admin\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullname").value("Test User"));
    }

    @Test
    void testUpdateUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setPassword("newpass");
        updatedUser.setFullname("Updated User");
        updatedUser.setEmail("updated@gmail.com");
        updatedUser.setAdmin(true);

        when(userService.findById(1L)).thenReturn(Optional.of(testUser));
        when(userService.update(any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\":\"newpass\",\"fullname\":\"Updated User\",\"email\":\"updated@gmail.com\",\"admin\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullname").value("Updated User"));
    }

    @Test
    void testDeleteUser() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(testUser));

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }
}
