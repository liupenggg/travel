package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * Created by fei on 2019/11/20.
 */
public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean regist(User user);

    boolean active(String code);

    User login(User user);
}
