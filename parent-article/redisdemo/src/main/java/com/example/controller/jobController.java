package com.example.controller;

import com.example.domain.Job;
import com.example.domain.User;
import com.example.service.jobService;
import com.example.vo.Saveredisvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("job")
@CrossOrigin
public class jobController {

    @Autowired
    private jobService jobService;
    //查询
    @GetMapping("findAll")
    public List<Job> findAll(){
        List<Job> all = jobService.findAll();
        return all;
    }
    //根据ID查询
    @GetMapping("findById/{id}")
    public Job findById(@PathVariable Integer id){
        return jobService.findById(id);
    }
    //修改
    @PutMapping("upd")
    public String upd(@RequestBody Job job){
        jobService.upd(job);
        return "修改成功";
    }
    //添加
    @PostMapping("save")
    public String save(@RequestBody Job job){
        jobService.save(job);
        return "添加成功";
    }
    //添加整个表
    @PostMapping("saveredis")
    public String saveredis(@RequestBody Saveredisvo saveredisvo){

        jobService.saveredis(saveredisvo.getUser(),saveredisvo.getList());
        return "添加成功";
    }
    //根据Id删除
    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable Integer id){
        jobService.delete(id);
        return "删除成功";
    }
    //根据项目名查项目里的所有信息
    @GetMapping("findAllById/{id}")
    public Job findAllById(@PathVariable Integer id){
        Job byId = jobService.findById(id);
        return byId;
    }
    //total值,总价
    @PostMapping("findAllBy")
    public Map<String, Object> findAllBy(@RequestBody User user){
        return jobService.findAllBy(user);
    }

}
