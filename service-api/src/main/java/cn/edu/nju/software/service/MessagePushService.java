package cn.edu.nju.software.service;

import cn.edu.nju.software.dto.DestinationVo;
import cn.edu.nju.software.entity.Destination;
import cn.edu.nju.software.entity.MessagePush;
import cn.edu.nju.software.entity.ResponseData;

import java.util.List;

/**
 * Created by zhangsong on 2017/12/5.
 */
public interface MessagePushService {
    int saveMessagePush(MessagePush messagePush);
    int deleteMessagePush(int id);
    MessagePush getMessagePushById(Integer id);
    ResponseData getAllMessagePush(int page,int pageSize);
    int saveDestination(Destination destination);
    int deleteDestination(int id);
    ResponseData getAllDestination(int page,int pageSize);
    Destination getDestinationById(Integer id);
    List<DestinationVo> getAllDestinationDescription();
}
