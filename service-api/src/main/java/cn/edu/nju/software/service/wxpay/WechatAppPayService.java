package cn.edu.nju.software.service.wxpay;


import cn.edu.nju.software.service.wxpay.cfg.AppPayConf;
import cn.edu.nju.software.service.wxpay.cfg.HttpConf;
import cn.edu.nju.software.service.wxpay.client.dto.WechatAppPayRequest;
import cn.edu.nju.software.service.wxpay.dto.*;
import cn.edu.nju.software.service.wxpay.exception.MalformedPduException;
import cn.edu.nju.software.service.wxpay.exception.WechatAppPayServiceException;

import java.io.Closeable;

/**
 * 微信App支付服务入口
 *
 * @author leon
 */
public interface WechatAppPayService{
    /**
     * 提供配置以初始化服务
     * @param conf 微信应用配置
     * @param httpConf Http配置信息
     */
//	public void init(AppPayConf conf, HttpConf httpConf);

    /**
     * 调用统一下单接口，因为异常情况无签名返回，由应用负责在收到正确响应时去验证签名
     *
     * @param order
     * @return
     * @throws WechatAppPayServiceException
     */
    public UnifiedOrderResponse unifiedOrder(UnifiedOrder order, String key) throws WechatAppPayServiceException;

    /**
     * 将收到的异步通知xml转成Java对象以供业务处理
     *
     * @param xml
     * @return
     * @throws MalformedPduException
     */
    public PaymentNotification parsePaymentNotificationXml(String xml, String key) throws MalformedPduException;

    /**
     * 订单查询，因为异常情况无签名返回，由应用负责在收到正确响应时去验证签名
     *
     * @param orderQuery
     * @return
     * @throws WechatAppPayServiceException
     */
    public OrderQueryResponse queryOrder(OrderQuery orderQuery, String key) throws WechatAppPayServiceException;

    /**
     * 生成客户端支付请求，需要提供调用统一下单接口后收到的prepay_id
     *
     * @param prepayId 调用统一下单接口后收到的prepay_id
     * @return
     * @throws WechatAppPayServiceException
     */
    public WechatAppPayRequest createPayRequestForClient(String prepayId, String appId, String mchId, String key);
}
