package com.mmall.Seckill.service;

import com.mmall.Seckill.dao.UserDao;
import com.mmall.Seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User u1 = new User();
        u1.setId(2);
        u1.setName("Kobe");

        User u2 = new User();
        u2.setId(1);
        u2.setName("testTx");

        userDao.insert(u1);
        userDao.insert(u2);
        return true;
    }
}
