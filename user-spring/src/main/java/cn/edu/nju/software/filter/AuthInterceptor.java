package cn.edu.nju.software.filter;

import cn.edu.nju.software.annotation.NotLogin;
import cn.edu.nju.software.annotation.RequiredPermissions;
import com.alibaba.fastjson.JSONObject;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(AuthInterceptor.class);
    private static final int notLogin = 0;
    private static final int notPermitted = 1;
    private static final int hasPermission = 2;

    @Autowired
    private IAuth auth;
    @Autowired
    private IUserService userService;

    @Autowired
    private IAffairService affairService;

    @Autowired
    private IAllianceService allianceService;


    private static final Map<String, Object> serviceMethodNotLoginInfoMapping = new HashMap<>();
    private static final Lock serviceMethodNotLoginInfoLock = new ReentrantLock();
    private static final Map<String, Object> serviceMethodRequiredPermissionsMapping = new HashMap<>();
    private static final Lock serviceMethodRequiredPermissionsLock = new ReentrantLock();

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

    private NotLogin getNotLoginFromHandlerMethodWithCache(HandlerMethod handlerMethod) {
        return getAnnotationFromHandlerMethodWithCache(handlerMethod, NotLogin.class, serviceMethodNotLoginInfoMapping, serviceMethodNotLoginInfoLock);
    }

    private RequiredPermissions getRequiredPermissionsFromHandlerMethodWithCache(HandlerMethod handlerMethod) {
        return getAnnotationFromHandlerMethodWithCache(handlerMethod, RequiredPermissions.class, serviceMethodRequiredPermissionsMapping, serviceMethodRequiredPermissionsLock);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
        jsonObject.put("code", ResponseCode.Unauthorized);
        jsonObject.put("data", "need login");
        writer.write(jsonObject.toJSONString());
    }


    protected void permissionDeniedHandle( HttpServletResponse response) throws Exception {
        PrintWriter writer = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", ResponseCode.Forbidden);
        jsonObject.put("data", "permission denied");
        writer.write(jsonObject.toJSONString());
    }



    protected int checkPermissions(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (getNotLoginFromHandlerMethodWithCache(handlerMethod) != null) {
            return hasPermission;
        }

        // isLogin
        if (!auth.isAuthenticated()) {
            return notLogin;
        }

        Long roleId = Long.parseLong(request.getParameter("operationRoleId"));
        if(!userService.belong(auth.currentUserId(),roleId)){//如果操作角色不属于当前登录用户
            return notPermitted;
        }

        RequiredPermissions requiredPermissions = getRequiredPermissionsFromHandlerMethodWithCache(handlerMethod);
        if (requiredPermissions == null) {
            return hasPermission; // 不做检查
        }

        int[] affairPermissions = requiredPermissions.affair();//检查事务权限
        if(affairPermissions!=null&&affairPermissions.length!=0){
            Long affairId = Long.parseLong(request.getParameter("affairId"));
            if(affairId==null){
                return notPermitted;
            }
            if(roleId==null){
                return notPermitted;
            }
            if(!isPermitted(affairPermissions,affairService.getPermissions(affairId,roleId))){
                return notPermitted;
            }
        }

        int[] alliancePermissions = requiredPermissions.alliance();//检查盟权限
        if(alliancePermissions!=null&&alliancePermissions.length!=0){
            Long allianceId = Long.parseLong(request.getParameter("allianceId"));
            if(allianceId==null){
                return notPermitted;
            }
            if(roleId==null){
                return notPermitted;
            }
            if(!isPermitted(alliancePermissions,allianceService.getPermissions(allianceId,roleId))){
                return notPermitted;
            }
        }

        return hasPermission;

    }



    private boolean isPermitted(int[] permissions,String currentPermission){
        if(permissions==null&&permissions.length==0){
            return true;
        }
        int i=0;
        int compair=0;
        int a,index;
        while (i<currentPermission.length()){
            a=0;//某项权限大小
            index=1;//进制比如12,为1*10+2
            char tmp = currentPermission.charAt(i);
            while (tmp!=','){
                a = a*index+(tmp-'0');
                i++;
                index=index*10;
                System.out.println(a);
                if(i==currentPermission.length()){
                    break;
                }
                tmp  = currentPermission.charAt(i);

            }
            for(int p:permissions){
                if(p==a){
                    compair++;
                }
                if(compair==permissions.length){
                    return true;
                }
            }
            i++;
        }
        return false;
    }

}
