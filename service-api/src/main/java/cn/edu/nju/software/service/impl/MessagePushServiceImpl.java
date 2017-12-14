package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.dao.DestinationMapper;
import cn.edu.nju.software.dao.MessagePushMapper;
import cn.edu.nju.software.dto.DestinationVo;
import cn.edu.nju.software.entity.*;
import cn.edu.nju.software.service.MessagePushService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangsong on 2017/12/5.
 */
@Service
public class MessagePushServiceImpl implements MessagePushService {
    @Autowired
    MessagePushMapper messagePushMapper;
    @Autowired
    DestinationMapper destinationMapper;


    @Override
    public int saveMessagePush(MessagePush messagePush) {
        return messagePushMapper.insert(messagePush);
    }

    @Override
    public int deleteMessagePush(int id) {
        return messagePushMapper.deleteByPrimaryKey(id);
    }

    @Override
    public MessagePush getMessagePushById(Integer id) {
        return messagePushMapper.selectByPrimaryKey(id);
    }

    @Override
    public ResponseData getAllMessagePush(int page, int pageSize) {
        ResponseData<List<MessagePush>> responseData=new ResponseData<>();

        PageHelper.startPage(page+1, pageSize);
        MessagePushExample messagePushExample = new MessagePushExample();
        //通过criteria构造查询条件
        List<MessagePush> list = messagePushMapper.selectByExample(messagePushExample);
        PageInfo<MessagePush> pageInfo = new PageInfo<>(list);
        int count = (int) pageInfo.getTotal();
        responseData.setCount(count);
        responseData.jsonFill(1, null, list);
        return responseData;
    }

    @Override
    public int saveDestination(Destination destination) {
        return destinationMapper.insert(destination);
    }

    @Override
    public int deleteDestination(int id) {
        return destinationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ResponseData getAllDestination(int page, int pageSize) {
        ResponseData<List<Destination>> responseData=new ResponseData<>();
        PageHelper.startPage(page+1,pageSize);
        DestinationExample destinationExample=new DestinationExample();
        List<Destination> list=destinationMapper.selectByExample(destinationExample);
        PageInfo<Destination> pageInfo=new PageInfo<>(list);
        int count=(int)pageInfo.getTotal();
        responseData.jsonFill(1,null,list);
        responseData.setCount(count);
        return responseData;
    }

    @Override
    public Destination getDestinationById(Integer id) {
        return destinationMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DestinationVo> getAllDestinationDescription() {
        return destinationMapper.getAllDestinationDescription();
    }
}
