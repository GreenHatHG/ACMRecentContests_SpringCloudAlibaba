package team.huoguo.acm.commons.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT加密，校验工具
 *
 * @author GreenHatHG
 **/

@Component
@Data
public class JWTUtil {

    // 过期时间1天
    private static long EXPIRE_TIME = 24* 60 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param id
     * @param secret 秘钥
     * @return
     */
    public static boolean verify(String token, String id, String secret) {
        try {
            //对秘钥进行加密后再与用户名混淆在一起
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("id", id)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return "";
        }
    }

    public static String getId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("id").asString();
        } catch (JWTDecodeException e) {
            return "";
        }
    }

    /**
     * 生成签名
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String id, String username, String secret) {
        // 指定过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withClaim("id", id)
                .withExpiresAt(date)
                .sign(algorithm);
    }

}
