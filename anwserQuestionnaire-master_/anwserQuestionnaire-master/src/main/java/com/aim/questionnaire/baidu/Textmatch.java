package com.aim.questionnaire.baidu;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 人脸对比
*/
public class Textmatch {

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */

    public  String match(String text1, String text2) {
        // 请求url
        String url = "https://aip.baidubce.com/rpc/2.0/nlp/v2/simnet";
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




            Map<String, Object> map1 = new HashMap<>();
            //请求参数详情，看百度人脸对比开发文档
            //https://ai.baidu.com/ai-doc/FACE/Lk37c1tpf
            map1.put("text_1", text1);
            map1.put("text_2", text2);


            String  param= JSON.toJSONString(map1);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            // 【调用鉴权接口获取的token】
            String accessToken = "24.d4fc7e0b54729336e5a415f167af7867.2592000.1626579388.282335-24392921";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            JSONObject jsonObject =  JSON.parseObject(result);

            if(jsonObject.get("error_code")!=null)
                return "0";

            return jsonObject.get("score").toString();
//            JSONObject jsonObject =  JSON.parseObject(result);
//            Object res=jsonObject.get("result");
//            String jj=jsonObject.get("error_msg").toString();
//            if(!jj.equals("SUCCESS"))
//                return "0";
//            String nihao=res.toString().substring(9,11);
////            System.out.print(nihao);
//            return nihao;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//    public static void main(String[] args) throws IOException {
//        Textmatch text=new Textmatch();
//        text.match("我是赖煜华","我是大傻逼");
//    }
}