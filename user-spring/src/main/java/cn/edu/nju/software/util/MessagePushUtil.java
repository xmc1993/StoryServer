package cn.edu.nju.software.util;

import cn.edu.nju.software.util.AndroidPush.*;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by zhangsong on 2017/12/4.
 */
public class MessagePushUtil {
    private static AndroidBroadcast androidBroadcast;
    private String appkey = "59c331505312dd4060000031";
    private String appMasterSecret = "5maxiqhmrj13m3suwnhauiq9etoosytu";
    private static PushClient client = new PushClient();



//懒汉模式，不知道会不会出现死锁的问题，埋个坑
    public static AndroidBroadcast getAndroidBroadcast() throws Exception {
        if (androidBroadcast == null)
            androidBroadcast = new AndroidBroadcast("59c331505312dd4060000031", "5maxiqhmrj13m3suwnhauiq9etoosytu");
        return androidBroadcast;
    }



    //广播：给所有人播
    public void sendAndroidBroadcast() throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast(appkey, appMasterSecret);
        broadcast.setTicker("Android broadcast ticker");
        broadcast.setTitle("题目");
        broadcast.setText("内容");
        broadcast.goAppAfterOpen();
        broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        broadcast.setProductionMode();
        // Set customized fields
        broadcast.setExtraField("这个是显示什么呢", "额外字段?");
        client.send(broadcast);
    }

    //单播：向指定用户推送消息
    public void sendAndroidUnicast() throws Exception {
        AndroidUnicast unicast = new AndroidUnicast(appkey, appMasterSecret);
        // TODO Set your device token
        unicast.setDeviceToken("Ajvyr_R-eNcZTjI3SChGL-P6s-RNItGvTFqKtp1vI26D");
        unicast.setTicker("Android unicast ticker");
        unicast.setTitle("中文的title");
        unicast.setText("Android unicast text");
        unicast.goAppAfterOpen();
        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        unicast.setProductionMode();
        // Set customized fields
        unicast.setExtraField("test", "helloworld");
        client.send(unicast);
    }

    //组播：向特定版本或者特定区域什么的发送推送消息
    public void sendAndroidGroupcast() throws Exception {
        AndroidGroupcast groupcast = new AndroidGroupcast(appkey, appMasterSecret);
        /*  TODO
		 *  Construct the filter condition:
		 *  "where":
		 *	{
    	 *		"and":
    	 *		[
      	 *			{"tag":"test"},
      	 *			{"tag":"Test"}
    	 *		]
		 *	}
		 */
        JSONObject filterJson = new JSONObject();
        JSONObject whereJson = new JSONObject();
        JSONArray tagArray = new JSONArray();
        JSONObject testTag = new JSONObject();
        JSONObject TestTag = new JSONObject();
        testTag.put("tag", "test");
        TestTag.put("tag", "Test");
        tagArray.put(testTag);
        tagArray.put(TestTag);
        whereJson.put("and", tagArray);
        filterJson.put("where", whereJson);
        System.out.println(filterJson.toString());

        groupcast.setFilter(filterJson);
        groupcast.setTicker("Android groupcast ticker");
        groupcast.setTitle("中文的title");
        groupcast.setText("Android groupcast text");
        groupcast.goAppAfterOpen();
        groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        groupcast.setProductionMode();
        client.send(groupcast);
    }

    //自定义播
    public void sendAndroidCustomizedcast() throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
        // TODO Set your alias here, and use comma to split them if there are multiple alias.
        // And if you have many alias, you can also upload a file containing these alias, then
        // use file_id to send customized notification.
        customizedcast.setAlias("alias", "alias_type");
        customizedcast.setTicker("Android customizedcast ticker");
        customizedcast.setTitle("中文的title");
        customizedcast.setText("Android customizedcast text");
        customizedcast.goAppAfterOpen();
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        customizedcast.setProductionMode();
        client.send(customizedcast);
    }

    //自定义文件播：向用户发送文件
    public void sendAndroidCustomizedcastFile() throws Exception {
        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey, appMasterSecret);
        // TODO Set your alias here, and use comma to split them if there are multiple alias.
        // And if you have many alias, you can also upload a file containing these alias, then
        // use file_id to send customized notification.
        String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb" + "\n" + "alias");
        customizedcast.setFileId(fileId, "alias_type");
        customizedcast.setTicker("Android customizedcast ticker");
        customizedcast.setTitle("中文的title");
        customizedcast.setText("Android customizedcast text");
        customizedcast.goAppAfterOpen();
        customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        // TODO Set 'production_mode' to 'false' if it's a test device.
        // For how to register a test device, please see the developer doc.
        customizedcast.setProductionMode();
        client.send(customizedcast);
    }

    //文件播：文件播
    public void sendAndroidFilecast() throws Exception {
        AndroidFilecast filecast = new AndroidFilecast(appkey, appMasterSecret);
        // TODO upload your device tokens, and use '\n' to split them if there are multiple tokens
        String fileId = client.uploadContents(appkey, appMasterSecret, "aa" + "\n" + "bb");
        filecast.setFileId(fileId);
        filecast.setTicker("Android filecast ticker");
        filecast.setTitle("中文的title");
        filecast.setText("Android filecast text");
        filecast.goAppAfterOpen();
        filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        client.send(filecast);
    }


    //测试

    public static void main(String[] args) {
        // TODO set your appkey and master secret here
        try {
            AndroidBroadcast broadcast=MessagePushUtil.getAndroidBroadcast();
            broadcast.setTicker("Android broadcast ticker");
            broadcast.setTitle("题目");
            broadcast.setText("内容");
            broadcast.goAppAfterOpen();
            broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
            // TODO Set 'production_mode' to 'false' if it's a test device.
            // For how to register a test device, please see the developer doc.
            broadcast.setProductionMode();
            // Set customized fields
            broadcast.setExtraField("这个是显示什么呢", "额外字段?");
            client.send(broadcast);
            //TODO these methods are all available, just fill in some fields and do the test
            /*messagePushUtil.sendAndroidCustomizedcastFile();
            messagePushUtil.sendAndroidBroadcast();
            messagePushUtil.sendAndroidGroupcast();
            messagePushUtil.sendAndroidCustomizedcast();
            messagePushUtil.sendAndroidFilecast();*/
            //messagePushUtil.sendAndroidBroadcast();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
