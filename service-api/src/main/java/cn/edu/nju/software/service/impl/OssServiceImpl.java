package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.service.OssService;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class OssServiceImpl implements OssService {
    private static Logger logger = LoggerFactory.getLogger(OssServiceImpl.class);
    private final static String host = "http://warmtale-backend-bucket.oss-cn-beijing.aliyuncs.com";
    private final static String accessId = "LTAIQLXhrN5gIJlk";

    @Autowired
    private OSSClient client;

    @Override
    public Map<String, String> getToken() {
        try {
            String dir = "backend";
            long gapTime = 30L;
            long expireEndTime = System.currentTimeMillis();
            expireEndTime += gapTime * 1000L;
            Date expiration = new Date(expireEndTime);
            //表单限制
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 500000000);
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            //生成表单签名
            String postPolicy = client.generatePostPolicy(expiration, policyConditions);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("host", host);
            respMap.put("dir", dir);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            return respMap;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     *
     */
    // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-beijing";
    public static final String STS_API_VERSION = "2015-04-01";

    private AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret, String roleArn,
                                          String roleSessionName, String policy, ProtocolType protocolType, long durationSeconds) throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);

            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            request.setDurationSeconds(durationSeconds);

            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);

            return response;
        } catch (ClientException e) {
            throw e;
        }
    }

    public static String ReadJson(String path) {
        //从给定位置获取文件
        File file = new File(path);
        BufferedReader reader = null;
        //返回值,使用StringBuffer
        StringBuffer data = new StringBuffer();
        //
        try {
            reader = new BufferedReader(new FileReader(file));
            //每次读取文件的缓存
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                data.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }

    public Map<String, String> getAppToken() {
        String webAppReourcePath = OssServiceImpl.class.getResource("/").getPath();
//        String path = OssServiceImpl.class.getClassLoader().getResource("oss/config.json").getPath();
        String path = webAppReourcePath + "oss/config.json";
        String data = ReadJson(path);
        System.out.println(data);
        JSONObject jsonObj = JSONObject.fromObject(data);

        // 只有 RAM用户（子账号）才能调用 AssumeRole 接口
        // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
        // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
        String accessKeyId = jsonObj.getString("AccessKeyID");
        String accessKeySecret = jsonObj.getString("AccessKeySecret");

        // RoleArn 需要在 RAM 控制台上获取
        String roleArn = jsonObj.getString("RoleArn");
        long durationSeconds = jsonObj.getLong("TokenExpireTime");
        String policyPath = webAppReourcePath + jsonObj.getString("PolicyFile");
        String policy = ReadJson(policyPath);
        // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
        // 具体规则请参考API文档中的格式要求
        String roleSessionName = "alice-001";

        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;

        try {
            final AssumeRoleResponse stsResponse = assumeRole(accessKeyId, accessKeySecret, roleArn, roleSessionName,
                    policy, protocolType, durationSeconds);
            Map<String, String> respMap = new LinkedHashMap<>();
            respMap.put("StatusCode", "200");
            respMap.put("AccessKeyId", stsResponse.getCredentials().getAccessKeyId());
            respMap.put("AccessKeySecret", stsResponse.getCredentials().getAccessKeySecret());
            respMap.put("SecurityToken", stsResponse.getCredentials().getSecurityToken());
            respMap.put("Expiration", stsResponse.getCredentials().getExpiration());
            return respMap;
        } catch (ClientException e) {
            Map<String, String> respMap = new LinkedHashMap<>();
            respMap.put("StatusCode", "500");
            respMap.put("ErrorCode", e.getErrCode());
            respMap.put("ErrorMessage", e.getErrMsg());
            return respMap;
        }
    }
}
