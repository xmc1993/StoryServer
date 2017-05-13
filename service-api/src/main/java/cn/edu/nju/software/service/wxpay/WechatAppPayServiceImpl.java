package cn.edu.nju.software.service.wxpay;

import cn.edu.nju.software.service.wxpay.client.dto.WechatAppPayRequest;
import cn.edu.nju.software.service.wxpay.dto.*;
import cn.edu.nju.software.service.wxpay.exception.MalformedPduException;
import cn.edu.nju.software.service.wxpay.exception.WechatAppPayServiceException;
import cn.edu.nju.software.service.wxpay.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WechatAppPayServiceImpl extends WechatHttpCapableClient implements WechatAppPayService {
    private static Logger logger = LoggerFactory.getLogger(WechatAppPayServiceImpl.class);
    private static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    private static String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";


    @Override
    public UnifiedOrderResponse unifiedOrder(UnifiedOrder order, String key) throws WechatAppPayServiceException {
        String responseText = null;
        try {
            // 序列化成xml
            String xml = WechatAppPayProtocolHandler.marshalToXml(order, key);
            logger.debug("Sending to wechat for unified order: " + xml);
            // 发到服务器并收回XML响应
            responseText = sendHttpPostAndReturnString(UNIFIED_ORDER_URL, xml);
            if (responseText == null) {
                throw new WechatAppPayServiceException(
                        "Unified order API to Weixin failed! Received empty response. Order Info: "
                                + order.toString());
            }
            // 反序列化为Java对象并返回
            UnifiedOrderResponse unifiedOrderResponse = WechatAppPayProtocolHandler.unmarshalFromXml(
                    responseText, UnifiedOrderResponse.class);
            logger.debug("Response from WX is {}", unifiedOrderResponse);
            return unifiedOrderResponse;

        } catch (IOException e) {
            logger.warn("Failed to call Weixin for order {} ", order, e);
            throw new WechatAppPayServiceException("Unified order API to Weixin failed! Order Info: "
                    + order.toString(), e);
        } catch (MalformedPduException e) {
            logger.warn("Failed to unmarshall the order response {} ", responseText, e);
            throw new WechatAppPayServiceException("Failed to unmarshall the order response: "
                    + responseText + " for the order: " + order.toString(), e);
        }
    }

    @Override
    public PaymentNotification parsePaymentNotificationXml(String xml, String key) throws MalformedPduException {
        return WechatAppPayProtocolHandler.unmarshalFromXmlAndValidateSignature(xml,
                PaymentNotification.class, key);
    }

    @Override
    public WechatAppPayRequest createPayRequestForClient(String prepayId, String appId, String mchId, String key) {
        WechatAppPayRequest request = new WechatAppPayRequest();
        request.setAppid(appId);
        request.setAppPackage("Sign=WXPay");
        request.setPartnerid(mchId);
        request.setPrepayid(prepayId);
        request.setTimestamp(Long.toString(System.currentTimeMillis() / 1000));
        request.setNoncestr(Util.generateString(32));
        request.setSign(Util.validateFieldsAndGenerateWxSignature(request, key));
        return request;
    }

    @Override
    public OrderQueryResponse queryOrder(OrderQuery orderQuery, String key) throws WechatAppPayServiceException {
        String responseText = null;
        try {
            // 序列化成xml
            String xml = WechatAppPayProtocolHandler.marshalToXml(orderQuery, key);
            logger.debug("Sending to wechat for order query: " + xml);
            // 发到服务器并收回XML响应
            responseText = sendHttpPostAndReturnString(ORDER_QUERY_URL, xml);
            if (responseText == null) {
                throw new WechatAppPayServiceException(
                        "Order Query API to Weixin failed! Received empty response. OrderQuery Info: "
                                + orderQuery.toString());
            }
            // 反序列化为Java对象并返回
            OrderQueryResponse orderQueryResponse = WechatAppPayProtocolHandler.unmarshalFromXml(
                    responseText, OrderQueryResponse.class);
            logger.debug("Response from WX for order query is {}", orderQueryResponse);
            return orderQueryResponse;
        } catch (IOException e) {
            logger.warn("Failed to call Weixin for order {} ", orderQuery, e);
            throw new WechatAppPayServiceException("Order Query API to Weixin failed! Query Info: "
                    + orderQuery.toString(), e);
        } catch (MalformedPduException e) {
            logger.warn("Failed to unmarshall the order query response {} ", responseText, e);
            throw new WechatAppPayServiceException("Failed to unmarshall the order query response: "
                    + responseText + " for querying the order: " + orderQuery.toString(), e);
        }
    }
}
