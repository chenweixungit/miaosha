package com.miaoshaproject.service.iml;


import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.UserPasswordDOMapper;
import com.miaoshaproject.dataObjects.UserDO;
import com.miaoshaproject.dataObjects.UserPasswordDO;
import com.miaoshaproject.error.BussinessException;
import com.miaoshaproject.error.EnumBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserServiceIml implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    @Override
    public UserModel getUserById(Integer id) {

        // 获取userObject信息
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        // 获取userPassword信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);

        // 返回对应的userModel信息
        return convertFromObject(userDO,userPasswordDO);
    }

    /**
     * 用户注册
     * @param userModel
     * @throws BussinessException
     */
    @Override
    @Transactional
    public void register(UserModel userModel) throws BussinessException {
        // 判断userModel是否为空
        if(userModel == null){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 判断各个属性是否为空
        if(StringUtils.isEmptyOrWhitespaceOnly(userModel.getName()) ||
            StringUtils.isEmptyOrWhitespaceOnly(userModel.getTelephone() )||
            userModel.getGender() == null || userModel.getAge() == null){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 将数据写入数据库
        UserDO userDO = convertUserDOFromObject(userModel);
        UserPasswordDO userPasswordDO= convertUserPasswordDOFromObject(userModel);
        userDOMapper.insertSelective(userDO);
        UserDO newUserDO = userDOMapper.selectByTelephone(userDO.getTelephone());
        userPasswordDO.setUserId(newUserDO.getId());
        userPasswordDO.setEncryedPassword(userModel.getEncrypedPassword());
        userPasswordDOMapper.insertSelective(userPasswordDO);

    }

    /**
     * 验证登录
     * @param telephone
     * @param encrypedPassword
     * @return
     */
    @Override
    @Transactional
    public UserModel validateLogin(String telephone, String encrypedPassword) throws BussinessException{
        // 用电话号码查询用户id
        UserDO userDO = userDOMapper.selectByTelephone(telephone);
        if(userDO == null){
            throw new BussinessException(EnumBusinessError.FAIL_LOGIN);
        }
        // 使用用户id查询密码
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        if(!com.alibaba.druid.util.StringUtils.equals(userPasswordDO.getEncryedPassword(),encrypedPassword)){
            throw new BussinessException(EnumBusinessError.FAIL_LOGIN);
        }
        return convertFromObject(userDO,userPasswordDO);
    }

    /**
     * userModel转userDO
     * @param userModel
     * @return
     */
    public UserPasswordDO convertUserPasswordDOFromObject(UserModel userModel){
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        BeanUtils.copyProperties(userModel,userPasswordDO);
        return userPasswordDO;
    }

    public UserDO convertUserDOFromObject(UserModel userModel){
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }


    public UserModel convertFromObject(UserDO userdo, UserPasswordDO userPasswordDO){
        UserModel userModel = new UserModel();
        if(userdo == null) return null;
        BeanUtils.copyProperties(userdo,userModel);
        if(userPasswordDO != null){
            userModel.setEncrypedPassword(userPasswordDO.getEncryedPassword());
        }
        return userModel;
    }
}
