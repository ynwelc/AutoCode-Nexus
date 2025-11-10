package com.ynwe.aicodezero.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.ynwe.aicodezero.model.dto.app.AppQueryRequest;
import com.ynwe.aicodezero.model.entity.App;
import com.ynwe.aicodezero.model.entity.User;
import com.ynwe.aicodezero.model.vo.AppVO;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author <a href="https://github.com/ynwelc">hhhhh</a>
 */
public interface AppService extends IService<App> {

    /**
     * 部署应用
     *
     * @param appId     应用ID
     * @param loginUser 登录用户
     * @return 可访问的部署地址
     */
    String deployApp(Long appId, User loginUser);

    /**
     * 聊天生成代码
     *
     * @param appId
     * @param message
     * @param loginUser
     * @return
     */
    Flux<String> chatToGenCode(Long appId, String message, User loginUser);

    /**
     * 获取应用封装类
     *
     * @param app
     * @return
     */
    AppVO getAppVO(App app);

    /**
     * 获取应用封装类列表
     *
     * @param appList
     * @return
     */
    List<AppVO> getAppVOList(List<App> appList);

    /**
     * 构造应用查询条件
     *
     * @param appQueryRequest
     * @return
     */
    public QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);


}
