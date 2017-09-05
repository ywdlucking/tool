package com.ywd.tool.execl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by admin on 2017/8/16.
 */
public class ReadXmlUtil {

    public static void main(String[] args) throws Exception {
        String rootPath = System.getProperty("user.dir");
        File file = new File(rootPath+"/data/销售地区.xlsx");
        String rs = read(file);
        File to = new File("C:/workspace/json.txt");
        if (!to.exists()){
            to.createNewFile();
        }
        FileWriter fw = new FileWriter(to);
        fw.write(rs);
        fw.flush();
        fw.close();
        System.out.println(rs);
    }

    private static String read(File file) throws Exception {
        InputStream ExcelFileToRead = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;
        int provincecount = 1;
        int citycount = 1;
        int areacount = 1;
        boolean isFirstLevel = false;
        boolean isTwoLevel = false;
        Iterator rows = sheet.rowIterator();
        rows.next();
        DataFormatter formatter = new DataFormatter();
        JSONArray root = new JSONArray();
        JSONArray cityArray = new JSONArray();
        JSONArray areaArray = new JSONArray();
        JSONObject province = new JSONObject();
        JSONObject city = new JSONObject();
        JSONObject area = new JSONObject();
        while (rows.hasNext()) {
            row = (XSSFRow) rows.next();
            if (row == null) {
                return null;
            }
            int lastCellNum = row.getLastCellNum();
            for (int j = 1; j <= lastCellNum; j++) {
                cell = row.getCell(j);
                String value = formatter.formatCellValue(cell).trim();
                if (j == 1) {
                    if (cell != null) {
                        if (!value.equals("")) {
                            if (isFirstLevel) {

                                if (isTwoLevel){
                                    city.put("child", areaArray);
                                    cityArray.add(city);
                                    province.put("child", cityArray);
                                }else {
                                    province.put("child", areaArray);
                                }
                                root.add(province);
                                isFirstLevel = false;
                                isTwoLevel = false;
                                cityArray.clear();
                                areaArray.clear();
                            }
                            citycount = 1;
                            areacount = 1;
                            isFirstLevel = true;
                            province.put("id", String.valueOf(provincecount));
                            province.put("value", value);
                            provincecount++;
                            System.out.println("province value is : " + value.trim());
                        }
                    }
                } else if (j == 2) {
                    if (cell != null) {
                        if (!value.equals("")) {
                            if (isTwoLevel) {
                                city.put("child", areaArray);
                                cityArray.add(city);
                                areaArray.clear();
                                areacount = 1;
                            }
                            isTwoLevel = true;
                            city.put("id", String.valueOf(citycount));
                            city.put("value", value);
                            System.out.println("city value is : " + value.trim());
                            citycount++;
                        }
                    }
                } else if (j == 3) {
                    if (cell != null) {
                        if (!value.equals("")) {
                            area.put("id", String.valueOf(areacount));
                            area.put("value", value);
                            areaArray.add(area);
                            area.clear();
                            System.out.println("area value is : " + value.trim());
                            areacount++;
                        }
                    }
                }
            }
        }
        if (isTwoLevel){
            city.put("child", areaArray);
            cityArray.add(city);
            province.put("child", cityArray);
        }else {
            province.put("child", areaArray);
        }
        root.add(province);
        return root.toString();
    }
}
