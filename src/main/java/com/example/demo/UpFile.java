package com.example.demo;

import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sunlu on 018/6/18.
 */
public class UpFile {
    private String lid;
    private String node_id;
    private String filename;
    private String fd;
    private byte[] bytes;
    private String mapid;

    public String getMapid() {
        return mapid;
    }

    public void setMapid(String mapid) {
        this.mapid = mapid;
    }

    public String getFd() {
        return fd;
    }

    public void setFd(String fd) {
        this.fd = fd;
    }

    public void setFilename(String name){
        this.filename=name;
    }

    public void setLid(String id){
        this.lid=id;
    }

    public void setBytes(byte[]bytes)throws IOException{
        this.bytes=bytes;
    }

    public String getLid(){
        return this.lid;
    }

    public String getFilename(){
        return this.filename;
    }

    public byte[] getBytes(){
        return this.bytes;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getNode_id() {
        return node_id;
    }
}
