package com.miaoshaproject.service;

import com.miaoshaproject.error.BussinessException;
import com.miaoshaproject.service.model.ItemModel;

import java.util.List;

public interface ItemService {
    // 创建商品
    ItemModel createItem(ItemModel itemModel) throws BussinessException;

    // 浏览商品列表
    List<ItemModel> listItem();

    // 商品详情浏览
    ItemModel getItemById(Integer id) throws BussinessException;
}
