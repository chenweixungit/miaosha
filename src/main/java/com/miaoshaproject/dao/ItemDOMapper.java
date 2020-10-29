package com.miaoshaproject.dao;

import com.miaoshaproject.dataObjects.ItemDO;
import org.springframework.stereotype.Component;

@Component
public interface ItemDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    ItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);
}