package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewObjects.ItemVO;
import com.miaoshaproject.controller.viewObjects.UserVO;
import com.miaoshaproject.error.BussinessException;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller("item")
@RequestMapping("/item")
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ItemController extends BaseController {
    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/create",method = {RequestMethod.POST}, consumes = {CONTENT_FORMAT_TYPE})
    public CommonReturnType addItem(@RequestParam(name = "title")String title,
        @RequestParam(name = "price")BigDecimal price,
        @RequestParam(name = "description")String description,
        @RequestParam(name = "stock") Integer stock,
        @RequestParam(name = "imgUrl") String imgUrl) throws BussinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setImgUrl(imgUrl);
        itemService.createItem(itemModel);
        ItemVO itemVO = convertModel(itemModel);
        return  CommonReturnType.create(null);
    }

    private ItemVO convertModel(ItemModel itemModel){
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        return itemVO;
    }

}
