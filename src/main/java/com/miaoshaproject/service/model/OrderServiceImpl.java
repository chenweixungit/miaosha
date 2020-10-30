package com.miaoshaproject.service.model;

import com.miaoshaproject.dao.ItemStockDOMapper;
import com.miaoshaproject.dao.OrderDOMapper;
import com.miaoshaproject.dao.SequenceDOMapper;
import com.miaoshaproject.dataObjects.OrderDO;
import com.miaoshaproject.dataObjects.SequenceDO;
import com.miaoshaproject.error.BussinessException;
import com.miaoshaproject.error.EnumBusinessError;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemStockDOMapper itemStockDOMapper;

    @Autowired
    OrderDOMapper orderDOMapper;

    @Autowired
    SequenceDOMapper sequenceDOMapper;

    /**
     * 创建订单
     * @param userId
     * @param itemId
     * @param amount
     * @return
     * @throws BussinessException
     */
    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BussinessException{
        // 判断校验信息
        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"用户不存在");
        }
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw  new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"商品不存在");
        }
        if(amount < 0 || amount > 99){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"商品数量信息不合法");
        }
        // 落单减库存
        boolean result = itemService.decreaseStock(itemId,amount);
        if(!result){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"库存不足");
        }
        // 判断互动是否正在进行中
        if(promoId != null){
            if(promoId.intValue() != itemModel.getPromoModel().getId()){
                throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不存在");
            }else if(itemModel.getPromoModel().getStatus().intValue() != 2){
                throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"活动还未开始");
            }
        }
        // 订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);
        orderModel.setItemId(itemId);
        if(promoId != null){
            orderModel.setOrderPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            orderModel.setOrderPrice(itemModel.getPrice());
        }
        orderModel.setOrderAmount(orderModel.getOrderPrice().multiply(new BigDecimal(amount)));
        // 生成交易流水号
        OrderDO orderDO = convertFromModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        // 加上商品销量
        itemService.increaseSales(itemId,amount);
        return orderModel;
    }

    /**
     * 生成订单
     * @return
     */
    private String generateOrderNo(){
        // 订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        // 前八位时间信息
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
        // 中间6位为自增
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for(int i = 0;i < 6 - sequenceStr.length();++i){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        // 最后两位分库分表位
        stringBuilder.append("00");
        return stringBuilder.toString();
    }

    private OrderDO convertFromModel(OrderModel orderModel){
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        return orderDO;
    }
}
