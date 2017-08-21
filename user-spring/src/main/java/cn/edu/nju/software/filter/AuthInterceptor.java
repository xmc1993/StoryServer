package cn.edu.nju.software.filter;

import cn.edu.nju.software.annotation.RequiredPermissions;
import cn.edu.nju.software.entity.Admin;
import cn.edu.nju.software.entity.OperationLog;
import cn.edu.nju.software.service.OperationLogService;
import cn.edu.nju.software.util.TokenConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class AuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthInterceptor.class);
    private static final int notLogin = 0;
    private static final int notPermitted = 1;
    private static final int hasPermission = 2;

    private static final Map<String, Object> serviceMethodRequiredPermissionsMapping = new HashMap<>();
    private static final Lock serviceMethodRequiredPermissionsLock = new ReentrantLock();
    private static final Map<String, Object> serviceMethodApiOperationInfoMapping = new HashMap<>();
    private static final Lock serviceMethodApiOperationInfoLock = new ReentrantLock();

    @Autowired
    private OperationLogService operationLogService;

    private static String getHandlerMethodSignature(HandlerMethod handlerMethod) {
        StringBuilder builder = new StringBuilder();
        builder.append(handlerMethod.getBeanType().getCanonicalName());
        builder.append(".");
        builder.append(handlerMethod.getMethod().getName());
        builder.append("(");
        for (int i = 0; i < handlerMethod.getMethodParameters().length; ++i) {
            MethodParameter methodParameter = handlerMethod.getMethodParameters()[i];
            if (i > 0) {
                builder.append(",");
            }
            builder.append(methodParameter.getParameterType().getCanonicalName());
        }
        builder.append(")");
        return builder.toString();
    }

    private <T extends Annotation> T getAnnotationFromHandlerMethodWithCache(HandlerMethod handlerMethod, Class<T> annotationCls, Map<String, Object> mapping, Lock lock) {
        // 首先判断此方法是否有@annotationCls,并cache这个元信息避免重新判断
        String signatureStr = getHandlerMethodSignature(handlerMethod);
        Object methodCacheInfoObj;
        methodCacheInfoObj = mapping.get(signatureStr);
        if (methodCacheInfoObj != null) {
            if (annotationCls.isAssignableFrom(methodCacheInfoObj.getClass())) {
                return (T) methodCacheInfoObj;
            } else {
                return null;
            }
        }
        lock.lock();
        try {
            methodCacheInfoObj = mapping.get(signatureStr);
            if (methodCacheInfoObj != null) {
                if (annotationCls.isAssignableFrom(methodCacheInfoObj.getClass())) {
                    return (T) methodCacheInfoObj;
                } else {
                    return null;
                }
            } else {
                T anno = handlerMethod.getMethodAnnotation(annotationCls);
                if (anno == null) {
                    mapping.put(signatureStr, false);
                    return null;
                }
                mapping.put(signatureStr, anno);
                return anno;
            }
        } finally {
            lock.unlock();
        }
    }

    private RequiredPermissions getRequiredPermissionsFromHandlerMethodWithCache(HandlerMethod handlerMethod) {
        return getAnnotationFromHandlerMethodWithCache(handlerMethod, RequiredPermissions.class, serviceMethodRequiredPermissionsMapping, serviceMethodRequiredPermissionsLock);
    }

    private ApiOperation getApiOperationFromHandlerMethodWithCache(HandlerMethod handlerMethod) {
        return getAnnotationFromHandlerMethodWithCache(handlerMethod, ApiOperation.class, serviceMethodApiOperationInfoMapping, serviceMethodApiOperationInfoLock);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //TODO 检查权限的入口
        int rs = checkPermissions(request, response, handler);
        if(rs==notLogin){
            permissionDeniedNeedLoginHandle(response);
            return false;
        }
        if(rs==notPermitted){
            permissionDeniedHandle(response);
            return false;
        }
        return super.preHandle(request, response, handler);
    }



    protected void permissionDeniedNeedLoginHandle(HttpServletResponse response) throws Exception {
        PrintWriter writer = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 401);
        jsonObject.put("status", 2);
        jsonObject.put("errorMes", "need login");
        writer.write(jsonObject.toJSONString());
    }


    protected void permissionDeniedHandle( HttpServletResponse response) throws Exception {
        PrintWriter writer = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 403);
        jsonObject.put("status", 2);
        jsonObject.put("errorMes", "permission denied");
        writer.write(jsonObject.toJSONString());
    }

    /**
     * 判断用户是否有访问该接口的权限
     * @param request
     * @param response
     * @param handler
     * @return
     */
    protected int checkPermissions(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequiredPermissions requiredPermissions = getRequiredPermissionsFromHandlerMethodWithCache(handlerMethod);


        if (requiredPermissions == null) {
            generateLog(request, response, handler);
            return hasPermission; // 不做检查
        }

        List<Integer> powerCodeList = (List<Integer>) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_POWERCODES);
        if (powerCodeList == null) {
            return notPermitted;
        }
        int[] requirePowerCodes = requiredPermissions.value();
        boolean res = isPermitted(requirePowerCodes, powerCodeList);
        if (res){
            generateLog(request, response, handler);
        }
        return res ? hasPermission : notPermitted;
    }

    /**
     * 打日志
     */
    private void generateLog(HttpServletRequest request, HttpServletResponse response, Object handler){
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ApiOperation apiOperation = getApiOperationFromHandlerMethodWithCache(handlerMethod);
        Admin admin = (Admin) request.getAttribute(TokenConfig.DEFAULT_USERID_REQUEST_ATTRIBUTE_NAME);
        OperationLog operationLog = new OperationLog();
        operationLog.setCreateTime(new Date());
        operationLog.setUpdateTime(new Date());
        operationLog.setOperationTime(new Date());
        operationLog.setAdminId(admin.getId());
        operationLog.setAdminName(admin.getUsername());
        operationLog.setOperation(request.getRequestURI());
        //设置描述信息
        operationLog.setDescription(apiOperation == null ? null : apiOperation.value());
        operationLog.setExtra(JSON.toJSONString(request.getParameterMap()));
        operationLogService.saveOperationLog(operationLog);

    }

    private boolean isPermitted(int[] permissions,List<Integer> curPermissions){
        for (int permission : permissions) {
            System.out.print(permission + "-");
        }
        System.out.println("");
        for (Integer curPermission : curPermissions) {
            System.out.print(curPermission + "-");
        }
        System.out.println("");
        for (int permission : permissions) {
            if (!curPermissions.contains(permission)) {
                return false;
            }
        }
        return true;
    }

}
