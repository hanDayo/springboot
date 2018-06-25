package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunlu on 2018/6/19.
 */
@Component
public class MindMapDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public int saveMindMap(MindMap mindMap){
        List<MindMap> list=null;
        Query query=new Query(Criteria.where("lid").is(mindMap.getLid()));
        list=mongoTemplate.find(query, MindMap.class ,"mindmap");
        System.out.println(list.size());
        if(list.size()==0){
            System.out.println("还未添加过该id的课程mindmap");
            mongoTemplate.save(mindMap,"mindmap");
        }else {
            Update update= new Update().set("items", mindMap.getItems());
            mongoTemplate.updateFirst(query,update,MindMap.class,"mindmap");
            System.out.println("已更新该id的课程mindmap");
        }
        return 1;
    }

    public MindMap getMindMap(Lesson lesson){
        List<MindMap>list=null;
        Query query=new Query(Criteria.where("lid").is(lesson.getID()));
        list=mongoTemplate.find(query, MindMap.class ,"mindmap");
        System.out.println(list.size());
        if(list.size()==0){
            return null;
        }else {
            return list.get(0);
        }
    }

    public void saveNum(Number number){
        List<Number>list=null;
        Query query=new Query(Criteria.where("lid").is(number.getLid()));
        list=mongoTemplate.find(query, Number.class ,"number");
        if(list.size()==0){
            System.out.println("还未添加过该id的课程的ids");
            mongoTemplate.save(number,"number");
        }else {
            Update update= new Update().set("ids", number.getIds());
            mongoTemplate.updateFirst(query,update,Number.class,"number");
            System.out.println("已更新该id的课程ids");
        }

    }

    public Number getNum(Lesson lesson){
        List<Number>list=null;
        Query query=new Query(Criteria.where("lid").is(lesson.getID()));
        list=mongoTemplate.find(query, Number.class ,"number");
        System.out.println(list.size());
        if(list.size()==0){
            return null;
        }else {
            return list.get(0);
        }
    }

    public void addQ0(Question0 question0){
        mongoTemplate.save(question0,"question0");
    }

    public List<Question0>getQ0(MPNode mpNode){
        List<Question0>list=null;
        Query query=new Query(Criteria.where("lid").is(mpNode.getLid()).and("node_id").is(mpNode.getNode_id()).and("mapid").is(mpNode.getMapid()));
        list=mongoTemplate.find(query, Question0.class ,"question0");
        List<Submit>list2=null;
        if(list.size()==0){
            return null;
        }else {
            for(int i=0;i<list.size();i++){
                Query query2=new Query(Criteria.where("lid").is(list.get(i).getLid()).and("node_id").is(list.get(i).getNode_id()).and("mapid").is(list.get(i).getMapid()).and("title").is(list.get(i).getTitle()));
                list2=mongoTemplate.find(query2, Submit.class ,"submit");
                System.out.println("sum size:"+list2.size());
                if(list2.size()==0){
                    list.get(i).setRate("无人答题");
                }else {
                    double sum=list2.size();
                    query2=new Query(Criteria.where("lid").is(list.get(i).getLid()).and("node_id").is(list.get(i).getNode_id()).and("mapid").is(list.get(i).getMapid()).and("title").is(list.get(i).getTitle()).and("answer").is("yes"));
                    list2=mongoTemplate.find(query2, Submit.class ,"submit");
                    System.out.println("yes size:"+list2.size());
                    double num=list2.size();
                    double a=num/sum;
                    System.out.println(a);
                    list.get(i).setRate(Double.toString(a));
                }
            }
            return list;
        }
    }

    public void addQ1(Question1 question1){
        mongoTemplate.save(question1,"question1");
    }

    public List<Question1>getQ1(MPNode mpNode){
        List<Question1>list=null;
        Query query=new Query(Criteria.where("lid").is(mpNode.getLid()).and("node_id").is(mpNode.getNode_id()).and("mapid").is(mpNode.getMapid()));
        list=mongoTemplate.find(query, Question1.class ,"question1");
        System.out.println("q1 size:"+list.size());
        if(list.size()==0){
            return null;
        }else {
            return list;
        }
    }

    public void removeQ0(Question0 question0){
        Query query = Query.query(Criteria.where("lid").is(question0.getLid()).and("node_id").is(question0.getNode_id()).and("mapid").is(question0.getMapid()).and("title").is(question0.getTitle()));
        mongoTemplate.remove(query,"question0");
    }

    public void removeQ1(Question1 question0){
        Query query = Query.query(Criteria.where("lid").is(question0.getLid()).and("node_id").is(question0.getNode_id()).and("mapid").is(question0.getMapid()).and("title").is(question0.getTitle()));
        mongoTemplate.remove(query,"question1");
    }

    public Response submit(Submit[]submits){
        List<Submit>list=null;
        Response response=new Response();
        response.setStatus("yes");
        for(int i=0;i<submits.length;i++){
            Query query=new Query(Criteria.where("lid").is(submits[i].getLid()).and("node_id").is(submits[i].getNode_id()).and("mapid").is(submits[i].getMapid()).and("title").is(submits[i].getTitle()).and("username").is(submits[i].getUsername()));
            list=mongoTemplate.find(query, Submit.class ,"submit");
            System.out.println("list size"+list.size());
            if(list.size()==0){
                System.out.println("还未答过该题");
                mongoTemplate.save(submits[i],"submit");
            }else {
                response.setStatus("已答过"+submits[i].getTitle());
            }
        }
        if(response.getStatus().equals("yes")){
            response.setStatus("答题成功");
        }
        return response;
    }
}
