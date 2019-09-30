package com.mybatis.generation.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatis.generation.model.BaseUser;
import com.mybatis.generation.service.BaseUserService;
import com.mybatis.generation.util.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;


/**
 * BaseUserController
 *
 * @author Liu Runyong
 * @date 2019-08-22 14:51
 */

@Api(tags = "BaseUser", description = "BaseUserController")
@RestController
@RequestMapping("/BaseUser/")
public class BaseUserController {


    @Autowired
    public BaseUserService baseUserService;


    @ApiOperation("新增")
    @PostMapping("insert")
    public ResponseResult insert(@ModelAttribute BaseUser baseUser) {
        // 执行新增
        baseUserService.insertBaseUserSelective(baseUser);
        // 返回
        return ResponseResult.createBySuccess();
    }

    @ApiOperation("编辑")
    @PostMapping("update")
    public ResponseResult update(@ModelAttribute BaseUser baseUser) {
        // 执行编辑
        baseUserService.updateBaseUserSelectiveByPrimaryKey(baseUser);
        // 返回
        return ResponseResult.createBySuccess();
    }

    @ApiOperation("编辑")
    @PostMapping("delete")
    @ApiImplicitParam(name = "ids", value = "主键数组", type = "Integer[]")
    public ResponseResult delete(@RequestParam(value = "ids") Integer[] ids) {
        // 执行删除
        baseUserService.deleteBaseUserByPrimaryKeys(ids);
        // 返回
        return ResponseResult.createBySuccess();
    }

    @ApiOperation("查询")
    @PostMapping("select")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "pageNum", type = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", type = "Integer")
    })
    public ResponseResult select(
            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        // 设置分页属性
        PageHelper.startPage(pageNum, pageSize);
        // 查询
        List<BaseUser> baseUserList = baseUserService.selectBaseUserByBaseUserExample(new Example(BaseUser.class));
        // 返回
        return ResponseResult.createBySuccess(new PageInfo<>(baseUserList));
    }

    @Scheduled(cron = "*/2 * * * * *")
    public void get() {
        System.out.println(132);
    }
}