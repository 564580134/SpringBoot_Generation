package com.mybatis.generation.util.serializable;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 生成model中，字段增加注释
 *
 * @author Liu Runyong
 * @date 2019/8/15
 */
public class CommentGenerator extends DefaultCommentGenerator {


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        super.addFieldComment(field, introspectedTable, introspectedColumn);
        if (introspectedColumn.getRemarks() != null && !"".equals(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("@ApiModelProperty(value = \"" + introspectedColumn.getRemarks() + "\",hidden=true)");
        }
    }




}
