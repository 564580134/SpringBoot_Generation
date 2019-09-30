package com.mybatis.generation.util.serializable;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
 * Example类和model类实现序列化插件
 *
 * @author Liu Runyong
 * @date 2019/8/15
 */
public class SerializablePlugin extends PluginAdapter {
    private FullyQualifiedJavaType serializable = new FullyQualifiedJavaType("java.io.Serializable");
    private FullyQualifiedJavaType serializable1 = new FullyQualifiedJavaType("lombok.AllArgsConstructor");
    private FullyQualifiedJavaType serializable2 = new FullyQualifiedJavaType("lombok.Data");
    private FullyQualifiedJavaType serializable3 = new FullyQualifiedJavaType("lombok.NoArgsConstructor");
    private FullyQualifiedJavaType serializable4 = new FullyQualifiedJavaType("lombok.experimental.Accessors");
    private FullyQualifiedJavaType serializable5 = new FullyQualifiedJavaType("io.swagger.annotations.ApiModel");
    private FullyQualifiedJavaType serializable6 = new FullyQualifiedJavaType("io.swagger.annotations.ApiModelProperty");
    private FullyQualifiedJavaType gwtSerializable = new FullyQualifiedJavaType("com.google.gwt.user.client.rpc.IsSerializable");
    private boolean addGWTInterface;
    private boolean suppressJavaInterface;

    public SerializablePlugin() {
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        this.addGWTInterface = Boolean.valueOf(properties.getProperty("addGWTInterface")).booleanValue();
        this.suppressJavaInterface = Boolean.valueOf(properties.getProperty("suppressJavaInterface")).booleanValue();
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成getter
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成setter
        return false;
    }


    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        this.makeSerializable(topLevelClass, introspectedTable);
        return true;
    }

    protected void makeSerializable(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (this.addGWTInterface) {
            topLevelClass.addImportedType(this.gwtSerializable);
            topLevelClass.addSuperInterface(this.gwtSerializable);
        }

        if (!this.suppressJavaInterface) {
            topLevelClass.addImportedType(this.serializable);
            topLevelClass.addImportedType(this.serializable1);
            topLevelClass.addImportedType(this.serializable2);
            topLevelClass.addImportedType(this.serializable3);
            topLevelClass.addImportedType(this.serializable4);
            topLevelClass.addImportedType(this.serializable5);
            topLevelClass.addImportedType(this.serializable6);
            topLevelClass.addJavaDocLine("/**");
            topLevelClass.addJavaDocLine(" * " + introspectedTable.getTableConfiguration().getDomainObjectName() + " " + introspectedTable.getRemarks());
            topLevelClass.addJavaDocLine(" * ");
            topLevelClass.addJavaDocLine(" * @author Liu Runyong");
            topLevelClass.addJavaDocLine(" * @date " + new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
            topLevelClass.addJavaDocLine(" */");
            topLevelClass.addAnnotation("@ApiModel(value = \"" + introspectedTable.getTableConfiguration().getDomainObjectName() + "\",description = \"" + introspectedTable.getRemarks() + "\")");
            topLevelClass.addAnnotation("@Data");
            topLevelClass.addAnnotation("@AllArgsConstructor");
            topLevelClass.addAnnotation("@NoArgsConstructor");
            topLevelClass.addAnnotation("@Accessors(chain = true)");
            topLevelClass.addImportedType(this.serializable4);
            topLevelClass.addSuperInterface(this.serializable);
            Field field = new Field();
            field.setFinal(true);
            field.setInitializationString("1L");
            field.setName("serialVersionUID");
            field.setStatic(true);
            field.setType(new FullyQualifiedJavaType("long"));
            field.setVisibility(JavaVisibility.PRIVATE);
            this.context.getCommentGenerator().addFieldComment(field, introspectedTable);
            topLevelClass.addField(field);
        }

    }

}
