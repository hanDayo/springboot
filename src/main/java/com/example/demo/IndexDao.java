package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sunlu on 2018/6/8.
 */
@Component
public class IndexDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public int register(Person person) {
        List<Person> result = findByName(person.getUsername());
        if (result.size() == 0) {
            System.out.println("还未注册");
            mongoTemplate.save(person, "person");
            return 1;
        } else {
            System.out.println("已经被注册");
            return 0;
        }
    }

    public int login(Person person) {
        List<Person> result = findByName(person.getUsername());
        System.out.println(result.size());
        if (result.size() == 0 || !person.getPassword().equals(result.get(0).getPassword()) ||
                !person.getType().equals(result.get(0).getType())) {
            return 0;
        }
        Query query = new Query(Criteria.where("username").is(person.getUsername()));
        Update update= new Update().set("status", person.getStatus());
        mongoTemplate.updateFirst(query,update,Person.class,"person");
        return 1;
    }

    public Response examinelogin(String username){
        List<Person> result = findByName(username);
        Response response = new Response();
        System.out.println("size:"+result.size());
        if(result.size()>0){
            System.out.println(result.get(0).getStatus());
            if(result.get(0).getStatus().equals("online")){
                response.setStatus("online");
                return response;
            }
        }
        response.setStatus("offline");
        return response;
    }

    public Response exitlogin(String username){
        List<Person> result = findByName(username);
        Response response = new Response();
        System.out.println("size:"+result.size());
        if(result.size()>0){
            Query query = new Query(Criteria.where("username").is(username));
            Update update= new Update().set("status", "offline");
            mongoTemplate.updateFirst(query,update,Person.class,"person");
        }
        response.setStatus("offline");
        return response;
    }
    public List<Person> findByName(String name) {
        List<Person> list = null;
        try {
            System.out.println("name=" + name);
            Query query = new Query(Criteria.where("username").is(name));
            list = mongoTemplate.find(query, Person.class, "person");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public int changePass(Account account) {
        List<Person> list = null;
        Query query = new Query(Criteria.where("username").is(account.getUsername()));
        list = mongoTemplate.find(query, Person.class, "person");
        if(list.size()==0){
            return 0;
        }else {
            if(list.get(0).getPassword().equals(account.getPassword())){
                return 2;
            }
        }
        Update update= new Update().set("password", account.getPassword());
        mongoTemplate.updateFirst(query,update,Person.class,"person");
        return 1;
    }

}
