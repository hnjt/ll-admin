package com.config.security;

import com.ll.admin.dto.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录跳转配置
 */
@Slf4j
@Controller
public class RequestController {

    /**
     * 成功首页
     * @param userDetails
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index (@AuthenticationPrincipal UserDetails userDetails, Model model){
        Msg msg = new Msg( "测试标题", "测试内容", "额外信息，只有管理员可以看到！" );
        model.addAttribute( "msg",msg );
        return "index";
    }

    /**
     * 登录失败返回接口
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/loginFail")
    public String loginFail (HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        Object badCredentialsException = session.getAttribute( "SPRING_SECURITY_LAST_EXCEPTION" );
        String badCredentialsExceptionStr = badCredentialsException.toString();
        String msg = badCredentialsExceptionStr.split( ":" )[1];
        //错误消息提示
        model.addAttribute( "msg",msg);
        return "login";
    }

    /**
     * 访问限制
     * hasRole和hasAuthority 两种性质完成独立的东西
     * 一个是用做角色控制，一个是操作权限的控制，二者也并不矛盾
     *
     * @GetMapping("/user-role")
     *   @PreAuthorize("hasRole('USER')")
     *   public String readUser() {
     *     return "have a user role";
     *   }
     *
     *   @PreAuthorize("hasAuthority('write')")
     *
     *   @PreAuthorize("hasAuthority('read')")
     *
     *   @PreAuthorize("hasAnyAuthority('read','write')")
     *
     *   @PreAuthorize("hasRole('admin')")
     */


}
