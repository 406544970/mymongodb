package model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询条件Model
 * @author 梁昊
 * @Date:2019-07-04
 * @version 1.0
 */
public final class MongodbSelect {
    private List<SortModel> sortModels;
    private Integer pageNumber;
    private Integer pageSize;
    private String collectName;
    private List<CriterialFilter> criterialFilterList;
    private List<CriterialFilter> orCriterialFilterList;
    private Integer dataTotal;
    private List<FieldModel> fieldNameLists;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (sortModels != null) {
            sortModels.clear();
        }
        if (criterialFilterList != null) {
            criterialFilterList.clear();
        }
        if (fieldNameLists != null) {
            fieldNameLists.clear();
        }
        if (orCriterialFilterList != null) {
            orCriterialFilterList.clear();
        }
    }

    /**
     * 分页Mongodb类
     *
     * @param className
     * @param <T>
     */
    public <T> MongodbSelect(Class<T> className) throws ClassNotFoundException {
        super();
        iniParam(true, className);
    }

    /**
     * 可自定义是否分页的Mongodb类
     *
     * @param pageSign
     * @param className
     * @param <T>
     */
    public <T> MongodbSelect(boolean pageSign, Class<T> className) throws ClassNotFoundException {
        iniParam(pageSign, className);
    }

    /**
     * 以字段名判断，此字段是否存在
     *
     * @param fieldName
     * @return
     */
    public boolean fieldNameIsHave(String fieldName) {
        if (fieldName == null) {
            return false;
        }
        if (fieldNameLists == null || fieldNameLists.isEmpty()) {
            return false;
        }
        List<FieldModel> collect = fieldNameLists.stream().filter(a -> a.getFieldName().equals(fieldName)).collect(Collectors.toList());
        if (collect != null && collect.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void setDefaultPageParam(boolean pageSign){
        setPageAllPara(pageSign);
    }
    private void setPageAllPara(boolean pageSign){
        this.pageNumber = pageSign ? 1 : 0;
        this.pageSize = pageSign ? 10 : 0;
    }

    private <T> void iniParam(boolean pageSign, Class<T> className) throws ClassNotFoundException {
        this.dataTotal = 0;
        setPageAllPara(pageSign);
        fieldNameLists = new ArrayList<>();
        for (Field field : className.getDeclaredFields()) {
            field.setAccessible(true);
            FieldModel fieldModel = new FieldModel();
            fieldModel.setFieldName(field.getName());
            fieldModel.setFieldType(field.getType().toString());
            fieldNameLists.add(fieldModel);
        }
    }

    public String getJavaType(String fieldName) {
        if (fieldName == null)
            return null;
        if (fieldNameLists == null || fieldNameLists.isEmpty())
            return null;

        int index = -1;
        for (int i = 0; i < fieldNameLists.size(); i++) {
            if (fieldNameLists.get(i).getFieldName().equals(fieldName)) {
                index = i;
                break;
            }
        }
        return index > -1 ? fieldNameLists.get(index).getFieldType() : null;
    }

    public List<SortModel> getSortModels() {
        return sortModels;
    }

    public void setSortModels(List<SortModel> sortModels) {
        this.sortModels = sortModels;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCollectName() {
        return collectName;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public List<CriterialFilter> getCriterialFilterList() {
        return criterialFilterList;
    }

    public void setCriterialFilterList(List<CriterialFilter> criterialFilterList) {
        this.criterialFilterList = criterialFilterList;
    }

    public List<CriterialFilter> getOrCriterialFilterList() {
        return orCriterialFilterList;
    }

    public void setOrCriterialFilterList(List<CriterialFilter> orCriterialFilterList) {
        this.orCriterialFilterList = orCriterialFilterList;
    }

    public Integer getDataTotal() {
        return dataTotal;
    }

    public void setDataTotal(Integer dataTotal) {
        this.dataTotal = dataTotal;
    }

    public List<FieldModel> getFieldNameLists() {
        return fieldNameLists;
    }

    public void setFieldNameLists(List<FieldModel> fieldNameLists) {
        this.fieldNameLists = fieldNameLists;
    }

}
