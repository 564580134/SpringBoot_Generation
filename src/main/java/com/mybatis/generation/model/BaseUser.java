package com.mybatis.generation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * BaseUser 
 * 
 * @author Liu Runyong
 * @date 2019/08/22
 */
@ApiModel(value = "BaseUser",description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BaseUser implements Serializable {
    @ApiModelProperty(value = "账号id",hidden=true)
    private Integer userId;

    @ApiModelProperty(value = "账号",hidden=true)
    private String account;

    @ApiModelProperty(value = "用户名",hidden=true)
    private String userName;

    @ApiModelProperty(value = "密码",hidden=true)
    private String password;

    @ApiModelProperty(value = "盐值",hidden=true)
    private String salt;

    @ApiModelProperty(value = "性别",hidden=true)
    private String sex;

    @ApiModelProperty(value = "手机号",hidden=true)
    private String phone;

    @ApiModelProperty(value = "邮箱",hidden=true)
    private String email;

    @ApiModelProperty(value = "组织id",hidden=true)
    private String organizationId;

    @ApiModelProperty(value = "账号是否可用 0:可用 1:禁用",hidden=true)
    private Integer status;

    @ApiModelProperty(value = "账号是否删除 0:未删除 1:已删除",hidden=true)
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间",hidden=true)
    private Date createTime;

    @ApiModelProperty(value = "修改时间",hidden=true)
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}