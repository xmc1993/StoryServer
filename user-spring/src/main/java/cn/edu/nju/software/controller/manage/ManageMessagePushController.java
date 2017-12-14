package cn.edu.nju.software.controller.manage;

import cn.edu.nju.software.dto.DestinationVo;
import cn.edu.nju.software.entity.Destination;
import cn.edu.nju.software.entity.MessagePush;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.service.MessagePushService;
import cn.edu.nju.software.util.AndroidPush.AndroidBroadcast;
import cn.edu.nju.software.util.AndroidPush.AndroidNotification;
import cn.edu.nju.software.util.AndroidPush.PushClient;
import cn.edu.nju.software.util.MessagePushUtil;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangsong on 2017/12/4.
 */

@Api(value = "Admin", description = "管理接口")
@Controller
@RequestMapping("/manage")
public class ManageMessagePushController {
    @Autowired
    MessagePushService messagePushService;

    @ApiOperation(value = "分页获取所有的消息推送记录", notes = "")
    @RequestMapping(value = "/getAllMessagePush", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<MessagePush>> getAllMessagePush(@ApiParam("page") @RequestParam Integer page,
                                                             @ApiParam("pageSize") @RequestParam Integer pageSize) {
        ResponseData<List<MessagePush>> responseData = messagePushService.getAllMessagePush(page, pageSize);
        return responseData;
    }

    @ApiOperation(value = "分页获取所有的跳转地", notes = "")
    @RequestMapping(value = "/getAllDestination", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<Destination>> getAllDestination(@ApiParam("page") @RequestParam Integer page,
                                                             @ApiParam("pageSize") @RequestParam Integer pageSize) {
        ResponseData<List<Destination>> responseData = messagePushService.getAllDestination(page, pageSize);
        return responseData;
    }

    @ApiOperation(value = "新建消息推送", notes = "")
    @RequestMapping(value = "/saveMessagePush", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> saveMessagePush(@ApiParam("通知栏提示文字") @RequestParam String ticker,
                                                 @ApiParam("通知标题") @RequestParam String title,
                                                 @ApiParam("通知文字描述") @RequestParam String text,
                                                 @ApiParam("跳转地id") @RequestParam int destinationId,
                                                 @ApiParam("定时发送时间（可选，如为空则表示立刻发送）(时间格式：YYYY-MM-DD HH:mm:ss)") @RequestParam(value = "startTime", required = false) String startTime,
                                                 @ApiParam("推送的过期时间") @RequestParam String expireTime,
                                                 @ApiParam("推送的类型") @RequestParam Integer pushType,
                                                 @ApiParam("额外的参数") @RequestParam(value ="extraField",required = false) String extraField)throws ParseException {
        ResponseData<Boolean> responseData = new ResponseData<>();
        MessagePush messagePush = new MessagePush();
        messagePush.setTicker(ticker);
        messagePush.setDestinationid(destinationId);
        messagePush.setTitle(title);
        messagePush.setText(text);
        messagePush.setCreatetime(new Date());
        if (extraField!=null)
            messagePush.setExtrafield(extraField);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagePush.setExpiretime(dateFormat.parse(expireTime));
        messagePush.setPushtype(pushType);
        if (startTime != null) {
            messagePush.setStarttime(dateFormat.parse(startTime));
        }
        int res = messagePushService.saveMessagePush(messagePush);
        if (res == 1) {
            responseData.jsonFill(1, null, true);
            return responseData;
        }
        responseData.jsonFill(2, "保存失败", null);
        return responseData;
    }

    @ApiOperation(value = "删除推送消息记录", notes = "")
    @RequestMapping(value = "/deleteMessagePush/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseData<Boolean> deleteMessagePush(@ApiParam("推送消息Id") @PathVariable Integer id
    ) {
        ResponseData<Boolean> responseData = new ResponseData<>();

        int res = messagePushService.deleteMessagePush(id);
        if (res == 1) {
            responseData.jsonFill(1, null, true);
            return responseData;
        }
        responseData.jsonFill(2, "删除失败", null);
        return responseData;
    }

    @ApiOperation(value = "添加跳转目的地", notes = "")
    @RequestMapping(value = "/saveDestination", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> saveDestination(@ApiParam("跳转地内容") @RequestParam(value = "content", required = false) String content,
                                                 @ApiParam("跳转类型") @RequestParam Integer destinationType,
                                                 @ApiParam("描述内容") @RequestParam String description
    ) {
        ResponseData<Boolean> responseData = new ResponseData<>();
        Destination destination = new Destination();
        destination.setCreatetime(new Date());
        destination.setUpdatetime(new Date());
        //1表示跳转到APP
        //2表示跳转到APP固定的activity
        //3表示跳转到一个URL里面
        switch (destinationType) {
            case 1:
                destination.setDescription(description);
                destination.setDestinationtype(destinationType);
                break;
            case 2:
            case 3:
                if (content == null) {
                    responseData.jsonFill(2, "该跳转类型需要跳转内容", null);
                    return responseData;
                }
                destination.setDestinationtype(destinationType);
                destination.setContent(content);
                destination.setDescription(description);
                break;
        }

        int res = messagePushService.saveDestination(destination);
        if (res == 1) {
            responseData.jsonFill(1, null, true);
            return responseData;
        }
        responseData.jsonFill(2, "保存失败", null);
        return responseData;
    }

    @ApiOperation(value = "删除跳转目的地", notes = "")
    @RequestMapping(value = "/deleteDestination/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseData<Boolean> deleteDestination(@ApiParam("跳转地Id") @PathVariable Integer id
    ) {
        ResponseData<Boolean> responseData = new ResponseData<>();

        int res = messagePushService.deleteDestination(id);
        if (res == 1) {
            responseData.jsonFill(1, null, true);
            return responseData;
        }
        responseData.jsonFill(2, "删除失败", null);
        return responseData;
    }

    @ApiOperation(value = "获取所有跳转地描述", notes = "")
    @RequestMapping(value = "/getAllDestinationDescription", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<DestinationVo>> getAllDestinationDescription() {
        ResponseData<List<DestinationVo>> responseData = new ResponseData<>();

        List<DestinationVo> list = messagePushService.getAllDestinationDescription();
        responseData.jsonFill(1, null, list);
        return responseData;
    }

    @ApiOperation(value = "发送推送", notes = "")
    @RequestMapping(value = "/messagePush", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> messagePush(@ApiParam("推送消息的id") @RequestParam Integer id) throws Exception {
        ResponseData<Boolean> responseData = new ResponseData<>();
        MessagePush messagePush = messagePushService.getMessagePushById(id);

        Destination destination = messagePushService.getDestinationById(messagePush.getDestinationid());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        switch (messagePush.getPushtype()) {
            //1表示广播
            case 1:
                AndroidBroadcast androidBroadcast = MessagePushUtil.getAndroidBroadcast();
                androidBroadcast.setTicker(messagePush.getTicker());
                androidBroadcast.setTitle(messagePush.getTitle());
                androidBroadcast.setText(messagePush.getText());
                androidBroadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
                switch (destination.getDestinationtype()) {
                    //1表示直接打开APP
                    case 1:
                        androidBroadcast.goAppAfterOpen();
                        break;
                    //2表示跳转到app里面activity
                    case 2:
                        androidBroadcast.goActivityAfterOpen(destination.getContent());
                        if(messagePush.getExtrafield()!=null){
                            Map maps=  JSON.parseObject(messagePush.getExtrafield(),Map.class);
                            for (Object map : maps.entrySet()){
                                androidBroadcast.setExtraField((String) map,(String) maps.get(map));
                            }
                        }
                        break;
                    case 3:
                        androidBroadcast.goUrlAfterOpen(destination.getContent());
                        break;
                }
                // TODO Set 'production_mode' to 'false' if it's a test device.
                androidBroadcast.setProductionMode();
                androidBroadcast.setExpireTime(dateFormat.format(messagePush.getExpiretime()));
                if (messagePush.getStarttime() != null) {
                    androidBroadcast.setStartTime(dateFormat.format(messagePush.getStarttime()));
                }
                PushClient client = new PushClient();
                Boolean res = client.send(androidBroadcast);
                if (res) {
                    responseData.jsonFill(1, null, true);
                    return responseData;
                }
                responseData.jsonFill(1, null, false);
                return responseData;
        }
        responseData.jsonFill(2, "该功能还没上线", null);
        return responseData;
    }
}
