package com.towen.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/course")
    public Course course(){
        Course course = new Course();
        course.setId(0);
        course.setName("aa");
        course.setStatus("1");
        return course;
    }
}
