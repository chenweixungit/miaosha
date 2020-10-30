package com.miaoshaproject.controller;

import com.miaoshaproject.error.BussinessException;
import com.miaoshaproject.error.EnumBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller("order")
@RequestMapping("/order")
@CrossOrigin
public class OrderController extends BaseController{

    @Autowired
    OrderService orderService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/order",method = {RequestMethod.POST},consumes = {CONTENT_FORMAT_TYPE})
    @ResponseBody
    public CommonReturnType createOrder(
            @RequestParam(name = "itemId")Integer itemId,
            @RequestParam(name = "amount") Integer amount,
            @RequestParam(name = "promoId",required = false) Integer promoId) throws BussinessException {
        Boolean isLogin = (Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin == null || isLogin.booleanValue()){
            throw  new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"用户未登录");
        }
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("userModel");
        if(userModel == null){
            throw  new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息为空");
        }
        orderService.createOrder(userModel.getId(),itemId,promoId,amount);
        return CommonReturnType.create(null);
    }

}
