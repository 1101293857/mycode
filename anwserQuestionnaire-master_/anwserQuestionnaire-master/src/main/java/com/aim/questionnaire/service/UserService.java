package com.aim.questionnaire.service;

        import com.aim.questionnaire.dao.UserEntityMapper;
        import com.aim.questionnaire.dao.entity.UserEntity;
        import com.aim.questionnaire.dao.entity.loginMess;
        import com.aim.questionnaire.dao.entity.people;
        import com.aim.questionnaire.dao.entity.picture;
        import org.apache.poi.hssf.usermodel.HSSFWorkbook;
        import org.apache.poi.ss.usermodel.Cell;
        import org.apache.poi.ss.usermodel.Row;
        import org.apache.poi.ss.usermodel.Sheet;
        import org.apache.poi.ss.usermodel.Workbook;
        import org.apache.poi.xssf.usermodel.XSSFWorkbook;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.web.multipart.MultipartFile;

        import java.io.InputStream;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserEntityMapper userEntityMapper;

    public UserEntity selectAllByName(String username) {
        UserEntity hasUser = userEntityMapper.selectAllByName(username);

        return hasUser;
    }

    public int AddUser(UserEntity ue) {
        return userEntityMapper.insert(ue);
    }

    public List<Object> queryUserList(String username) {
        List<Object> resultList = new ArrayList<Object>();


        List<Map<String,Object>> userResult = userEntityMapper.queryUserList(username);
        for(Map<String,Object> userObj : userResult) {
            resultList.add(userObj);
        }
        System.out.println(resultList.size());
        return resultList;
    }

    public HashMap<String,byte[]> selectpic(String username){
        return userEntityMapper.selectpic(username);
    }


    /**
     * 处理上传的文件
     *
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    public List getBankListByExcel(InputStream in, String fileName) throws Exception {
        List list = new ArrayList<>();
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }

            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }

                List<Object> li = new ArrayList<>();
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    li.add(cell);
                }
                list.add(li);
            }
        }
        System.out.println("nihao"+list.size());
        return list;
    }

    /**
     * 判断文件格式
     *
     * @param inStr
     * @param fileName
     * @return
     * @throws Exception
     */
    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inStr);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("请上传excel文件！");
        }
        return workbook;
    }

    public List<people> queryProblemList() {
//        List<people> resultList = new ArrayList<people>();
//
//
//        List<Map<String,people>> userResult = userEntityMapper.queryProblemList();
//        for(Map<String,people> userObj : userResult) {
//            resultList.add((people) userObj);
//        }
//        return resultList;
        return userEntityMapper.queryProblemList();
    }

    public int insertLoginMess(loginMess login){
        return userEntityMapper.insertLoginMess(login);
    }

    public int insertpic(picture picc){
        return userEntityMapper.insertpic(picc);
    }
}

