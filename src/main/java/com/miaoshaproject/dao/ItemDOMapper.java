package com.miaoshaproject.dao;

import com.miaoshaproject.dataObjects.ItemDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ItemDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    ItemDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);

    List<ItemDO> listItem();

    int increaseSales(@Param("id")Integer id,@Param("amount")Integer amount);
}