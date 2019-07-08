package model;

/**
 * mongodb中的查询条件过滤器的字段信息
 * @author 梁昊
 * @Date:2019-07-04
 * @version 1.0
 */
public final class FieldModel {
    /**
     * 属性名称
     */
    private String fieldName;
    /**
     * 属性类型
     */
    private String fieldType;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
