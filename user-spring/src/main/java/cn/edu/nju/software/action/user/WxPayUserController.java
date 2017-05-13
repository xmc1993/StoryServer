package cn.edu.nju.software.action.user;

import cn.edu.nju.software.action.BaseController;
import cn.edu.nju.software.entity.Business;
import cn.edu.nju.software.entity.ResponseData;
import cn.edu.nju.software.entity.User;
import cn.edu.nju.software.service.user.AppUserService;
import cn.edu.nju.software.service.user.UserBusinessService;
import cn.edu.nju.software.service.wxpay.WechatAppPayServiceImpl;
import cn.edu.nju.software.service.wxpay.dto.*;
import cn.edu.nju.software.service.wxpay.exception.MalformedPduException;
import cn.edu.nju.software.service.wxpay.exception.WechatAppPayServiceException;
import cn.edu.nju.software.service.wxpay.util.RandCharsUtils;
import cn.edu.nju.software.service.wxpay.util.Util;
import cn.edu.nju.software.service.wxpay.util.WXSignUtils;
import cn.edu.nju.software.vo.HttpURLData;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by xmc1993 on 2017/4/21.
 */
public class WxPayUserController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(WxPayUserController.class);
    private final String SUCCESS = "SUCCESS";
    private final String FAIL = "FAIL";
    @Autowired
    private WechatAppPayServiceImpl wechatAppPayService;
    @Autowired
    private UserBusinessService businessService;
    @Autowired
    private AppUserService userService;
    @Autowired
    private HttpURLData httpURLData;

    /**
     * @api {get} /user/unifiedOrder 统一下单
     * @apiName UnifiedOrder
     * @apiGroup User|WxPay
     * @apiVersion 0.0.1
     * @apiParam {String} appId AppID
     * @apiParam {String} mobile mobile
     * @apiParam {String} body
     * @apiParam {String} detail
     * @apiParam {String} attach
     * @apiParam {String} out_trade_no
     * @apiParam {String} total_fee
     * @apiParam {String} spbill_create_ip
     * @apiParam {String} goods_tag
     * @apiParam {String} product_id
     * @apiSuccess (成功) {UnifiedOrderResponse} obj 订单信息
     * @apiError (200错误) IncorrectAppId appId不正确
     * @apiError (200错误) VideoNotExists 不存在该视频
     */

    @ApiOperation(value = "统一下单", notes = "统一下单")
    @RequestMapping(value = "/user/unifiedOrder", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData<Object> unifiedOrder(@ApiParam("AppId") @RequestParam(value = "appId") String appId, @ApiParam("Mobile") @RequestParam(value = "mobile") String mobile,
                                             @ApiParam("商品描述") @RequestParam(value = "body") String body, @ApiParam("商品详情详情参见微信支付官方文档") @RequestParam(value = "detail") String detail,
                                             @ApiParam("附加数据") @RequestParam(value = "attach") String attach, @ApiParam("商户订单号") @RequestParam(value = "out_trade_no") String out_trade_no,
                                             @ApiParam("总金额单位为【分】") @RequestParam(value = "total_fee") int total_fee, @ApiParam("spbill_create_ip") @RequestParam(value = "spbill_create_ip") String spbill_create_ip,
                                             @ApiParam("商品标记") @RequestParam(value = "goods_tag") String goods_tag, @ApiParam("支付类型") @RequestParam(value = "fee_type") String fee_type,
                                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseData responseData = new ResponseData();
        //获取business
        Business business = businessService.getBusinessByAppId(appId);
        if (business == null) {
            responseData.setErrorMes("不存在该客户");
            responseData.setStatus(2);
            return responseData;
        }

        UnifiedOrder order = new UnifiedOrder();
        order.setBody(body);
        order.setDetail(detail);
        order.setAttach(attach);
        order.setOut_trade_no(out_trade_no);
        order.setTotal_fee(total_fee);
        order.setSpbill_create_ip(spbill_create_ip);
        order.setGoods_tag(goods_tag);
        order.setFee_type(fee_type);
        order.setAppid(business.getWeChatAppId())
                .setMch_id(business.getMchId())
                .setDevice_info("WEB");//终端设备号(门店号或收银设备ID)，默认请传"WEB"
        order.setNonce_str(RandCharsUtils.getRandomString(16));//随机字符串，不长于32位。推荐随机数生成算法
        order.setTime_start(RandCharsUtils.timeStart());//交易开始时间
        order.setTime_expire(RandCharsUtils.timeExpire());//必须大于5分钟 此处默认为30分钟
        order.setLimit_pay("no_credit");

        //根据AppId以及HttpUrlData生成相应的回调地址
        String curAppId = business.getAppId();
        String notifyUrl = httpURLData.getXinzhuUrl() + "/user/" + curAppId.substring(appId.lastIndexOf('.') + 1) + "Notify";
        order.setNotify_url(notifyUrl);

        UnifiedOrderResponse unifiedOrderResponse;
        try {
            unifiedOrderResponse = wechatAppPayService.unifiedOrder(order, business.getMchKey());
        } catch (WechatAppPayServiceException e) {
            e.printStackTrace();
            responseData.jsonFill(2, "访问微信下单接口失败", null);
            return responseData;
        }

        responseData.jsonFill(1, null, unifiedOrderResponse);

        return responseData;
    }


    /**
     * @api {get} /user/queryOrder 查询订单
     * @apiName QueryOrder
     * @apiGroup User|WxPay
     * @apiVersion 0.0.1
     * @apiParam {String} appId AppID
     * @apiParam {String} mobile mobile
     * @apiParam {String} transaction_id 微信的订单号
     * @apiParam {String} [out_trade_no] 商户内部订单号(可选)和transaction_id二选一
     * @apiSuccess (成功) {UnifiedOrderResponse} obj 订单信息
     * @apiError (200错误) IncorrectAppId appId不正确
     * @apiError (200错误) VideoNotExists 不存在该视频
     */
    @ApiOperation(value = "查询订单", notes = "查询订单")
    @RequestMapping(value = "/user/queryOrder", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData<Object> queryOrder(@ApiParam("AppId") @RequestParam(value = "appId") String appId,
                                           @ApiParam("mobile") @RequestParam("mobile") String mobile, @ApiParam("微信的订单号") @RequestParam("transaction_id") String transaction_id,
                                           @ApiParam("商户内部订单号(可选)和transaction_id二选一") @RequestParam(value = "out_trade_no", required = false) String out_trade_no,
                                           HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        ResponseData responseData = new ResponseData();
        //获取business
        Business business = businessService.getBusinessByAppId((String) appId);
        if (business == null) {
            responseData.setErrorMes("不存在该客户");
            responseData.setStatus(2);
            return responseData;
        }
        //TODO 是否要删除
        User user = userService.getUserByMobileOrId(mobile);
        if (user == null) {
            responseData.setErrorMes("不存在该用户");
            responseData.setStatus(2);
            return responseData;
        }


        OrderQuery oq = new OrderQuery();
        //TODO init oq
        oq.setAppid(business.getWeChatAppId()).setMch_id(business.getMchId()).setNonce_str(RandCharsUtils.getRandomString(16)).setTransaction_id(transaction_id)
                .setOut_trade_no(out_trade_no);


        OrderQueryResponse orderQueryRespone;
        try {
            orderQueryRespone = wechatAppPayService.queryOrder(oq, business.getMchKey());
        } catch (WechatAppPayServiceException e) {
            e.printStackTrace();
            responseData.jsonFill(2, "访问微信订单查询接口失败", null);
            return responseData;
        }

        responseData.jsonFill(1, null, orderQueryRespone);
        return responseData;
    }


    @ApiOperation(value = "嘉书汉语的微信支付回调url", notes = "嘉书汉语的微信支付回调url")
    @RequestMapping(value = "/user/hanyuNotify", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void hanyuNotify(
            HttpServletRequest request, HttpServletResponse response) throws IOException, MalformedPduException {
        unionNotify("com.xinzhu.hanyu", request, response);
    }

    @ApiOperation(value = "享佳的微信支付回调url", notes = "享佳的微信支付回调url")
    @RequestMapping(value = "/user/xiangjiaNotify", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void xiangjiaNotify(
            HttpServletRequest request, HttpServletResponse response) throws IOException, MalformedPduException {
        unionNotify("com.xinzhu.xiangjia", request, response);
    }

    @ApiOperation(value = "心筑的微信支付回调url", notes = "心筑的微信支付回调url")
    @RequestMapping(value = "/user/sintureNotify", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void sintureNotify(
            HttpServletRequest request, HttpServletResponse response) throws IOException, MalformedPduException {
        unionNotify("com.xinzhu.sinture", request, response);
    }

    @ApiOperation(value = "时刻的微信支付回调url", notes = "时刻的微信支付回调url")
    @RequestMapping(value = "/user/trainNotify", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void trainNotify(
            HttpServletRequest request, HttpServletResponse response) throws IOException, MalformedPduException {
        unionNotify("com.classeshop.train", request, response);
    }

    private void unionNotify(String appId, HttpServletRequest request, HttpServletResponse response) throws IOException, MalformedPduException {
        Business business = businessService.getBusinessByAppId(appId);
        ServletInputStream inputStream = request.getInputStream();
        StringBuffer result = new StringBuffer("");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            result.append(s);
        }

        String orderInfo = result.toString();
        PaymentNotification paymentNotification = wechatAppPayService.parsePaymentNotificationXml(orderInfo, business.getMchKey());
        String res;

        if (paymentNotification.getReturn_code().equals(SUCCESS)) {
            SortedMap<Object, Object> parameters = new TreeMap();
            parameters.put("appid", paymentNotification.getAppid());
            parameters.put("attach", paymentNotification.getAttach());
            parameters.put("bank_type", paymentNotification.getBank_type());
            parameters.put("cash_fee", paymentNotification.getCash_fee());
            parameters.put("fee_type", paymentNotification.getFee_type());
            parameters.put("is_subscribe", paymentNotification.getIs_subscribe());
            parameters.put("mch_id", paymentNotification.getMch_id());
            parameters.put("nonce_str", paymentNotification.getNonce_str());
            parameters.put("openid", paymentNotification.getOpenid());
            parameters.put("out_trade_no", paymentNotification.getOut_trade_no());
            parameters.put("result_code", paymentNotification.getResult_code());
            parameters.put("return_code", paymentNotification.getReturn_code());
            parameters.put("time_end", paymentNotification.getTime_end());
            parameters.put("total_fee", paymentNotification.getTotal_fee());
            parameters.put("trade_type", paymentNotification.getTrade_type());

            String sign = WXSignUtils.createSign("UTF-8", parameters, business.getMchKey());
            if (sign.equals(paymentNotification.getSign())) {
                //支付成功
                res = Util.generateResult(SUCCESS, "OK");
            } else {
                //校验失败
                res = Util.generateResult(FAIL, "校验失败。");
            }

        } else

        {
            logger.error("微信支付失败");
            logger.error(paymentNotification.getErr_code());
            logger.error(paymentNotification.getErr_code_des());
            logger.error(paymentNotification.getReturn_msg());
            //支付失败
            res = Util.generateResult(FAIL, paymentNotification.getReturn_msg());
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        out.write(res);
        out.flush();
        out.close();
    }
}
