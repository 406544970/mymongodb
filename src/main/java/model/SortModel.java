package model;

/**
 * 查询条件中的Sort
 * @author 梁昊
 * @Date:2019-07-04
 * @version 1.0
 */
public final class SortModel {
    private Boolean ascSign;
    private String fieldName;

    public Boolean getAscSign() {
        return ascSign;
    }

    public void setAscSign(Boolean ascSign) {
        this.ascSign = ascSign;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public SortModel() {
        super();
        this.ascSign = true;
    }
}
