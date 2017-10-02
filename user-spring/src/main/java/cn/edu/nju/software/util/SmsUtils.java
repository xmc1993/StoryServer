package cn.edu.nju.software.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.aliyun.openservices.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
* @author zs1996 E-mail:806949096@qq.com
* @version 创建时间：2017年10月1日 
*/
//用于根据电话号码发送短信的工具类
public class SmsUtils {
	
	/**
	 * 获取IAcsClient对象
	 * 
	 * @return
	 * @throws ClientException
	 * @throws com.aliyuncs.exceptions.ClientException 
	 */
	private static IAcsClient initClient() throws com.aliyuncs.exceptions.ClientException {
	    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	    // 初始化ascClient需要的几个参数
	    final String product = "Dysmsapi";// 短信API产品名称
	    final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名
	    // 秘钥key和secret
	    final String appkey = "LTAIQLXhrN5gIJlk";
	    final String appSecret = "L7oz7G51B1o3mh7jTsXsI1oDoGxAuI";
	    // 初始化ascClient,暂时不支持多region
	    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", appkey, appSecret);
	    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
	    IAcsClient acsClient = new DefaultAcsClient(profile);
	    return acsClient;
	}

	
	/**
	 * 获取SMS_72780019短信模板对应的请求
	 * 
	 * @return
	 */
	private static SendSmsRequest getVerifyCode(String phoneNumber,String code) {
	     //组装请求对象
	     SendSmsRequest request = new SendSmsRequest();
	     request.setMethod(MethodType.POST);
	     //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
	     request.setPhoneNumbers(phoneNumber);
	     //必填:短信签名-可在短信控制台中找到
	     request.setSignName("读个小故事");
	     //必填:短信模板-可在短信控制台中找到
	     request.setTemplateCode("SMS_101110009");
	     //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
	     JSONObject jsonObject=new JSONObject();
	     try {
			jsonObject.put("code", code);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     request.setTemplateParam(jsonObject.toString());
	     //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
	     request.setOutId("yourOutId");
		return request;
	 }
	
	/**
	 * @param templateCode 短信模板code
	 * @throws ClientException
	 * @throws com.aliyuncs.exceptions.ClientException 
	 * @throws ServerException 
	 * 
	 */
	public static SendSmsResponse sendMessage(String templateCode,String phoneNumber,String code) throws ServerException, com.aliyuncs.exceptions.ClientException {
	    // 初始化client对象
	    IAcsClient client = initClient();
	    // 短信模板请求对象
	    SendSmsRequest request = null;
	    // 根据短信模板code来获取不同的短信模板请求对象
	    switch(templateCode)
	    {
	        case "SMS_101110009": 
	            request = getVerifyCode(phoneNumber,code);
	            break;
	    }
	    // 发送短信
	    SendSmsResponse response = client.getAcsResponse(request);
	    return response;
	}
}

