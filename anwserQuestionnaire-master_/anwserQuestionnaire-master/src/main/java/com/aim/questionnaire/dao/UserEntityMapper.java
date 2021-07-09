package com.aim.questionnaire.dao;

import com.aim.questionnaire.dao.entity.UserEntity;
import com.aim.questionnaire.dao.entity.loginMess;
import com.aim.questionnaire.dao.entity.people;
import com.aim.questionnaire.dao.entity.picture;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public interface UserEntityMapper {
    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    UserEntity selectAllByName(String username);

    /**
     * 添加用户到数据库
     * @param ue
     * @return
     */
    int insert(UserEntity ue);

    /**
     * 模糊查找
     * @param ue
     * @return
     */
    List<Map<String,Object>> queryUserList(String username);

    /**
     *插入登录信息
     */
    int insertLoginMess(loginMess mess);

    int insertpic(picture Pic);

    HashMap<String,byte[]> selectpic(String username);

    List<people> queryProblemList();



}