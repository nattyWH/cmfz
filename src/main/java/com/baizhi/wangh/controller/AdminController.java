package com.baizhi.wangh.controller;

import com.baizhi.wangh.entity.Admin;
import com.baizhi.wangh.service.AdminService;
import com.baizhi.wangh.yanzheng.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    @ResponseBody
    public String login(Admin admin,HttpSession session,String code){
        Admin admin1 = adminService.login(admin);
        String serverCode = session.getAttribute("ServerCode").toString();
        if(admin1!=null){
            if(code.equals(serverCode)){
                session.setAttribute("admin",admin1);
                return "ok";
            }else {
                return "codeError";
            }
        }else {
            return "error";
        }
    }

    //验证码
    @RequestMapping("/createImg")
    public void createImg(HttpServletResponse response, HttpSession session) throws IOException {
        CreateValidateCode validateCode = new CreateValidateCode();
        String code = validateCode.getCode();
        validateCode.write(response.getOutputStream()); // 把图片输出client

        // 把生成的验证码 存入session
        session.setAttribute("ServerCode", code);
    }

    //退出
    @RequestMapping("/out")
    public String out(HttpSession session){
        session.invalidate();
        return "redirect:/jsp/login.jsp";
    }
}
