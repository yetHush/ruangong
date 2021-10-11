package bigdata.filesystem.comn.utils;

import cn.hutool.crypto.digest.BCrypt;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @ClassName: PasswordUtil
 * @Description: 用户加解密工具
 * @Author: yangqh
 * @Date: 2020/12/25/025 15:05
 * @Version: v1.0
 */

@Component
public class PasswordUtil {
    /**
     * @Description: 校验密码是否正确
     * @param: [loginPwd, userPwd, salt]
     * @return: java.lang.Boolean
     * @auther: yangqh
     * @date: 2020/12/25/025 15:09
     */
    public static Boolean checkPassword(String loginPwd, String userPwd, String salt) {
        String encryptedPwd = AesUtil.encrypt(loginPwd);
        return StringUtils.equals(BCrypt.hashpw(encryptedPwd, salt), userPwd);
    }

    /**
     * @Description: 用AES算法对密码加密，再加盐加密
     * @param: [password, salt]
     * @return: java.lang.String
     * @auther: yangqh
     * @date: 2020/12/25/025 15:12
     */
    public static String encryptPassword(String password, String salt) {
        //1.用AES算法进行对称加密
        String encrpytData = AesUtil.encrypt(password);
        //2.加盐加密
        String userPwd = BCrypt.hashpw(encrpytData, salt);
        return userPwd;
    }

    /**
     * @Description: 生成随机盐，对加密后的数据进行加密
     * @param: []
     * @return: java.lang.String
     * @auther: yangqh
     * @date: 2020/12/25/025 15:11
     */
    public static String getSalt() {
        //生成随机盐，对加密后的数据进行加密
        String salt = BCrypt.gensalt();
        return salt;
    }
}
