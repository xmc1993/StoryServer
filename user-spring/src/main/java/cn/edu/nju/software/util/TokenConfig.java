package cn.edu.nju.software.util;

import cn.edu.nju.software.filter.AccessTokenValidationInterceptor;

/**
 * <p>与token相关的配置信息。
 * 
 * <p>目前包含有3种token：
 * <ul>
 *  <li>access token: 用户访问api时使用的token</li>
 *  <li>upload token: 用户上传文件时使用的token</li>
 * </ul>
 *
 * @author caoxudong
 * @since 0.1.0
 */
public class TokenConfig {

  /**
   * 默认的访问Token的HTTP请求头的名字
   */
  public static final String DEFAULT_ACCESS_TOKEN_HEADER_NAME = "X-Access-Token";
  /**
   * 默认的访问token的有效期，默认为15天
   */
  public static final long DEFAULT_ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 15;
  /**
   * 在校验身份通过后，会设置请求属性，记录用户id。该属性定义了默认的请求属性名
   */
  public static final String DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME = "userId";
  /**
   * 默认的上传Token的HTTP请求头的名字
   */
  public static final String DEFAULT_UPLOAD_TOKEN_HEADER_NAME = "X-Upload-Token";
  /**
   * 默认的上传token的有效期，默认为5分钟
   */
  public static final long DEFAULT_UPLOAD_TOKEN_EXPIRATION = 300;
  
  /**
   * 在通过{@link AccessTokenValidationInterceptor}校验后，会在指定的request属性中设置用户的主键id。
   * 属性名，由该字段指定。
   */
  private static String userIdRequestAttributeName = DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME;
  
  public static final String DEFAULT_BUSINESS_REQUEST_ATTRIBUTE_NAME = "businessId";
  

}
