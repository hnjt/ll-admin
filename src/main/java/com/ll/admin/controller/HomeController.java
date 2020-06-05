package com.ll.admin.controller;

import com.ll.admin.domain.Msg;
import com.ll.admin.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index (@AuthenticationPrincipal UserDetails userDetails, Model model){
        Msg msg = new Msg( "测试标题", "测试内容", "额外信息，只有管理员可以看到！" );
        model.addAttribute( "msg",msg );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println( authentication );
        System.out.println( userDetails);
        System.out.println("---------------------------");
        System.out.println( authentication.getDetails() );
        System.out.println( authentication.getAuthorities() );
        System.out.println( authentication.getCredentials());
        System.out.println( authentication.getPrincipal());
        System.out.println("------------------------------");
        /**
         * authentication.getPrincipal() 可以强转为登录对象获取登录信息
         */
        User user =(User) authentication.getPrincipal();
        System.out.println("authentication.getPrincipal():获取登录对象信息"+user.getPassword());
        String[] split = authentication.getDetails().toString().split( ";" );
        String[] split1 = split[0].split( ":" );
        String remoteIpAddress = "";
        for (int i = 0; i < split1.length; i++) {
            if (i > 1){
                remoteIpAddress += split1[i];
            }
            if (i > 2 || i == split1.length){
                remoteIpAddress += ":";
            }
        }
        String sessionId = split[1].split( ":" )[1];
        /**
         * authentication.getDetails() 获取登录绑定式信息
         *  SessionId、RemoteIpAddress
         */
        System.out.println("获取登录绑定信息："+remoteIpAddress + sessionId);
        /**
         * 这里可以根据 switch case 来判断 authentication.getAuthorities()
         * 区分最会跳转的页面
         */
        System.out.println("获取登录角色："+authentication.getAuthorities());
        return "/index";
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
