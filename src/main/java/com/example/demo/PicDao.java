package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by sunlu on 2018/6/18.
 */
@Component
public class PicDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void uploadWare(UpFile upFile) {
        System.out.println("name: "+upFile.getFilename());
        mongoTemplate.save(upFile, "upload_ware");
    }

    public void uploadResource(UpFile upFile) {
        System.out.println("name: "+upFile.getFilename());
        mongoTemplate.save(upFile, "upload_resource");
    }

    public List<UpFile> showWare(MPNode mpNode){
        List<UpFile>list=null;
        Query query=new Query(Criteria.where("lid").is(mpNode.getLid()).and("node_id").is(mpNode.getNode_id()).and("mapid").is(mpNode.getMapid()));
        list=mongoTemplate.find(query, UpFile.class ,"upload_ware");
        List<UpFile>list2=null;
        Query query2=new Query(Criteria.where("lid").is(mpNode.getLid()).and("node_id").is(mpNode.getNode_id()).and("mapid").is(mpNode.getMapid()));
        list2=mongoTemplate.find(query2, UpFile.class ,"upload_ware_fd");
        System.out.println(list.size());
        System.out.println(list2.size());
        if(list.size()==0){
            System.out.println("无上传课件");
            return null;
        }else {
            for(int i=0;i<list.size();i++){
                list.get(i).setFd(list2.get(i).getFd());
            }
            System.out.println("有上传课件:"+list.size());
            return list;
        }
    }

    public List<UpFile> showResource(MPNode mpNode){
        List<UpFile>list=null;
        Query query=new Query(Criteria.where("lid").is(mpNode.getLid()).and("node_id").is(mpNode.getNode_id()).and("mapid").is(mpNode.getMapid()));
        list=mongoTemplate.find(query, UpFile.class ,"upload_resource");
        List<UpFile>list2=null;
        Query query2=new Query(Criteria.where("lid").is(mpNode.getLid()).and("node_id").is(mpNode.getNode_id()).and("mapid").is(mpNode.getMapid()));
        list2=mongoTemplate.find(query2, UpFile.class ,"upload_resource_fd");
        if(list.size()==0){
            System.out.println("无上传课件");
            return null;
        }else {
            for(int i=0;i<list.size();i++){
                list.get(i).setFd(list2.get(i).getFd());
            }
            System.out.println("有上传课件:"+list.size());
            return list;
        }
    }

    public byte[]getResourceBuffer(UpFile upFile){
        List<UpFile>list=null;
        Query query=new Query(Criteria.where("lid").is(upFile.getLid()).and("filename").is(upFile.getFilename()).and("mapid").is(upFile.getMapid()));
        list=mongoTemplate.find(query, UpFile.class ,"upload_resource");
        System.out.println("size: "+list.size());
        return list.get(0).getBytes();
    }
    public byte[]getResourceBuffer1(UpFile upFile){
        List<UpFile>list=null;
        Query query=new Query(Criteria.where("lid").is(upFile.getLid()).and("filename").is(upFile.getFilename()).and("mapid").is(upFile.getMapid()));
        list=mongoTemplate.find(query, UpFile.class ,"upload_ware");
        System.out.println("size: "+list.size());
        return list.get(0).getBytes();
    }

    public void uploadFD(UpFile upFile){
        mongoTemplate.save(upFile, "upload_resource_fd");
    }
    public void uploadFD1(UpFile upFile){
        mongoTemplate.save(upFile, "upload_ware_fd");
    }

    public void uploadLink(LinkD linkD){
        mongoTemplate.save(linkD, "upload_link");
    }

    public List<LinkD> getLink(MPNode mpNode){
        List<LinkD>list=null;
        Query query=new Query(Criteria.where("lid").is(mpNode.getLid()).and("node_id").is(mpNode.getNode_id()).and("mapid").is(mpNode.getMapid()));
        list=mongoTemplate.find(query, LinkD.class ,"upload_link");
        if(list.size()==0){
            System.out.println("无上传link");
            return null;
        }else {
            return list;
        }
    }
}
