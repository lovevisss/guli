package org.zufedfc.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.zufedfc.commonutils.R;
import org.zufedfc.eduservice.entity.EduTeacher;
import org.zufedfc.eduservice.entity.vo.EduTeacherQuery;
import org.zufedfc.eduservice.service.EduTeacherService;
import org.zufedfc.servicebase.exception.GuliException;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-09-06
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;
//    1.查询讲师表所有数据
//    rest风格
//    GET http://localhost:8001/eduservice/edu-teacher/findAll
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAll(){

        return R.ok().data("items", teacherService.list(null));
    }

//    2.逻辑删除讲师的方法
//    rest风格
//    DELETE http://localhost:8001/eduservice/edu-teacher/1
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping ("{id}")
    public R removeById(@ApiParam(name = "id", value = "输入讲师的ID", required = true) @PathVariable  String id){
        boolean flag = teacherService.removeById(id);
        try {

            int i = 10/0;
        }catch (Exception e){
            throw new GuliException(23,"执行了自定义异常处理..");
        }
        if(flag)
        {
            return R.ok().message("讲师删除成功");
        }else
        {
            return R.error().message("讲师删除失败");
        }
    }

//    3.分页查询讲师的方法
//    rest风格
//    GET http://localhost:8001/eduservice/edu-teacher/pageTeacher/1/3
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
                             @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit)
    {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        teacherService.page(pageTeacher, null);
        return R.ok().data("total", pageTeacher.getTotal()).data("rows", pageTeacher.getRecords());

    }

//    4.条件查询带分页的方法
//    rest风格
//    POST http://localhost:8001/eduservice/edu-teacher/pageTeacherCondition/1/3
    @ApiOperation(value = "条件查询带分页的方法")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable long limit,
                                  @ApiParam(name = "teacherQuery", value = "查询对象", required = false) @RequestBody(required = false) EduTeacherQuery teacherQuery)
    {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
//     多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            System.out.println(name);
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            System.out.println(level);
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            System.out.println(begin);
            wrapper.ge("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)){
            System.out.println(end);
            wrapper.le("gmt_create", end);
        }

        teacherService.page(pageTeacher, wrapper);
        return R.ok().data("total", pageTeacher.getTotal()).data("rows", pageTeacher.getRecords());

    }

//    5.添加讲师接口的方法
//    rest风格
//    POST http://localhost:8001/eduservice/edu-teacher/addTeacher
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@ApiParam(name = "eduTeacher", value = "讲师对象", required = true) @RequestBody EduTeacher eduTeacher)
    {
        boolean save = teacherService.save(eduTeacher);
        if(save)
        {
            return R.ok().message("讲师添加成功");
        }else
        {
            return R.error().message("讲师添加失败");
        }
    }

//    6.根据讲师id进行查询
//    rest风格
//    GET http://localhost:8001/eduservice/edu-teacher/getTeacher/1
    @ApiOperation(value = "根据讲师id进行查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id)
    {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

//    7.讲师修改功能
//    rest风格
//    POST http://localhost:8001/eduservice/edu-teacher/updateTeacher
    @ApiOperation(value = "讲师修改功能")
    @PostMapping("updateTeacher")
    public R updateTeacher(@ApiParam(name = "eduTeacher", value = "讲师对象", required = true) @RequestBody EduTeacher eduTeacher)
    {
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag)
        {
            return R.ok().message("讲师修改成功");
        }else
        {
            return R.error().message("讲师修改失败");
        }
    }



}

