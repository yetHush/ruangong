package bigdata.filesystem.comn.utils;

import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.exception.ErrorMsgException;
import bigdata.filesystem.dto.UserCreateDTO;
import cn.hutool.json.JSONUtil;
import com.power.common.util.RSAUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: RsaUtil
 * @Description: 项目数据用RSA加密解密工具
 * @Author: yangqh
 * @Date: 2020/12/25/025 11:02
 * @Version: v1.0
 */
@Component
public class RsaUtil {
    public static final String PUBLICKEY = "publicKey";
    public static final String PRIVATEKEY = "privateKey";
    public static final String CHARSET = "UTF-8";
    public static final int KEY_SIZE = 2048;
    private static final String RSA_ECB_PADDING = "RSA/ECB/PKCS1Padding";
    private static final String KEY_ALGORITHM = "RSA";

    private static String PUBLIC_KEY;

    @Value("${rsa.public-key}")
    public void setPublicKey(String publicKey) {
        RsaUtil.PUBLIC_KEY = publicKey;
    }

    private static String PRIVATE_KEY;

    @Value("${rsa.private-key}")
    public void setPrivateKey(String privateKey) {
        RsaUtil.PRIVATE_KEY = privateKey;
    }


    /**
     * * 生成密钥对 *
     *
     * @param keySize key size
     * @return KeyPair 密钥对
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(keySize, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        System.out.println("privateKey:" + keyPair.getPrivate());
        System.out.println("publicKey:" + keyPair.getPublic());
        return keyPair;

    }

    /**
     * map中的公钥和私钥都经过base64编码
     *
     * @param keySize size of key
     * @return map
     */
    public static Map<String, String> createKeys(int keySize) {
        try {
            KeyPair keyPair = RSAUtil.generateKeyPair(keySize);
            Key publicKey = keyPair.getPublic();
            String publicKeyStr = StringUtils.newStringUtf8(Base64.encodeBase64(publicKey.getEncoded()));
            Key privateKey = keyPair.getPrivate();
            String privateKeyStr = StringUtils.newStringUtf8(Base64.encodeBase64(privateKey.getEncoded()));
            Map<String, String> keyPairMap = new HashMap<>();
            keyPairMap.put(PUBLICKEY, publicKeyStr);
            keyPairMap.put(PRIVATEKEY, privateKeyStr);
            return keyPairMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据秘钥对KeyPair获取公钥
     *
     * @param keyPair KeyPair
     * @return String
     */
    public static String getPublicKey(KeyPair keyPair) {
        Key publicKey = keyPair.getPublic();
        return StringUtils.newStringUtf8(Base64.encodeBase64(publicKey.getEncoded()));
    }

    /**
     * 根据秘钥对KeyPair获取私钥
     *
     * @param keyPair KeyPair
     * @return String
     */
    public static String getPrivateKey(KeyPair keyPair) {
        Key privateKey = keyPair.getPrivate();
        return StringUtils.newStringUtf8(Base64.encodeBase64(privateKey.getEncoded()));
    }

    /**
     * @Description: 对DTO之类的数据进行加密
     * @param: [clazz]
     * @return: java.lang.String
     * @auther: yangqh
     * @date: 2020/12/25/025 14:04
     */
    public static <T> String encrypt(T clazz) {
        String data = JSONUtil.toJsonPrettyStr(clazz);
        // 使用公钥来加密字符串
        String encodedData = RSAUtil.encryptString(data, RsaUtil.PUBLIC_KEY);
        return encodedData;
    }

    /**
     * @Description: 对数据进行解密，并转换为相应的DTO数据
     * @param: [data, beanClass]
     * @return: T
     * @auther: yangqh
     * @date: 2020/12/25/025 14:31
     */
    public static <T> T decrypt(String data, Class<T> beanClass) {
        // 使用私钥解密已加密的字符串
        String decodedData = null;
        try {
            decodedData = RSAUtil.decryptString(data, RsaUtil.PRIVATE_KEY);
        } catch (Exception e) {
            throw new ErrorMsgException(BaseMessage.DATA_ERROR.getMsg());
        }
        T clazz = JSONUtil.toBean(decodedData, beanClass);
        return clazz;
    }

//    public static void main(String[] args) {
//      try {
//        KeyPair keyPair = RsaUtil.generateKeyPair(RsaUtil.KEY_SIZE);
//        System.out.println("privateKey:" + RsaUtil.getPrivateKey(keyPair));
//        System.out.println("publicKey:" + RsaUtil.getPublicKey(keyPair));
//      } catch (NoSuchAlgorithmException e) {
//        e.printStackTrace();
//      }
//    }
}
