package team.huoguo.acm.userinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.huoguo.acm.commons.exception.UniqueException;
import team.huoguo.acm.userinfo.dao.MongoDao;
import team.huoguo.acm.userinfo.domain.UserInfo;

/**
 * @author GreenHatHG
 */

@Component
public class UserInfoService {

    @Autowired
    private MongoDao mongoDao;

    public UserInfo findOne(String queryKey, String queryValue){
        return mongoDao.findOne(queryKey, queryValue);
    }

    public Boolean exists(String queryKey, String queryValue){
        return mongoDao.exists(queryKey, queryValue);
    }

    public void usernameUniue(String username){
        if(mongoDao.exists("username", username)){
            throw new UniqueException("用户名已存在");
        }
    }

    public void mailUnique(String mail){
        if(mongoDao.exists("mail", mail)){
            throw new UniqueException("邮箱已存在");
        }
    }

    public void registerUnique(String username, String mail){
        usernameUniue(username);
        mailUnique(mail);
    }

    public void insert(UserInfo userInfo){
        mongoDao.insert(userInfo);
    }

    public void updateOne(String queryKey, String queryValue, String updateKey, String updateValue){
        mongoDao.updateOne(queryKey, queryValue, updateKey, updateValue);
    }
}
