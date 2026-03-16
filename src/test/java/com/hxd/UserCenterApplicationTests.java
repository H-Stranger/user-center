package com.hxd;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserCenterApplicationTests {
    @Test
    void testDigest() throws NoSuchAlgorithmException {
        String s = DigestUtils.md5DigestAsHex(("abcd" + "fdalkfda").getBytes(StandardCharsets.UTF_8));
        System.out.println(s);
    }

}
