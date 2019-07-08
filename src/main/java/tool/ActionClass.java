package tool;

import com.mongodb.*;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * mongodb行为抽象类
 *
 * @author 梁昊
 * @version 1.0
 * @Date:2019-07-05
 */
public abstract class ActionClass {
    private String collectName;
    private MongoTemplate mongoTemplate;

    public ActionClass(String collectName){
        this.collectName = collectName;
    }

    public String getMyVersion() {
        return String.format("My Version:", "2019.07.08.1");
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public String getCollectName() {
        return collectName;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
