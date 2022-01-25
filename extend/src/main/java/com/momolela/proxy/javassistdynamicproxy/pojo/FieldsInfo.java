package com.momolela.proxy.javassistdynamicproxy.pojo;

public class FieldsInfo {
    private String fieldName; // 属性名
    private String fieldClass; // 属性类型
    private String modifiers; // 修饰符

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldClass() {
        return fieldClass;
    }

    public void setFieldClass(String fieldClass) {
        this.fieldClass = fieldClass;
    }

    public String getModifiers() {
        return modifiers;
    }

    public void setModifiers(String modifiers) {
        this.modifiers = modifiers;
    }
}
