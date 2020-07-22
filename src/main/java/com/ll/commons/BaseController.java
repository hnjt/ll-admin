package com.ll.commons;

import com.ll.admin.domain.Login;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制层基类
 */
@Slf4j
@RestController
public class BaseController {

    /**
     * 将请求参数封装成map对象
     */
    protected Map<String, String> initParams(HttpServletRequest request){
        Map<String, String> paramMap = new HashMap<>();
        if (request == null)
            return paramMap;

        Enumeration<?> paramNames = request.getParameterNames();
        if (request != null && paramNames != null && paramNames.hasMoreElements()) {
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);
                if (paramValues.length == 1) {
                    paramMap.put(paramName, paramValues[0]);
                } else {
                    paramMap.put(paramName, ArrayUtils.toString(paramValues));
                }
            }
        }
        return paramMap;
    }

    /**
     * 获取登录信息
     * @param request
     * @return
     */
    protected Login getDetails (HttpServletRequest request){
        HttpSession session = request.getSession();
        SecurityContextImpl ssc = (SecurityContextImpl) session.getAttribute( "SPRING_SECURITY_CONTEXT" );
        Authentication authentication = ssc.getAuthentication();
        Login details =(Login) authentication.getPrincipal();
        return details;
    }
}
