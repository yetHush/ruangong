package bigdata.filesystem.comn.utils;

import cn.hutool.json.JSONUtil;
import com.power.common.util.AESUtil;
import com.power.common.util.RSAUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: AES加解密工具
 * @param:
 * @return:
 * @auther: yangqh
 * @date: 2020/12/25/025 14:05
 */
@Component
public class AesUtil {
    private static String AES_PWD;

    @Value("${aes.pwd}")
    public void setAesPwd(String aesPwd) {
        AesUtil.AES_PWD = aesPwd;
    }

    private static String AES_VECTOR;

    @Value("${aes.vector}")
    public void setAesVector(String aesVector) {
        AesUtil.AES_VECTOR = aesVector;
    }

    /**
     * @Description: AES加密DTO数据并返回字符串
     * @param: [clazz]
     * @return: java.lang.String
     * @auther: yangqh
     * @date: 2020/12/25/025 14:36
     */
    public static <T> String encrypt(T clazz) {
        String data = JSONUtil.toJsonStr(clazz);
        // 使用公钥来加密字符串
        String encodedData = AESUtil.encodeByCBC(data, AesUtil.AES_PWD, AesUtil.AES_VECTOR);
//        String encodedData = AESUtil.encodeByECB(data, AesUtil.AES_PWD);
        return encodedData;
    }

    /**
     * @Description: AES解密数据并返回DTO
     * @param: [data, beanClass]
     * @return: T
     * @auther: yangqh
     * @date: 2020/12/25/025 14:41
     */
    public static <T> T decrypt(String data, Class<T> beanClass) {
        // 使用私钥解密已加密的字符串
        String decodedData = AESUtil.decodeByCBC(data, AesUtil.AES_PWD, AesUtil.AES_VECTOR);
//        String decodedData = AESUtil.decodeByECB(data, AesUtil.AES_PWD);
        T clazz = JSONUtil.toBean(decodedData, beanClass);
        return clazz;
    }
}
