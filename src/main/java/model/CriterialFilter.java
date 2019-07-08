package model;

import myenum.CriteriaOperate;

/**
 * mongodb查询条件过滤器类
 * @author 梁昊
 * @Date:2019-07-04
 * @version 1.0
 */
public final class CriterialFilter<T> {
    public CriterialFilter() {
        super();
        this.criteriaOperate = CriteriaOperate.IS;
    }
    private CriteriaOperate criteriaOperate;
    private String fieldName;
    private T fieldValue;
    private T fieldValueEnd;
    private String javaType;

    public CriteriaOperate getCriteriaOperate() {
        return criteriaOperate;
    }

    public void setCriteriaOperate(CriteriaOperate criteriaOperate) {
        this.criteriaOperate = criteriaOperate;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public T getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(T fieldValue) {
        this.fieldValue = fieldValue;
    }

    public T getFieldValueEnd() {
        return fieldValueEnd;
    }

    public void setFieldValueEnd(T fieldValueEnd) {
        this.fieldValueEnd = fieldValueEnd;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}
