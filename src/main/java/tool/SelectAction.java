package tool;

import model.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询行为类
 *
 * @author 梁昊
 * @version 1.0
 * @Date:2019-07-05
 */
public class SelectAction extends ActionClass {
    public SelectAction(String collectName){
        super(collectName);
    }

    @Override
    public String getMyVersion() {
        return super.getMyVersion();
    }

    @Override
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        super.setMongoTemplate(mongoTemplate);
    }

    @Override
    public String getCollectName() {
        return super.getCollectName();
    }

    @Override
    public void setCollectName(String collectName) {
        super.setCollectName(collectName);
    }

    @Override
    public MongoTemplate getMongoTemplate() {
        return super.getMongoTemplate();
    }

    /**
     * Select方法，通过条件构造器得到
     *
     * @param resultClassName 返回类名称
     * @param mongodbSelect   Select条件构造器对象
     * @param <T>             返回类泛型
     * @return 返回结果集
     * @throws ParseException IOSDate转换异常
     */
    public <T> List<T> getMongodbList(Class<T> resultClassName, MongodbSelect mongodbSelect) throws ParseException {
        boolean fieldHave = true;
        List<CriterialFilter> criterialFilterList = mongodbSelect.getCriterialFilterList();
        fieldHave = isFieldCriterialFilterList(mongodbSelect, fieldHave, criterialFilterList);
        if (fieldHave) {
            List<CriterialFilter> orCriterialFilterList = mongodbSelect.getOrCriterialFilterList();
            fieldHave = isFieldCriterialFilterList(mongodbSelect, fieldHave, orCriterialFilterList);
        }
        if (fieldHave) {
            List<SortModel> sortModels = mongodbSelect.getSortModels();
            if (sortModels != null) {
                for (SortModel sortModel : mongodbSelect.getSortModels()
                        ) {
                    fieldHave = fieldHave && mongodbSelect.fieldNameIsHave(sortModel.getFieldName());
                }
            }
        }
        if (!fieldHave) {
            return null;
        }
        Query query = new Query();
        PageModel pageModel = null;
        if (mongodbSelect.getSortModels() != null) {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();  //排序
            for (SortModel row : mongodbSelect.getSortModels()
                    ) {
                orders.add(new Sort.Order(row.getAscSign() ? Sort.Direction.ASC : Sort.Direction.DESC
                        , row.getFieldName()));
            }
            if (orders != null && !orders.isEmpty()) {
                pageModel = new PageModel();
                Sort sort = new Sort(orders);
                pageModel.setSort(sort);
            }
        }
        if (mongodbSelect.getPageNumber() > 0 && mongodbSelect.getPageSize() > 0) {
            pageModel.setPageSize(mongodbSelect.getPageSize());
            pageModel.setPageNumber(mongodbSelect.getPageNumber());
        }
        MyPageable myPageable = null;
        if (pageModel != null) {
            myPageable = new MyPageable();
            myPageable.setPage(pageModel);
        }
        Criteria criteria = ToolClass.getCriteria(mongodbSelect.getCriterialFilterList());
        if (criteria != null) {
//            Criteria orCriteria = ToolClass.getCriteria(mongodbSelect.getOrCriterialFilterList());
//            if (orCriteria != null) {
//                criteria = criteria.orOperator(orCriteria);
//            }
            query.addCriteria(criteria);
        }
        if (mongodbSelect.getPageNumber() > 0 && mongodbSelect.getPageSize() > 0) {
            mongodbSelect.setDataTotal((int) super.getMongoTemplate().count(query, resultClassName, super.getCollectName()));
        }
        if (myPageable == null) {
            return super.getMongoTemplate().find(query, resultClassName, super.getCollectName());
        } else {
            return super.getMongoTemplate().find(query.with(myPageable), resultClassName, super.getCollectName());
        }
    }

    private boolean isFieldCriterialFilterList(MongodbSelect mongodbSelect, boolean fieldHave, List<CriterialFilter> criterialFilterList) {
        if (criterialFilterList != null) {
            for (CriterialFilter criterialFilter : criterialFilterList
                    ) {
                fieldHave = fieldHave && mongodbSelect.fieldNameIsHave(criterialFilter.getFieldName());
                String javaType = mongodbSelect.getJavaType(criterialFilter.getFieldName());
                if (javaType != null) {
                    criterialFilter.setJavaType(javaType);
                }
            }
        }
        return fieldHave;
    }

    /**
     * 检测条件构造器中的字段名、字段值是否合法
     * 由调用方根据需要调用，检测合法后，到将检测调用方代码删除
     *
     * @param criterialFilterList 条件构造器
     * @param outMessage          不合法的信息
     * @return 是否合法
     */
    public boolean checkCriterial(List<CriterialFilter> criterialFilterList, StringBuilder outMessage) {
        boolean returnSign = true;
        if (criterialFilterList != null && criterialFilterList.size() > 0) {
            int index = 1;
            for (CriterialFilter criterialFilter : criterialFilterList
                    ) {
                if (criterialFilter.getFieldName() == null) {
                    outMessage.append(String.format("第%d个条件中，字段为空！", index));
                    returnSign = returnSign && false;
                }
                if (criterialFilter.getFieldValue() == null) {
                    outMessage.append(String.format("第%d个条件中，值为空！", index));
                    returnSign = returnSign && false;
                }
                index++;
            }
        }
        return returnSign;
    }


}
