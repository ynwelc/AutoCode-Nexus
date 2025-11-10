package com.ynwe.aicodezero.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.ynwe.aicodezero.model.dto.chathistory.ChatHistoryQueryRequest;
import com.ynwe.aicodezero.model.entity.ChatHistory;
import com.ynwe.aicodezero.model.entity.User;

import java.time.LocalDateTime;

/**
 * 对话历史 服务层。
 *
 * @author <a href="https://github.com/ynwelc">hhhhh</a>
 */
public interface ChatHistoryService extends IService<ChatHistory> {

    /**
     * 分页查询某APP的对话记录
     *
     * @param appId          应用ID
     * @param pageSize       每页大小
     * @param lastCreateTime 最后创建时间
     * @param loginUser      登录用户
     * @return 对话历史列表
     */
    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);

    /**
     * 添加对话消息
     *
     * @param appId       应用ID
     * @param message     消息内容
     * @param messageType 消息类型
     * @param userId      用户ID
     * @return 是否添加成功
     */
    boolean addChatMessage(Long appId, String message, String messageType, Long userId);

    /**
     * 根据应用ID删除对话消息
     *
     * @param appId 应用ID
     * @return 是否删除成功
     */
    boolean deleteByAppId(Long appId);

    /**
     * 构造查询条件
     *
     * @param chatHistoryQueryRequest 查询参数
     * @return 查询结构，历史对话信息
     */
    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);
}
