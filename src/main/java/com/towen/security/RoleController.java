package com.towen.security;

import com.towen.common.MyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/admin/role")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * 返回角色列表页面
     */
    @Secured({"Role_Root","Role_Editor","Role_Other"})
    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size, Model model){
        Sort sort = new Sort(Sort.Direction.DESC, "updateDate");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Role> datas = roleRepository.findRoles(pageable);
        model.addAttribute("datas",datas);
        return "role/list";

    }
    /**
     * 角色新增保存
     */

    @Secured("Role_Root")
    @PostMapping("/save")
    public String save(@RequestParam("name") String name,Role role){
        /**
         * 验证角色名称字段唯一性
         */
        int num = roleRepository.findNumByName(name,MyConfig.Flag_Nomal);
        if(num>0){
            return "redirect:/admin/error";
        }
        role.setName(name);
        //获取当前登录用户并将其id存为create_by和update_by
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userNow = userRepository.findByUsername(userDetails.getUsername());
        role.setCreateBy(userNow.getId());
        role.setUpdateBy(userNow.getId());
        role.setStatus(MyConfig.Flag_Nomal);
        //获取当前日期
        Date date = new Date();
        SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        role.setCreateDate(matter.format(date));
        role.setUpdateDate(matter.format(date));
        roleRepository.save(role);
        return "redirect:/admin/role/list";
    }
    /**
     * 跳转到角色修改页面
     */

    @Secured("Role_Root")
    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id") long id, Model model){
        Role role = roleRepository.findById(id);
        model.addAttribute("role",role);
        return "role/edit";
    }

    /**
     * 角色修改信息的保存
     */

    @Secured("Role_Root")
    @PostMapping("/update")
    public String update(Role role){
        //获取当前登录用户并将其id存为create_by和update_by
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userNow = userRepository.findByUsername(userDetails.getUsername());
        role.setUpdateBy(userNow.getId());
        //获取当前日期
        Date date=new Date();
        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        role.setUpdateDate(matter.format(date));
        role.setStatus(MyConfig.Flag_Nomal);
        roleRepository.save(role);
        return "redirect:/admin/role/list";

    }


    /**
     * 角色删除,状态改为0
     */
    @Secured("Role_Root")
    @GetMapping("/delete")
    public String delete(@RequestParam(value = "id") long id){
        Role role = roleRepository.findById(id);
        role.setStatus(MyConfig.Flag_Del);
        roleRepository.save(role);
        return "redirect:/admin/role/list";
    }


}
