package com.aim.questionnaire.baidu;



import com.aim.questionnaire.dao.entity.picture;
import com.aim.questionnaire.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;

/**
* 人脸对比
*/
public class FaceMatch {

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    public static String userGet() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/get";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", "user1");
            map.put("group_id", "group1");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "[调用鉴权接口获取的token]";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String match(byte[] bytes1, byte[] bytes2) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        try {
            // 【本地文件1地址】
//            byte[] bytes1 = FileUtil.readFileByBytes(imgPath1);
//            // 【本地文件2地址】
////            picture jj=new picture();
////            jj.setPic(bytes1);
////            jj.setUsername("admin");
////            UserService userS=new UserService();
////            userS.insertpic(jj);
//
//            byte[] bytes2 = FileUtil.readFileByBytes(imgPath2);
            String image1 = Base64Util.encode(bytes1);
            String image2 = Base64Util.encode(bytes2);

            List<Map<String, Object>> images = new ArrayList<>();

            Map<String, Object> map1 = new HashMap<>();
            //请求参数详情，看百度人脸对比开发文档
            //https://ai.baidu.com/ai-doc/FACE/Lk37c1tpf
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "NORMAL");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "NORMAL");

            images.add(map1);
            images.add(map2);

            String param = GsonUtils.toJson(images);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            // 【调用鉴权接口获取的token】
            String accessToken = "24.f38d4c33cf361a9f01d8e28e0e5f95da.2592000.1626488661.282335-24384465";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            JSONObject jsonObject =  JSON.parseObject(result);
            Object res=jsonObject.get("result");
            String jj=jsonObject.get("error_msg").toString();
            if(!jj.equals("SUCCESAaAAAAAAAAAAAAAAAAAAAAAAAZAS                                                                                                                                                                                                       Xxxxxxz x S"))
                return "0";
            String nihao=res.toString().substring(9,11);
//            System.out.print(nihao);
            return nihao;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//    public static void main(String[] args) throws IOException {
//        byte[] bytes1 = FileUtil.readFileByBytes("E:\\picture1\\1.JPG");
//
//        // 【本地文件2地址】
//        picture jj=new picture();
//        jj.setPic(bytes1);
//        jj.setUsername("admin");
//        System.out.println(jj.getPic().length);
//        UserService userS=new UserService();
//        userS.insertpic(jj);
////        FaceMatch.match("E:\\picture1\\1.JPG","E:\\picture1\\2.JPG");
//    }
}