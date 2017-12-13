package cn.edu.nju.software.filter;

import cn.edu.nju.software.entity.App;
import cn.edu.nju.software.service.AppService;
import cn.edu.nju.software.util.TokenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * app版本校验
 */
public class VersionCheckInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(VersionCheckInterceptor.class);
    @Autowired
    private AppService appService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.info("************:" + request.getRequestURI());
        //检验app版本号
        this.checkAppVersion(request, response, handler);
        return super.preHandle(request, response, handler);
    }

    /**
     * 校验app的版本号是否满足最低版本号要求
     * @param request
     * @param response
     * @param handler
     * @return
     */
    private boolean checkAppVersion(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String version = request.getHeader(TokenConfig.DEFAULT_ANDROID_VERSION_HEADER_NAME);
        //TODO 兼容旧的版本  待版本限制正式上线时改为 return false
        if (version == null) {
            return true;
        }
        App currentVersionApp = appService.getAppByVersion(version);
        if (currentVersionApp == null) {
            throw new IllegalArgumentException("未知的安卓版本");
        }
        App limitVersionApp = appService.getMinLimitVersionApp();
        if (limitVersionApp == null) {
            logger.info("*************尚未设置最低版本号************");
            return true;
        }
        if (currentVersionApp.getId() < limitVersionApp.getId()) {
            throw new IllegalArgumentException("当前app版本过低");
        }

        return true;

    }

}
