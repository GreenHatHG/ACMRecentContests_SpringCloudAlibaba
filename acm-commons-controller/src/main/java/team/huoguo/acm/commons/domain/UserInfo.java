package team.huoguo.acm.commons.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author GreenHatHG
 */

@Data
@Builder
public class UserInfo implements Serializable {

        private String id;
        private String username;
        private String password;
        private String mail;

        /**
         * 家乡城市
         */
        private String city;

        /**
         * 头像图片链接
         */
        private String avatar;

        /**
         * 创建时间
         */
        private Long createTime;
}
