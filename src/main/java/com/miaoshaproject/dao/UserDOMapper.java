package com.miaoshaproject.dao;

import com.miaoshaproject.dataObjects.UserDO;
import org.springframework.stereotype.Component;

@Component
public interface UserDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);


}