package team.huoguo.acm.userinfo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import team.huoguo.acm.userinfo.domain.UserInfo;

/**
 * @author GreenHatHG
 */

@Component
public class MongoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void insert(UserInfo userInfo){
        mongoTemplate.insert(userInfo);
    }

    public UserInfo findOne(String queryKey, String queryValue){
        return mongoTemplate.findOne(new
                Query(Criteria.where(queryKey).is(queryValue)), UserInfo.class);
    }

    public Boolean exists(String queryKey, String queryValue){
        return mongoTemplate.exists(
                new Query(Criteria.where(queryKey).is(queryValue)), UserInfo.class);
    }

    /**
     *
     * @param queryKey 查询类型
     * @param queryValue 查询类型对应的值
     * @param updateKey 更新的值对应的类型
     * @param updateValue 更新的值
     */
    public void updateOne(String queryKey, String queryValue, String updateKey, String updateValue){
        mongoTemplate.updateFirst(
                new Query(Criteria.where(queryKey).is(queryValue)),
                new Update().set(updateKey, updateValue),UserInfo.class);
    }

}