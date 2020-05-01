package com.example.popra;

import com.example.popra.controller.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WebTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController mainController;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .string(containsString("Some shop")));
    }

    @Test
    public void needToLoginTest() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/signIn"));
        this.mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status()
                        .is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/signIn"));
    }

    //проверям что при корректоном логине и пароле идет редирект
    @Test
    public void completeLoginTest() throws Exception {
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/signIn").user("admin").password("admin"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    //проверили что при вводе некорректного имени пользователя и пароля бросает ошибку
    @Test
    public void tryLoginWithBadUsernameTest() throws Exception {
        this.mockMvc.perform(post("/signIn").param("username","admin").param("password","asf"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signIn?error"));

    }

    //проверили что при вводе регистрации нового пользователя у которого логин совпадает с тем что есть в базе - идет сообщение об ошибке
    @Test
    public void tryRegistrationWithBadUsernameTest() throws Exception {
        this.mockMvc.perform(post("/signUp").param("username","admin").param("password","asf"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    //проверили что при регистрации нового пользователя у которого логин, который точно номарльный - будет ответ Created
    @Test
    public void tryRegistrationWithGoodUsernameTest() throws Exception {
        this.mockMvc.perform(post("/signUp").param("username","newUser").param("password","newUser"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void mainTest() {
        assertThat(this.mainController.mainPage());
    }
}
