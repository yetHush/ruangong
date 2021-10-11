package bigdata.filesystem.service.impl;

import bigdata.filesystem.comn.base.BaseCode;
import bigdata.filesystem.comn.base.BaseMessage;
import bigdata.filesystem.comn.exception.ErrorMsgException;
import bigdata.filesystem.entity.DmUser;
import bigdata.filesystem.service.TokenService;
import bigdata.filesystem.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: Token工具
 * @param:
 * @return:
 * @auther: yangqh
 * @date: 2020/12/25/025 16:37
 */
@Service("TokenService")
public class TokenServiceImpl implements TokenService {

    @Value("${base.default.login.expire-time}")
    private Long DEFAULT_EXPIRE_TIME;

    @Autowired
    private UserService userService;

    /**
     * @Description: Token生成函数
     * @param: [user]
     * @return: java.lang.String
     * @auther: yangqh
     * @date: 2020/12/25/025 16:37
     */
    public String getToken(DmUser dmUser) {
        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + this.DEFAULT_EXPIRE_TIME;//一小时有效时间
        Date start = new Date(currentTime);
        Date end = new Date(endTime);
        String token = "";

        token = JWT.create().withAudience(dmUser.getUserNum()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(dmUser.getPwdSalt()));
        return token;
    }

    /**
     * @Description: 校验Token的有效性
     * @param: [token]
     * @return: void
     * @auther: yangqh
     * @date: 2020/12/25/025 16:51
     */
    public DmUser checkToken(String token) {
        // 执行认证
        if (StringUtils.isBlank(token)) {
            throw new ErrorMsgException(BaseCode.TOKEN_INVALID.getCode(), BaseMessage.PLEASE_LOGIN.getMsg());
        }
        String userNum;
        DmUser dmUser;
        try {
            //查询用户并校验
            userNum = JWT.decode(token).getAudience().get(0);
            dmUser = userService.getUserByUserNum(userNum);
            if (dmUser == null) {
                throw new ErrorMsgException(BaseCode.TOKEN_INVALID.getCode(), BaseMessage.USER_OR_PWD_ERROR.getMsg());
            }
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(dmUser.getPwdSalt())).build();
            jwtVerifier.verify(token);
            return dmUser;
        } catch (JWTDecodeException j) {
            throw new ErrorMsgException(BaseCode.TOKEN_INVALID.getCode(), BaseMessage.PLEASE_LOGIN.getMsg());
        } catch (JWTVerificationException e) {
            throw new ErrorMsgException(BaseCode.TOKEN_INVALID.getCode(), BaseMessage.PLEASE_LOGIN.getMsg());
        }
    }
}
