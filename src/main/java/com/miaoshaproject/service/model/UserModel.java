package com.miaoshaproject.service.model;





import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserModel {
    private Integer id;
    @NotNull(message = "姓名不能为空")
    private String name;
    @NotNull(message = "性别不能为空")
    private Byte gender;
    @NotNull(message = "年龄不能为空")
    @Min(value = 0,message = "年龄不能小于0岁")
    @Max(value = 150,message = "年龄不能超过150岁")
    private Integer age;
    @NotNull(message = "手机号不能为空")
    private String telephone;

    private Integer registerMode;

    private Integer thirdPartyId;
    @NotNull(message = "密码不能为空")
    private String encrypedPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(Integer registerMode) {
        this.registerMode = registerMode;
    }

    public Integer getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(Integer thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getEncrypedPassword() {
        return encrypedPassword;
    }

    public void setEncrypedPassword(String encrypedPassword) {
        this.encrypedPassword = encrypedPassword;
    }
}
