package com.mybatis.generation.service;

import com.mybatis.generation.model.BaseUser;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


/**
* BaseUserService 用户信息
*
* @author Liu Runyong
* @date  2019-08-22 14:51
*/
public interface BaseUserService {


    /**
     * 新增BaseUser 信息
     *
     * @param baseUser 用户信息
     *
     */
    void insertBaseUserSelective(BaseUser baseUser);

    /**
     * 编辑BaseUser 信息
     *
     * @param baseUser 用户信息
     *
     */
    void updateBaseUserSelectiveByPrimaryKey(BaseUser baseUser);

    /**
     * 删除BaseUser 信息
     *
     * @param ids 主键数组
     *
     */
    void deleteBaseUserByPrimaryKeys(Integer[] ids);

    /**
     * 查询BaseUser 信息
     *
     * @param example 条件集合
     * @return list
     */
    List<BaseUser> selectBaseUserByBaseUserExample(Example example);


}