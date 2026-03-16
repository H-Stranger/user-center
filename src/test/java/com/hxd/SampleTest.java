package com.hxd;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hxd.mapper.UserMapper;
import com.hxd.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class SampleTest {

//    @Autowired  按类型注入
    //mybatis-plus会自动引入多种mapper
    @Resource  //按类名注入
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

}
