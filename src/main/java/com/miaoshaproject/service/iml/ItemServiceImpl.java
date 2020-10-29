package com.miaoshaproject.service.iml;

import com.miaoshaproject.dao.ItemDOMapper;
import com.miaoshaproject.dao.ItemStockDOMapper;
import com.miaoshaproject.dataObjects.ItemDO;
import com.miaoshaproject.dataObjects.ItemStockDO;
import com.miaoshaproject.error.BussinessException;
import com.miaoshaproject.error.EnumBusinessError;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private ValidatorImpl validator ;
    /**
     * 创建商品
     * @param itemModel
     * @return
     */
    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BussinessException {
        // 验证数据合法性
        if(itemModel == null){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasError()){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        // 数据插入数据库
        ItemDO itemDO = convertFromModel(itemModel);
        itemDOMapper.insertSelective(itemDO);
        ItemStockDO itemStockDO = convertStockFromModel(itemModel);
        itemStockDO.setItemId(itemDO.getId());
        itemStockDOMapper.insertSelective(itemStockDO);
        return this.getItemById(itemDO.getId());
    }
    private ItemDO convertFromModel(ItemModel itemModel){
        if(itemModel == null)
            return null;
        ItemDO itemDo = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDo);
        return itemDo;
    }

    private ItemStockDO convertStockFromModel(ItemModel itemModel){
        if(itemModel == null)
             return null;
        ItemStockDO itemStockDO = new ItemStockDO();
        BeanUtils.copyProperties(itemModel,itemStockDO);
        return itemStockDO;
    }

    private ItemModel convertFromDO(ItemDO itemDO,ItemStockDO itemStockDO){
        if(itemDO == null || itemStockDO == null)
            return null;
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        BeanUtils.copyProperties(itemStockDO,itemModel);
        return itemModel;
    }
    @Override
    @Transactional
    public List<ItemModel> listItem() {


        return null;
    }

    /**
     * 根据id查询商品
     * @param id
     * @return
     * @throws BussinessException
     */
    @Override
    @Transactional
    public ItemModel getItemById(Integer id) throws BussinessException{
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(id);
        ItemModel itemModel = convertFromDO(itemDO,itemStockDO);
        if(itemModel == null){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        return itemModel;
    }
}
