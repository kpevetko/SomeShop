package com.example.popra;

import com.example.popra.controller.AdminController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class someTest {
    @Autowired
    private AdminController adminController;

    @Test
    public void contexLoads() throws Exception{
        assertThat(adminController).isNotNull();
    }
}
