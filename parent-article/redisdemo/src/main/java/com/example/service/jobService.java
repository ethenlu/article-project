package com.example.service;

import com.example.domain.Job;
import com.example.domain.User;
import com.example.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.lettuce.core.protocol.CommandType.SORT;

@Service
@Transactional
public class jobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    public List<Job> findAll(){
        //1.从redis中获得数据,数据的形式json字符串
        Object all = redisTemplate.opsForValue().get("Job");


        //2.判断redis中是否存在数据
        if(all==null){
            //3.不存在数据 从数据查询
            List<Job> all1 = mongoTemplate.findAll(Job.class);
            //4.将查询出的数据存储到redis缓存
            //向将list集合转换成json格式的字符串 使用jackson进行转换

            redisTemplate.opsForValue().set("Job",all1);
            System.out.println("================从数据库中获得comment的数据=================");
            return all1;
        }else{
            System.out.println("=====================从redis中获得comment的数据============================");
        }
        return (List<Job>) all;
    }

    public Job findById(Integer id){

        Object all = redisTemplate.opsForValue().get("Job_"+id);

        if(all==null){
            Job job = mongoTemplate.findById(id,Job.class);

            redisTemplate.opsForValue().set("Job_"+id,job);
            System.out.println("================从数据库中获得comment的数据=================");
            return job;
        }else{
            System.out.println("=====================从redis中获得comment的数据============================");
            return (Job) all;
        }




    }


    public String upd(Job job){

        redisTemplate.delete("Job_"+job.getId());
        mongoTemplate.save(job);
        jobRepository.save(job);

        return "修改成功";
    }

    public String save(Job job){

        mongoTemplate.save(job);
        jobRepository.save(job);

        return "保存成功";
    }

    public String saveredis(User user, List<Job> list){

        redisTemplate.opsForValue().set(user.getName(),list );

        for (Job job: list) {

            jobRepository.save(job);
        }

        return "保存成功";
    }

    public Map<String, Object> findAllBy(User user){

         List<Job> o = (List<Job>) redisTemplate.opsForValue().get(user.getName());
         Integer arrry  =  o.size();

        Double total;
        total=0.0;
        for (Job o1 : o) {
            total = total+o1.getPrice();
        }

        Map<String, Object> objectObjectMap = new HashMap<>();
        objectObjectMap.put("arrry",arrry);
        objectObjectMap.put("total",total);
        objectObjectMap.put("list",o);

        return objectObjectMap;

    }

    public String delete(Integer id) {
        Job job = new Job();
        job.setId(id);
        redisTemplate.delete("Job_"+id);
        mongoTemplate.remove(job);
        jobRepository.deleteById(id);

//        Query query = new Query();
//        query.equals(job);

        return "删除成功";



    }
}
