package com.aim.questionnaire.controller;

import com.aim.questionnaire.baidu.FaceMatch;
import com.aim.questionnaire.baidu.Textmatch;
import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.dao.entity.UserEntity;
import com.aim.questionnaire.dao.entity.loginMess;
import com.aim.questionnaire.dao.entity.people;
import com.aim.questionnaire.dao.entity.picture;
import com.aim.questionnaire.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserService userService;
//    @Autowired
//    private RedisTemplate redisTemplate;
//    public List<String>  keySet=new ArrayList<String>();

    @PostMapping("/peopleRes")
    public  String peopleRes(String text) throws IOException, InterruptedException {
        System.out.println(text);
        Textmatch text1=new Textmatch();
        List<people> result=userService.queryProblemList();
        if(text.equals("")){
            return "";
        }
        if(text==null){
            return "";
        }
        double nihao=0;
        String res="";
        for(int i=0;i<result.size();i++){
            Thread.sleep(500);
            if(Double.parseDouble(text1.match(text, result.get(i).getProblem()))>nihao){
                nihao=Double.parseDouble(text1.match(text,result.get(i).getProblem()));
                res=result.get(i).getAnswer();
            }
        }
        if(nihao<0.50){
            res="无法回答该问题";
        }
        return res;
    }




    @PostMapping("/searchPic")
    public  HttpResponseEntity uploadImage(@RequestParam("file") MultipartFile file ,String username) throws  IOException {
        String userName=username;
        System.out.println(username);
        String resultUrl;
        byte[] bytes = file.getBytes();
        HttpResponseEntity httpResponseEntity=new HttpResponseEntity();
        HashMap<String,byte[]> picc=userService.selectpic(username);
        if(picc==null){
            httpResponseEntity.setCode("500");
            return httpResponseEntity;
        }
        byte[] bytes1=picc.get("pic");
//        byte[] picc=userService.selectpic(username);




        int nihao=Integer.parseInt(FaceMatch.match(bytes,bytes1));
        UserEntity hasUser = userService.selectAllByName(username);
        if(nihao>80){
            httpResponseEntity.setData(hasUser);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            System.out.println(nihao);
            return httpResponseEntity;
        }else{
            httpResponseEntity.setCode("500");
            return httpResponseEntity;
        }

//        picture picc=new picture();
//        picc.setPic(bytes);
//        picc.setUsername(username);
//        userService.insertpic(picc);
//        HashMap<String,byte[]> picc=userService.selectpic(username);
//        byte[] bytes1=picc.get("pic");


//        for(int i=0;i<bytes.length;i++){
//            System.out.println(bytes[i]);
//        }
//        resultUrl = uploadService.uploadImage( bytes, ".png");
//        return ResponseEntity.ok(resultUrl);
    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity UserLogin(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String un = map.get("username").toString();
        String pw = map.get("password").toString();
        UserEntity hasUser = userService.selectAllByName(un);
        loginMess lo=new loginMess();
        lo.setUser_id(hasUser.getId());
        lo.setId(null);
        lo.setOperate_time(new Date());
        userService.insertLoginMess(lo);
        if (pw.equals(hasUser.getPassword())) {
            httpResponseEntity.setData(hasUser);
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage("登陆成功");
        }
        else {
            httpResponseEntity.setData(hasUser);
            httpResponseEntity.setCode("500");
            httpResponseEntity.setMessage("密码错误");
        }

        return httpResponseEntity;
    }

    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addUserInfo(@RequestBody UserEntity ue) throws ParseException {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//        String un = map.get("username").toString();
//        String pw = map.get("password").toString();
//        String unickName= map.get("unickName").toString();
//        String startTime = map.get("startTime").toString();
//        String stopTime = map.get("stopTime").toString();
//        String valid = map.get("valid").toString();
//        String createdBy = map.get("createdBy").toString();
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //UserEntity ue = new UserEntity();
//        ue.setId(un);
//        ue.setUsername(unickName);
//        ue.setPassword(pw);
//        ue.setStartTime(sdf.parse(sdf.format(Long.parseLong(startTime))));
//        ue.setStopTime(sdf.parse(sdf.format(Long.parseLong(stopTime))));
//        ue.setStatus(valid);
//        ue.setCreatedBy(createdBy);

        int r = userService.AddUser(ue);
//        ValueOperations<String, List<Object>> operations = redisTemplate.opsForValue();
//        String key=ue.getUsername();
//        for(String j:keySet){
//            if(j.indexOf(key)!=-1){
//                redisTemplate.delete(j);
//            }
//        }
        if (r==-1) {
            httpResponseEntity.setCode(Constans.USER_USERNAME_CODE);
            httpResponseEntity.setMessage("用户名已存在，添加失败！");
        }
        else {
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setData(ue);
        }

        return httpResponseEntity;
    }
//    @RequestMapping(value = "/queryUserList",method = RequestMethod.POST, headers = "Accept=application/json")
//    public HttpResponseEntity queryUserList(@RequestBody(required = false) UserEntity map) {
//        String username=map.getUsername();
//        if(username.equals(""))
//            return null;
//        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//        List<Object> result = userService.queryUserList(username);
//        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//        httpResponseEntity.setUser(result);
//        return httpResponseEntity;
//    }

    @RequestMapping(value = "/queryUserList",method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryUserList(@RequestBody(required = false) UserEntity map) {
        String username=map.getUsername();
        if(username.equals(""))
            return null;
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
//        ValueOperations<String, List<Object>> operations = redisTemplate.opsForValue();
//        boolean hasKey = redisTemplate.hasKey(username);
        List<Object> result=null;
//        if(hasKey){
//            result = operations.get(username);
//            System.out.println("result");
//        }else{
//            result = userService.queryUserList(username);
//            operations.set(username,result,5, TimeUnit.HOURS);
//            keySet.add(username);
//        }
        result = userService.queryUserList(username);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setUser(result);
        return httpResponseEntity;
    }

    @PostMapping(value = "/addUserInfoList")
    @ResponseBody
    public HttpResponseEntity addUserInfoList(HttpServletRequest request) throws Exception {
        if(request.getContentLengthLong()<2000){
            HttpResponseEntity httpResponseEntity=new HttpResponseEntity();
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            System.out.println("sdjgfhdsf");
            return httpResponseEntity;
        }
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while(headerNames.hasMoreElements()) {//判断是否还有下一个元素
//            String nextElement = headerNames.nextElement();//获取headerNames集合中的请求头
//            String header2 = request.getHeader(nextElement);//通过请求头得到请求内容
//            System.out.println(nextElement+"      "+header2);
//            //System.out.println(nextElement+":"+header2);
//        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("filename");
        if (file.isEmpty()) {
            return null;
        }
        InputStream inputStream = file.getInputStream();
        List<List<Object>> list = userService.getBankListByExcel(inputStream, file.getOriginalFilename());
        inputStream.close();

        for (int i = 0; i < list.size(); i++) {
            List<Object> lo = list.get(i);
            UserEntity ue=new UserEntity();
            if(lo.size()<7)
                continue;

            ue.setId(lo.get(0).toString());
            ue.setPassword(lo.get(2).toString());
            ue.setUsername(lo.get(1).toString());
            String key=lo.get(1).toString();
//            for(String j:keySet){
//                if(j.indexOf(key)!=-1){
//                    redisTemplate.delete(j);
//                }
//            }
            ue.setStartTime(new Date());
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(new Date());
            rightNow.add(Calendar.MONTH,1);
            ue.setRoleId(lo.get(5).toString());
            ue.setStatus(Integer.toString((int)Double.parseDouble(lo.get(6).toString())));
            ue.setStopTime(rightNow.getTime());
            int r = userService.AddUser(ue);
        }
        return null;
    }

//    @PostMapping(value = "/addUserInfoList")
//    public HttpResponseEntity addUserInfoList(@RequestBody List<Object> user) throws Exception {
//
//        HttpResponseEntity httpResponseEntity=new HttpResponseEntity();
//        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
//        return httpResponseEntity;
//    }

    @PostMapping(value = "/selectUserListToExcel")
    @ResponseBody
    public HttpResponseEntity selectUserListToExcel(HttpServletRequest request) throws Exception {
        HttpResponseEntity httpResponseEntity=new HttpResponseEntity();
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        return httpResponseEntity;
    }


}

