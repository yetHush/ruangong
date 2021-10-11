package bigdata.filesystem.service;

import bigdata.filesystem.entity.DmUser;

/**
 * @Description: Token工具
 * @param:
 * @return:
 * @auther: yangqh
 * @date: 2020/12/25/025 16:53
 */
public interface TokenService {

    String getToken(DmUser dmUser);

    DmUser checkToken(String token);
}