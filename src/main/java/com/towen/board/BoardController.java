package com.towen.board;

import com.towen.security.User;
import com.towen.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class BoardController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    public BoardController() {}

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String DashBoard(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userNow = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("userNow",userNow);
        return "dashboard";
    }
}
