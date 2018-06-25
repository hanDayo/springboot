package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by sunlu on 2018/6/8.
 */

@CrossOrigin
@RestController
public class IndexController {
    @Autowired
    private IndexDao indexDao;
    @Autowired
    private LessonsDao lessonsDao;
    @Autowired
    private PicDao picDao;
    @Autowired
    private MindMapDao mindMapDao;

    @PostMapping("test2")
    public Person test2(@RequestBody Person person){
        return person;
    }
    @PostMapping(value = "register", produces = {"application/json;charset = UTF-8"})
    @ResponseBody
    public String register(@RequestBody Person person) {
        System.out.println("enter register");
        int result = indexDao.register(person);
        if (result == 1)
            return "{\"status\":\"yes\"}";
        else
            return "{\"status\":\"no\"}";
    }
    @GetMapping("test")
    public String test(){
        return "123";
    }
    @PostMapping("login")
    public Response login(@RequestBody Person person) {
        System.out.println("enter login");
        Response response = new Response();
        response.setUsername(person.getUsername());
        response.setType(person.getType());
        int result = indexDao.login(person);
        if (result == 1) {
            response.setStatus("yes");
            System.out.println("login success");
        } else {
            response.setStatus("no");
            System.out.println("login failure");
        }
        return response;
    }
    @PostMapping("examinelogin/{username}")
    public Response examineLogin(@PathVariable String username){
        Response response = new Response();
        response=indexDao.examinelogin(username);
        return response;
    }
    @PostMapping("exitlogin/{username}")
    public Response exitLogin(@PathVariable String username){
        Response response = new Response();
        response=indexDao.exitlogin(username);
        return response;
    }

    @RequestMapping("/teacher_lessons")
    public Object[] getList(@RequestBody Person person) {
        List<Lesson> list = lessonsDao.getLesson(person);
        System.out.println(list.size());
        Object[]result=new Object[list.size()];
        for(int i=0;i<list.size();i++){
            result[i]=list.get(i);
        }
        return result;
    }

    @PostMapping("teacher_add_lessons")
    public String addLessons(@RequestBody Lesson lesson) {
        System.out.println(lesson.getID() + lesson.getName() + lesson.getTeacher() + lesson.getPeople_num());
        int result = lessonsDao.addLessons(lesson);
        if (result == 1) {
            return "{\"status\":\"yes\"}";
        } else {
            return "{\"status\":\"no\"}";
        }
    }

    @PostMapping("student_add_lessons")
    public String addCourses(@RequestBody Course lesson) {
        System.out.println(lesson.getCid() + lesson.getName() + lesson.getStudent() + lesson.getPeople_num()+lesson.getLid());
        int result = lessonsDao.addCourses(lesson);
        if (result == 1) {
            return "{\"status\":\"yes\"}";
        } else {
            return "{\"status\":\"no\"}";
        }
    }

    @PostMapping("addAll")
    public Object[] getAll(@RequestBody Person person) {
        List<Lesson> list = lessonsDao.getAll(person);
        Object[]result=new Object[list.size()];
        for(int i=0;i<list.size();i++){
            result[i]=list.get(i);
        }
        return result;
    }

    @PostMapping("student_lessons")
    public Object[] getChoose(@RequestBody Person person) {
        List<Course> list = lessonsDao.getChoose(person);
        System.out.println("student-courses"+list.size());
        Object[]result=new Object[list.size()];
        for(int i=0;i<list.size();i++){
            result[i]=list.get(i);
        }
        return result;
    }

    @PostMapping(value = "upload_ware/{lid}/{node_id}/{mapid}")
    public String testUpload(@RequestParam("file") MultipartFile file, @PathVariable String lid,@PathVariable String node_id,@PathVariable String mapid) throws IOException {
        System.out.println(lid+node_id);
        System.out.println("后台文件上传函数");
        System.out.println("获取到的文件名称为：" + file);
        String filePath = file.getOriginalFilename(); // 获取文件的名称
        UpFile upFile=new UpFile();
        upFile.setBytes(file.getBytes());
        upFile.setFilename(filePath);
        upFile.setLid(lid);
        upFile.setNode_id(node_id);
        upFile.setMapid(mapid);
        picDao.uploadWare(upFile);
        return "客户资料上传成功";
    }

    @PostMapping(value = "upload_resource/{lid}/{node_id}/{mapid}")
    public String testUpload1(@RequestParam("file") MultipartFile file, @PathVariable String lid,@PathVariable String node_id,@PathVariable String mapid) throws IOException {
        System.out.println(lid+node_id);
        System.out.println("后台文件上传函数");
        System.out.println("获取到的文件名称为：" + file);
        String filePath = file.getOriginalFilename(); // 获取文件的名称
        UpFile upFile=new UpFile();
        upFile.setBytes(file.getBytes());
        upFile.setFilename(filePath);
        upFile.setLid(lid);
        upFile.setNode_id(node_id);
        upFile.setMapid(mapid);
        picDao.uploadResource(upFile);
        return "客户资料上传成功";
    }

    @PostMapping("showWare")
    public List<UpFile> showWare(@RequestBody MPNode mpNode)throws IOException{
        System.out.println("enter showWare");
        List<UpFile> result=picDao.showWare(mpNode);
        if(result==null){
            return result;
        }else {
            for(int i=0;i<result.size();i++){
                result.get(i).setBytes(null);
            }
            return result;
        }
    }
    @PostMapping("showResource")
    public List<UpFile> showResource(@RequestBody MPNode mpNode)throws IOException{
        System.out.println("enter showResource");
        List<UpFile> result=picDao.showResource(mpNode);
        if(result==null){
            return result;
        }else {
            for(int i=0;i<result.size();i++){
                result.get(i).setBytes(null);
            }
            return result;
        }
    }

    @PostMapping("download")
    public void download(@RequestBody UpFile upFile,HttpServletResponse response)throws IOException{
        System.out.println("enter download");
        System.out.println(upFile.getLid());
        System.out.println(upFile.getFilename());
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition",
                "attachment;fileName=" + upFile.getFilename());// 设置文件名
        byte[] buffer = picDao.getResourceBuffer(upFile);
        OutputStream os = response.getOutputStream();
        os.write(buffer,0,buffer.length);
        System.out.println("success");
    }
    @PostMapping("download1")
    public void download1(@RequestBody UpFile upFile,HttpServletResponse response)throws IOException{
        System.out.println("enter download");
        System.out.println(upFile.getLid());
        System.out.println(upFile.getFilename());
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition",
                "attachment;fileName=" + upFile.getFilename());// 设置文件名
        byte[] buffer = picDao.getResourceBuffer1(upFile);
        OutputStream os = response.getOutputStream();
        os.write(buffer,0,buffer.length);
        System.out.println("success");
    }
    @PostMapping("changePass")
    public Response changePass(@RequestBody Account account){
        int result=indexDao.changePass(account);
        Response response=new Response();
        if(result==2){
            response.setStatus("same");
        }else if(result==1){
            response.setStatus("yes");
        }else {
            response.setStatus("no");
        }
        return response;
    }


    @PostMapping("saveMindMap")
    public String saveMindMap(@RequestBody MindMap mindMap){
        System.out.println(mindMap.getLid());
        int result = this.mindMapDao.saveMindMap(mindMap);
        return "{\"status\":\"yes\"}";
    }

    @PostMapping("getMindMap")
    public MindMap getMindMap(@RequestBody Lesson lesson){
        System.out.println(lesson.getID());
        MindMap mindMap=this.mindMapDao.getMindMap(lesson);
        return mindMap;
    }

    @PostMapping("saveNum")
    public String saveNum(@RequestBody Number number){
        System.out.println(number.getIds().length);
        this.mindMapDao.saveNum(number);
        return "{\"status\":\"yes\"}";
    }

    @PostMapping("getNum")
    public Number getNum(@RequestBody Lesson lesson){
        System.out.println(lesson.getID());
        Number number=this.mindMapDao.getNum(lesson);
        return number;
    }

    @PostMapping("addQ0")
    public String addQ0(@RequestBody Question0 question0){
        System.out.println(question0.getLid());
        this.mindMapDao.addQ0(question0);
        return "{\"status\":\"yes\"}";
    }

    @PostMapping("getQ0")
    public List<Question0> getQ0(@RequestBody MPNode mpNode){
        List<Question0>list=this.mindMapDao.getQ0(mpNode);
        return list;
    }

    @PostMapping("addQ1")
    public String addQ1(@RequestBody Question1 question1){
        this.mindMapDao.addQ1(question1);
        return "{\"status\":\"yes\"}";
    }

    @PostMapping("getQ1")
    public List<Question1> getQ1(@RequestBody MPNode mpNode){
        List<Question1>list=this.mindMapDao.getQ1(mpNode);
        return list;
    }

    @PostMapping("removeQ0")
    public String removeQ0(@RequestBody Question0 question0){
        this.mindMapDao.removeQ0(question0);
        return "{\"status\":\"yes\"}";
    }
    @PostMapping("removeQ1")
    public String removeQ1(@RequestBody Question1 question0){
        this.mindMapDao.removeQ1(question0);
        return "{\"status\":\"yes\"}";
    }

    @PostMapping("/upload_fd")
    public String upload(@RequestBody UpFile upFile){
        System.out.println("enter fd");
        System.out.println(upFile.getFilename()+upFile.getFd()+upFile.getNode_id()+upFile.getLid());
        this.picDao.uploadFD(upFile);
        return "{\"status\":\"yes\"}";
    }
    @PostMapping("/upload_fd1")
    public String upload1(@RequestBody UpFile upFile){
        System.out.println("enter fd");
        System.out.println(upFile.getFilename()+upFile.getFd()+upFile.getNode_id()+upFile.getLid());
        this.picDao.uploadFD1(upFile);
        return "{\"status\":\"yes\"}";
    }

    @PostMapping("upload_link")
    public String uploadLink(@RequestBody LinkD linkD){
        System.out.println(linkD.getMapid());
        this.picDao.uploadLink(linkD);
        return "{\"status\":\"yes\"}";
    }
    @PostMapping("get_link")
    public List<LinkD> getLink(@RequestBody MPNode mpNode){
        List<LinkD>list=this.picDao.getLink(mpNode);
        return list;
    }
    @PostMapping("submit")
    public Response submit(@RequestBody Submit[]submits){
        System.out.println(submits.length);
        Response response=this.mindMapDao.submit(submits);
        return response;
    }
}


