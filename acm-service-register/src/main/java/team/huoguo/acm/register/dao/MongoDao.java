package team.huoguo.acm.register.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import team.huoguo.acm.register.domain.UserInfo;

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

    public UserInfo findByUserName(String username){
        return mongoTemplate.findOne(new
                Query(Criteria.where("username").is(username)), UserInfo.class);
    }

    public UserInfo findByMail(String mail){
        return mongoTemplate.findOne(new
                Query(Criteria.where("mail").is(mail)), UserInfo.class);
    }

}