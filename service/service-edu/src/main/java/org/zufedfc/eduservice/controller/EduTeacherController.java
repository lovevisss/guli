package org.zufedfc.eduservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.zufedfc.eduservice.entity.EduTeacher;
import org.zufedfc.eduservice.service.EduTeacherService;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-09-06
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;
//    1.查询讲师表所有数据
//    rest风格
//    GET http://localhost:8001/eduservice/edu-teacher/findAll
    @GetMapping("findAll")
    public List<EduTeacher> findAll(){
        return teacherService.list(null);
    }



}

