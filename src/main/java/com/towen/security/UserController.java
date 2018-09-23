package com.towen.security;

import com.towen.common.Md5Utils;
import com.towen.common.MyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    /**
     * 返回user列表页面
     *
     * @return
     */
    @Secured({"Role_Root", "Role_Editor", "Role_Other"})
    @GetMapping("/list")
    public String userList(@RequestParam(value = "page", defaultValue = "0") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size, Model model) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateDate");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<User> datas = userRepository.findUsers(pageable);
        List<Role> roleList = roleRepository.findByRoleId(5);
        model.addAttribute("roleList",roleRepository);
        model.addAttribute("datas", datas);
        return "user/list";
    }

    /**
     * 用户基本信息保存
     *
     * @param user
     * @return
     */
    @Secured("Role_Root")
    @PostMapping("/save")
    public String save(@RequestParam("username") String username, @RequestParam("password") String password,
                       @RequestParam("trueName") String trueName, User user) {
        /**
         * 判断用户名字段唯一性
         */
        int num = userRepository.findNumByUsername(username, MyConfig.Flag_Nomal);
        if (num > 0) {
            return "redirect:/admin/error";

        }
        user.setUsername(username);
        /**
         * 使用BCrypt加密算法
         */
        BCryptPasswordEncoder encode =new BCryptPasswordEncoder();
        user.setPassword(encode.encode(password));
        /**
         * 用户和角色的关联
         */
        //暂自定义
        List<Role> roleList = roleRepository.findByRoleId(5);
        user.setRoles(roleList);
        //获取当前登录用户并将其id存为create_by和update_by
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userNow = userRepository.findByUsername(userDetails.getUsername());
        user.setUpdateBy(userNow.getId());
        user.setCreateBy(userNow.getId());
        user.setStatus(MyConfig.Flag_Nomal);
        //获取当前日期
        Date date = new Date();
        SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setUpdateDate(matter.format(date));
        user.setCreateDate(matter.format(date));
        userRepository.save(user);
        return "redirect:/admin/user/list";
    }

    /**
     * 用户信息修改页面
     *
     * @param id
     * @return
     */
    @Secured("Role_Root")
    @GetMapping("/edit")
    public String edit(@RequestParam("id") long id, Model model) {
        /*,@RequestParam(value = "username", defaultValue = "")String username,@RequestParam(value = "password", defaultValue = "")String password,
                       @RequestParam(value = "trueName",defaultValue = "") String trueName,User user*/
        User user = userRepository.findOneById(id);
        /**
         * 修改信息的同时也要判断登陆用户名字段的唯一性
         *//*
        int num = userRepository.findNumByUsername(username,MyConfig.Flag_Nomal);
        if(num > 0){
            return "redirect:/admin/error";

        }
        user.setUsername(username);
        user.setPassword(Md5Utils.md5(password));
        user.setTrueName(trueName);
        //获取当前登录用户并将其id存为create_by和update_by
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userNow = userRepository.findByUsername(userDetails.getUsername());
        user.setCreateBy(userNow.getId());
        user.setUpdateBy(userNow.getId());
        user.setStatus(MyConfig.Flag_Nomal);
        //获取当前日期
        Date date=new Date();
        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setUpdateDate(matter.format(date));
        user.setCreateDate(matter.format(date));
        userRepository.save(user);*/
//        if (user != null) {
//            return user;
//        }
        model.addAttribute("user", user);
        return "user/edit";

    }

    /**
     * 保存用户修改的信息
     */
    @Secured("Role_Root")
    @PostMapping("/update")
    public String update(User user){
        //获取当前登录用户并将其id存为和update_by
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userNow = userRepository.findByUsername(userDetails.getUsername());
        user.setUpdateBy(userNow.getId());
        //获取当前日期
        Date date=new Date();
        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setUpdateDate(matter.format(date));
        user.setStatus(MyConfig.Flag_Nomal);
        userRepository.save(user);
        return "redirect:/admin/user/list";

    }


    /**
     * 删除用户信息,状态改为0
     *
     * @param id
     * @return
     */
    @Secured("Role_Root")
    @GetMapping("/delete")
    public String delete(@RequestParam("id") long id) {
        User user = userRepository.findOneById(id);
        user.setStatus(MyConfig.Flag_Del);
        userRepository.save(user);
        return "redirect:/admin/user/list";

    }

}
