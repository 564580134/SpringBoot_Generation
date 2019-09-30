package com.mybatis.generation.service.impl;

import com.mybatis.generation.model.BaseUser;
import com.mybatis.generation.dao.BaseUserMapper;
import com.mybatis.generation.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
* BaseUserService
*
* @author Liu Runyong
* @date  2019-08-22 14:51
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseUserServiceImpl  implements BaseUserService {


    @Override
    public void insertBaseUserSelective(BaseUser baseUser) {

    }

    @Override
    public void updateBaseUserSelectiveByPrimaryKey(BaseUser baseUser) {

    }

    @Override
    public void deleteBaseUserByPrimaryKeys(Integer[] ids) {

    }

    @Override
    public List<BaseUser> selectBaseUserByBaseUserExample(Example example) {
        return null;
    }
}