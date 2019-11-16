package team.huoguo.acm.register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import team.huoguo.acm.commons.exception.UniqueException;
import team.huoguo.acm.register.dao.MongoDao;
import team.huoguo.acm.register.domain.UserInfo;

/**
 * @author GreenHatHG
 */

@Component
public class UserInfoService {

    @Autowired
    private MongoDao mongoDao;

    public void usernameUniue(String username){
        if(mongoDao.findByUserName(username) != null){
            throw new UniqueException("用户名已存在");
        }
    }

    public void mailUnique(String mail){
        if(mongoDao.findByMail(mail) != null){
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
}
