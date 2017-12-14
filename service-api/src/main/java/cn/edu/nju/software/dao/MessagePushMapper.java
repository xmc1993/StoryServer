package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.MessagePush;
import cn.edu.nju.software.entity.MessagePushExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagePushMapper {
    int countByExample(MessagePushExample example);

    int deleteByExample(MessagePushExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MessagePush record);

    int insertSelective(MessagePush record);

    List<MessagePush> selectByExample(MessagePushExample example);

    MessagePush selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MessagePush record, @Param("example") MessagePushExample example);

    int updateByExample(@Param("record") MessagePush record, @Param("example") MessagePushExample example);

    int updateByPrimaryKeySelective(MessagePush record);

    int updateByPrimaryKey(MessagePush record);
}