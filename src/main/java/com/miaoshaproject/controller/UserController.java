package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewObjects.UserVO;
import com.miaoshaproject.error.BussinessException;
import com.miaoshaproject.error.EnumBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import static com.miaoshaproject.response.CommonReturnType.create;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    /**
     * 用户注册
     * @param telephone
     * @param optCode
     * @param name
     * @param gender
     * @param age
     * @param password
     * @return
     * @throws BussinessException
     */
    @RequestMapping(value = "/register",method = {RequestMethod.POST},consumes = {CONTENT_FORMAT_TYPE})
    @ResponseBody
    public CommonReturnType uerRegister(
    @RequestParam(name="telephone")String telephone,
    @RequestParam(name="optCode")String optCode,
    @RequestParam(name="name") String name ,
    @RequestParam(name="gender") String gender,
    @RequestParam(name="age") Integer age,
    @RequestParam(name="password") String password) throws BussinessException{
        String sessionCode = (String)httpServletRequest.getSession().getAttribute(telephone);
        // 判断校验码是否正确
        if(!com.alibaba.druid.util.StringUtils.equals(optCode,sessionCode)){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 把数据插入数据库
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setEncrypedPassword(MD5Encoder.encode(password.getBytes()));
        userModel.setAge(age);
        Byte gen;
        if(com.alibaba.druid.util.StringUtils.equals(gender,"男")){
            gen = new Byte(String.valueOf(1));
        }else{
            gen = new Byte(String.valueOf(0));
        }
        userModel.setGender(gen);
        userModel.setTelephone(telephone);
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     * @throws BussinessException
     */
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BussinessException{
        UserModel userModel = userService.getUserById(id);
        if(userModel == null){
            throw new BussinessException(EnumBusinessError.UER_NOT_EXIST);
        }
        return create(convertFromModel(userService.getUserById(id)));
    }

    /**
     * userModel转换为用户可见的userVO类型
     * @param userModel
     * @return
     */
    public UserVO convertFromModel(UserModel userModel){
        if(userModel == null) return null;
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(userModel,userVo);
        return userVo;
    }


    /**
     * 获取验证码
     * @param telephone
     * @return
     */
    @RequestMapping(value = "/getOtp",method = {RequestMethod.POST},consumes = {CONTENT_FORMAT_TYPE})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telephone")String telephone){
        // 生成随机的六位数验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 100000;

        // 这里方便使用，使用了httpsession来存储电话和验证码
        // 正常的分布式系统中，采用redis来存储的验证码，redis本身存储键值对格式的数据，同时redis自带过期处理，
        httpServletRequest.getSession().setAttribute(telephone,randomInt);
        System.out.println("telephone: "+ telephone + " 验证码" + randomInt);
        return CommonReturnType.create(null);
    }



}
