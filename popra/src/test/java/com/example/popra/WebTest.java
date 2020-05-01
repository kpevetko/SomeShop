package com.example.popra;

import com.example.popra.controller.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        this.mockMvc.perform(post("/signIn").param("username", "admin").param("password", "asf"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/signIn?error"));

    }

    //проверили что при вводе регистрации нового пользователя у которого логин совпадает с тем что есть в базе - идет сообщение об ошибке
    @Test
    public void tryRegistrationWithBadUsernameTest() throws Exception {
        this.mockMvc.perform(post("/signUp").param("username", "admin").param("password", "asf"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    //проверили что при регистрации нового пользователя у которого логин, который точно номарльный - будет ответ Created
    @Test
    public void tryRegistrationWithGoodUsernameTest() throws Exception {
        this.mockMvc.perform(post("/signUp").param("username", "newUser").param("password", "newUser"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void mainTest() {
        assertThat(this.mainController.mainPage());
    }


    @Test
    public void X() throws Exception {
        for (int i = 0; i < 100; i++) {
            this.mockMvc.perform(post("/signUp").param("username", "user" + i).param("password", "user" + i))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }
     /*   for (int i = 0; i < 10; i++) {
            this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin("/signIn").user("user" + i).password("user" + i))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
        }*/

       /* for (int i = 0; i < 2; i++) {
            ThreadMy t = new ThreadMy();
            t.mockMvc = this.mockMvc;
            t.i = i;
            t.start();
        }
        Thread.sleep(10000);*/
    }



   /* class ThreadMy extends Thread {
        public MockMvc mockMvc;
        public int i;

        @Override
        public void run() {

            while (WebTest.atomicInteger.get() > 0) {
                //нить засыпает на случайное время
                int a = (int) (Math.random() * (500 - 100)) + 100;
                try {
                    this.mockMvc.perform(put("/user/products").with(user("user" + i)).param("name", "вилка").param("numOf", "10"))
                            .andDo(print())
                            .andExpect(status().isOk());
                    WebTest.atomicInteger.getAndDecrement();
                    Thread.sleep(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }*/


    @Test
    public void UX() throws Exception {
        for (int i = 0; i < 5; i++) {
            CreateNewUserThread userThread = new CreateNewUserThread();
            userThread.mockMvc = mockMvc;
            //userThread.i = i;
            userThread.start();
        }
        Thread.sleep(10000);
    }

    public static AtomicInteger atomicInteger = new AtomicInteger(100);

    public class CreateNewUserThread extends Thread {
        public MockMvc mockMvc;
        //public int i;

        public void registration() throws Exception {
            int numU = (int) (Math.random() * (100 - 1)) + 1;
            this.mockMvc.perform(post("/signUp").param("username", "user" + numU).param("password", "user" + numU))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }


        public void buyProduct() throws Exception {
            try {
                WebTest.atomicInteger.getAndDecrement();
                int numU = (int) (Math.random() * (100 - 1)) + 1;
                int numOfProducts = (int) (Math.random() * (20 - 1)) + 1;
                this.mockMvc.perform(put("/user/products").with(user("user" + numU)).param("name", "вилка").param("numOf", String.valueOf(numOfProducts)))
                        .andDo(print())
                        .andExpect(status().isOk());
            } catch (Exception e) {
                WebTest.atomicInteger.getAndIncrement();
                throw new Exception();
            }
        }

        @Override
        public void run() {
            //for (int i = 0; i < 2; i++) {
            while (WebTest.atomicInteger.get() > 0) {
                int a = (int) (Math.random() * (500 - 100)) + 100;
                try {
                    //registration();
                    buyProduct();
                    Thread.sleep(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //}
        }
    }
}
