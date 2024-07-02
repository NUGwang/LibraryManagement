package com.example;

import com.example.entiy.Bookrecord;
import com.example.entiy.User;
import com.example.mapper.BookRecordMapper;
import com.example.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LibraryManagementApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    BookRecordMapper bookRecordMapper;
    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        List<Bookrecord> bookrecords = bookRecordMapper.selectList(null);
//        for(User user:userList) {
////            System.out.println(user);
////        }
        for(Bookrecord bookRecord: bookrecords) {
            System.out.println(bookRecord);
        }
    }


}
