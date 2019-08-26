package tool;

import model.CriterialFilter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.text.ParseException;
import java.util.List;

/**
 * mongodb增加(save)、修改、删除类
 *
 * @author 梁昊
 * @version 1.0
 * @Date:2019-07-05
 */
public class ChangeAction extends ActionClass {
    public ChangeAction(){
        super(null);
    }

    public ChangeAction(String collectName) {
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
     * 指定集合 修改数据，且仅修改查到的第一条
     *
     * @param criterialFilterList 条件构造器
     * @param keyAndValueList     需要修改的字段和内容列表
     * @param collectName         collection名称
     * @return 影响行数
     * @throws ParseException ISODate转换异常
     */
    public int updateFirst(List<CriterialFilter> criterialFilterList, List<CriterialFilter> keyAndValueList, String collectName) throws ParseException {
        return updateMultOrOne(criterialFilterList, keyAndValueList, collectName, false);
    }

    /**
     * 指定集合 修改数据，且修改所找到的所有数据
     *
     * @param criterialFilterList 条件构造器
     * @param keyAndValueList     需要修改的字段和内容列表
     * @param collectName         collection名称
     * @return 影响行数
     * @throws ParseException ISODate转换异常
     */
    public int updateMult(List<CriterialFilter> criterialFilterList, List<CriterialFilter> keyAndValueList, String collectName) throws ParseException {
        return updateMultOrOne(criterialFilterList, keyAndValueList, collectName, true);
    }

    /**
     * 指定集合 修改数据，且修改所找到的所有数据
     *
     * @param criterialFilterList 条件构造器
     * @param keyAndValueList     需要修改的字段和内容列表
     * @param collectName         collection名称
     * @param allSign             true:修改所有数据，false:仅修改查到的第条数据
     * @return 影响行数
     * @throws ParseException ISODate转换异常
     */
    private int updateMultOrOne(List<CriterialFilter> criterialFilterList, List<CriterialFilter> keyAndValueList, String collectName, boolean allSign) throws ParseException {
        if (keyAndValueList == null || keyAndValueList.isEmpty()) {
            return 0;
        }
        Query query = new Query();
        if (criterialFilterList != null && criterialFilterList.size() > 0) {
            Criteria criteria = ToolClass.getCriteria(criterialFilterList);
            query.query(criteria);
        }
        if (keyAndValueList != null && keyAndValueList.size() > 0) {
            Criteria criteria = ToolClass.getCriteria(keyAndValueList);
            query.query(criteria);
        }
        Update update = new Update();
        for (CriterialFilter row :
                keyAndValueList) {
            update.set(row.getFieldName(), row.getFieldValue());
        }
        if (allSign)
            return super.getMongoTemplate().updateMulti(query, update, collectName).getN();
        else
            return super.getMongoTemplate().updateFirst(query, update, collectName).getN();
    }

    /**
     * 保存数据，若数据_id不存在则为增加，否则，为update.
     *
     * @param data        数据
     * @param collectName collection名称
     */
    public void savaData(Object data, String collectName) {
        super.getMongoTemplate().save(data, collectName);
    }

    /**
     * 保存数据，若数据_id不存在则为增加，否则，为update.
     * 需要在Model层加入@Document注释，Collection = "???"，若_id重复，会报错
     *
     * @param data 数据
     */
    public void saveData(Object data) {
        super.getMongoTemplate().save(data);
    }

    /**
     * 增加数据，需要在Model层加入@Document注释，Collection = "???"，若_id重复，会报错
     *
     * @param data 指定数据
     */
    public void insertData(Object data) {
        super.getMongoTemplate().insert(data);
    }

    /**
     * 增加数据，需要在Model层加入@Document注释，Collection = "???"，若_id重复，会报错
     *
     * @param data        指定数据
     * @param collectName Collection名称
     */
    public void insertData(Object data, String collectName) {
        super.getMongoTemplate().insert(data, collectName);
    }

    /**
     * 删除指定数据，需要在Model层加入@Document注释，Collection = "???"
     *
     * @param data 指定数据
     * @return 影响条数
     */
    public int deleteData(Object data) {
        return super.getMongoTemplate().remove(data).getN();
    }
    /**
     * 删除指定数据，需要在Model层加入@Document注释，Collection = "???"
     * @param data 指定数据
     * @return 影响条数
     */

    /**
     * 根据条件删除数据
     *
     * @param criteria    条件数据
     * @param collectName Collection名称
     * @return 影响条数
     */
    public int deleteDataByCondition(Criteria criteria, String collectName) {
        Query query = Query.query(criteria);
        return super.getMongoTemplate().remove(query, collectName).getN();
    }

    /**
     * 根据条件删除数据
     *
     * @param criterialFilterList 条件列表
     * @param collectName         Collection名称
     * @return 影响条数
     * @throws ParseException ISODate转换异常
     */
    public int deleteDataByCondition(List<CriterialFilter> criterialFilterList, String collectName) throws ParseException {
        Criteria criteria = ToolClass.getCriteria(criterialFilterList);
        return deleteDataByCondition(criteria, collectName);
    }


    /**
     * 删除指定数据
     *
     * @param data        指定数据
     * @param collectName collection名
     * @return 影响条数
     */
    public int deleteData(Object data, String collectName) {
        return super.getMongoTemplate().remove(data, collectName).getN();
    }

    /**
     * 清空Collection
     *
     * @param collectName Collection名称
     */
    public void clearCollection(String collectName) {
        super.getMongoTemplate().dropCollection(collectName);
    }

    /**
     * 根据指定类名，清空Collection
     *
     * @param className 类名
     */
    public void clearCollection(Class<?> className) {
        super.getMongoTemplate().dropCollection(className);
    }

}
