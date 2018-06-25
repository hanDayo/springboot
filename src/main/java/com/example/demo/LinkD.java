package com.example.demo;

/**
 * Created by sunlu on 2018/6/22.
 */
public class LinkD {
    private String linkname;
    private String linkcontent;
    private String lid;
    private String node_id;
    private String mapid;

    public void setMapid(String mapid) {
        this.mapid = mapid;
    }

    public String getMapid() {
        return mapid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getLid() {
        return lid;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getLinkcontent() {
        return linkcontent;
    }

    public String getLinkname() {
        return linkname;
    }

    public void setLinkcontent(String linkcontent) {
        this.linkcontent = linkcontent;
    }

    public void setLinkname(String linkname) {
        this.linkname = linkname;
    }
}
