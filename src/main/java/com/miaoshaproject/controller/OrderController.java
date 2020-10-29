package com.miaoshaproject.controller;

import com.miaoshaproject.error.BussinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin
public class OrderController extends BaseController{

    @Autowired
    OrderService orderService;
    @RequestMapping(value = "/order",method = {RequestMethod.POST},consumes = {CONTENT_FORMAT_TYPE})
    @ResponseBody
    public CommonReturnType createOrder(
            @RequestParam(name = "itemId")Integer itemId,
            @RequestParam(name = "amount") Integer amount) throws BussinessException {
        orderService.createOrder(null,itemId,amount);
        return CommonReturnType.create(null);
    }

}
