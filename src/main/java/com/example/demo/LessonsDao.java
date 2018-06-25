package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sunlu on 2018/6/13.
 */
@Component
public class LessonsDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Lesson> getLesson(Person person){
        List<Lesson> list=null;
        try {
            System.out.println("teacher="+person.getUsername());
            Query query=new Query(Criteria.where("teacher").is(person.getUsername()));
            list=mongoTemplate.find(query, Lesson.class ,"lessons");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public List<Course> getChoose(Person person){
        List<Course> list=null;
        try {
            System.out.println("student="+person.getUsername());
            Query query=new Query(Criteria.where("student").is(person.getUsername()));
            list=mongoTemplate.find(query, Course.class ,"courses");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }


    public List<Lesson> getAll(Person person){
        List<String> listOfID = new ArrayList<String>();
        Query query=new Query(Criteria.where("student").is(person.getUsername()));
        List<Course>result=mongoTemplate.find(query,Course.class,"courses");
        System.out.println("result num"+result.size());
        for(int i=0;i<result.size();i++){
            listOfID.add(result.get(i).getCid());
        }
        for(int i=0;i<listOfID.size();i++){
            System.out.println(listOfID.get(i));
        }
        Query query2 = new Query(Criteria.where("id").nin(listOfID));
        List<Lesson>re2=mongoTemplate.find(query2,Lesson.class,"lessons");
        System.out.println("re2 size:"+re2.size());
        return re2;
    }

    public int addLessons(Lesson lesson){
        System.out.println("enter");
        List<Lesson> result = null;
        try {
            Query query=new Query(Criteria.where("id").is(lesson.getID()));
            result=mongoTemplate.find(query, Lesson.class ,"lessons");
        } catch (Exception e) {
            // TODO: handle exception
        }
        if(result.size()==0){
            System.out.println("还未添加过该id的课程");
            lesson.setPeople_num("0");
            mongoTemplate.save(lesson, "lessons");
            return 1;
        }else {
            System.out.println("该id的课程已存在");
            return 0;
        }
    }

    public int addCourses(Course lesson){
        List<Course> result = null;
        try {
            Query query=new Query(Criteria.where("student").is(lesson.getStudent()).and("cid").is(lesson.getCid()));
            result=mongoTemplate.find(query, Course.class ,"courses");
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println(result.size());
        if(result.size()==0){
            System.out.println("没有选过该id的课程");
            Query query=new Query(Criteria.where("id").is(lesson.getCid()));
            List<Lesson>result2=mongoTemplate.find(query, Lesson.class ,"lessons");
            lesson.setPeople_num(String.valueOf(Integer.parseInt(result2.get(0).getPeople_num())+1));
            System.out.println(lesson.getCid() + lesson.getName() + lesson.getStudent() + lesson.getPeople_num());
            mongoTemplate.save(lesson,"courses");
            mongoTemplate.insert(lesson);
            update(lesson);
            return 1;
        }else {
            System.out.println("已经选过这个课程");
            return 0;
        }
    }

    public void update(Course course){
        try {
            Query query=new Query(Criteria.where("id").is(course.getCid()));
            Update update= new Update().set("people_num", course.getPeople_num());
            //更新查询返回结果集的第一条
            mongoTemplate.updateFirst(query,update,Lesson.class,"lessons");
            //更新查询返回结果集的所有
            // mongoTemplate.updateMulti(query,update,Student.class);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
