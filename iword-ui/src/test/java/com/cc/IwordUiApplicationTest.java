package com.cc;

import com.cc.utils.WxLogin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author cc
 * @version 1.0
 * @date 2023/2/12 14:56
 */
@SpringBootTest
public class IwordUiApplicationTest {

    @Autowired
    WxLogin wxLogin;

    @Test
    public void test() {
        try {
            System.out.println(wxLogin.getOpenId("083R8wHa1g1BJE01vPGa1IYI0s4R8wHN"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void encode() {
        System.out.println(passwordEncoder.encode("null"));
    }
}
