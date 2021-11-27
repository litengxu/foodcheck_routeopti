package com.bjfu.fcro.common.utils;

import com.bjfu.fcro.common.exception.BizException;
import com.bjfu.fcro.common.exception.CommonEnum;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class OfficeTool {
    protected static final Logger logger = LoggerFactory.getLogger(OfficeTool.class);

    public static Map<Integer, Map<Integer, Object>> readExcelContentz(File file) throws Exception {
            Map<Integer, Map<Integer, Object>> content = new HashMap<Integer, Map<Integer, Object>>();
            // 上传文件名
            Workbook wb = getWb(file);
            //文件格式错误，抛出异常
            if (wb == null) {
                throw new BizException(CommonEnum.NO_EXCEl);
            }
            //得到第一个sheet
            Sheet sheet = wb.getSheetAt(0);
            // 得到总行数
            int rowNum = sheet.getLastRowNum();
                   /*得到总列数*/
            //保存合并的单元格的内容
            Object temp = null;
            for(int i=0;i<=rowNum;i++){
                  /*得到第1行*/
                Row row = sheet.getRow(i);
                   /*得到总列数*/
                int colNum = row.getPhysicalNumberOfCells();
                Map<Integer, Object> cellValue = new HashMap<>();
                //从1开始是因为第一列是序号列
                for(int  j=1;j<colNum;j++){
                    //可以把row设置为String类型
//                    row.getCell(j).setCellType(CellType.STRING);

                    String value = row.getCell(j).getStringCellValue();
                    //判断单元格是否是合并单元格
                    int res = isMergedRegion(sheet,i,j);
                    //排除辖区这一行
                    if( value.equals("辖区")){
                        break;
                    }
//                    如果某一行是一个合并的单元格，则跳过这一行 返回-1代表合并的单元格为1行
                    if(res == -1){
                        break;
                    }
                    //如果是合并的单元格，且单元格中有内容，则更新temp的值，会给是合并单元格但没有值的使用
                    if(res == 1 && value.length()>0){
                        temp = row.getCell(j);
                    }
                    //判断单元格是否是合并单元格，是则插入temp，不是则直接取值
                    if(row.getCell(j).getCellType() == Cell.CELL_TYPE_BLANK || row.getCell(j) == null){
                        cellValue.put(j,temp);
                    }else{
                        cellValue.put(j,row.getCell(j));
                    }
                }
                if(cellValue.size() > 0){
                    content.put(i,cellValue);
                }
            }
            return content;
        }
    //根据Cell类型设置数据
    private static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case NUMERIC:
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                    break;
                case FORMULA: {
                    cellvalue = cell.getDateCellValue();
                    break;
                }
                case STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }
    /*转成输入流到workbook，并判断文件的格式*/
    private static Workbook getWb(File mf)  throws Exception{
        if(mf == null){
            System.out.println("mf为空");
        }
        if(mf == null){
            System.out.println("mf为空");
        }
        String filepath = mf.getName();
        String ext = filepath.substring(filepath.lastIndexOf("."));
        Workbook wb = null;
        InputStream is = null;

        try {
            is = new FileInputStream(mf);
//            is = mf.getInputStream();
            if (".xls".equals(ext)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(ext)) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = null;
            }
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        }finally {
            is.close();
        }
        return wb;
    }


    /**
     * 判断是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private  static int isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    if (firstRow == lastRow) {
                        //当前合并单元格为一行
                        return -1;
                    }
                    //当前是合并单元格
                    return 1;
                }
            }
        }
        //当前不是合并单元格
        return 0;
    }
    //判断单元格是否为空或者没有数据
    public boolean isCellEmpty(XSSFCell cell) {
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            //为空时返回true
            return true;
        } else {
            //不为空时返回false
            return false;
        }
    }

    /**
     * 文件统一保存在static下，根据传入的path保存
     * @param file 上传的文件
     * @param path 保存的文件夹  	路径格式：\\AAA\\BBB
     * @return
     */
    public static String uploadAndReturnPath(MultipartFile file , String path , String fileName,boolean img){
        //1.上传文件，并且获取到文件的保存路径
        String filePath = "";
        try {
            File pathRoot = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!pathRoot.exists()) {
                pathRoot = new File("");
            }
            /**由于会修改classes文件，项目会重新启动    所以下面的注释需要等到利用jar包部署服务器后使用*/
            String osName = System.getProperty("os.name");
            String saveFile = null;

            if (Pattern.matches("Linux.*", osName)) {
                if(img == false){
                    //            linux 使用 /
                  saveFile = pathRoot.getAbsolutePath().replace("%20"," ").replace('/', '/')+"/files"+"/"+path;

                }else{
                    //            linux 使用 /
                  saveFile = pathRoot.getAbsolutePath().replace("%20"," ").replace('/', '/')+"/files/images"+"/"+path;
                }
            } else if (Pattern.matches("Windows.*", osName)) {
                if(img == false){
                    //            window使用 \
//                  saveFile = pathRoot.getAbsolutePath().replace("%20"," ").replace('/', '\\')+"\\files"+"\\"+path;
                    saveFile = pathRoot.getAbsolutePath().replace("%20"," ").substring(0,pathRoot.getAbsolutePath().replace("%20"," ").length()-15).replace('/', '\\')+"\\files"+"\\"+path;

                }else{
                    //            window使用 \
//                saveFile = pathRoot.getAbsolutePath().replace("%20"," ").replace('/', '\\')+"\\files\\images"+"\\"+path;
                    saveFile = pathRoot.getAbsolutePath().replace("%20"," ").substring(0,pathRoot.getAbsolutePath().replace("%20"," ").length()-15).replace('/', '\\')+"\\files\\images"+"\\"+path;
                }
            }
//            windows下使用下面这个
//            String saveFile = pathRoot.getAbsolutePath().replace("%20"," ").substring(0,pathRoot.getAbsolutePath().replace("%20"," ").length()-15).replace('/', '\\')+"\\files"+"\\"+path;
            File f = new File(saveFile);
            if(!f.exists()) {
                f.mkdirs();
            }
            FileOutputStream writer = new FileOutputStream(new File(f , fileName));
            writer.write(file.getBytes());
            writer.flush();
            writer.close();
            //文件路径
            if (Pattern.matches("Linux.*", osName)) {
                //            linux下使用
                filePath = saveFile + "/" + fileName;
            } else if (Pattern.matches("Windows.*", osName)) {
                //            windows下使用
                filePath = saveFile + "\\" + fileName;
            }



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return filePath;
    }

    /**文件下载*/
    public static void download(String filename, HttpServletResponse res) throws IOException {
        File pathRoot = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!pathRoot.exists()) {
            pathRoot = new File("");
        }
        String osName = System.getProperty("os.name");
        String saveFile = null;
        OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        /*
        * window 下
        *       pathRoot E:\ltx\foodcheck_routeopti  可以用于存文件
        *       但是根据file取文件，需要用到 /,所以要把 \ 替换为 /
        * Linux 下
        *        pathRoot不存在 所以要pathRoot = new File("");获取新的pathRoot
        *        获取到为/usr/software/mysoft/files/images/
        *   */
        if (Pattern.matches("Linux.*", osName)) {
            saveFile = pathRoot.getAbsolutePath().replace("%20"," ").replace('/', '/')+"/files"+"/"+"template";
            bis = new BufferedInputStream(new FileInputStream(new File(saveFile+"/" + filename)));
        } else if (Pattern.matches("Windows.*", osName)) {
            saveFile = pathRoot.getAbsolutePath().replace("%20"," ").substring(0,pathRoot.getAbsolutePath().replace("%20"," ").length()-15).replace('/', '\\')+"\\files"+"\\"+"template";
            bis = new BufferedInputStream(new FileInputStream(new File(saveFile+"\\" + filename)));
        }
        /**由于会修改classes文件，项目会重新启动    所以下面的注释需要等到利用jar包部署服务器后使用*/
//     String saveFile = pathRoot.getAbsolutePath().replace("%20"," ").replace('/', '\\')+"\\files"+"\\"+template;
        // 发送给客户端的数据

        // 读取filename
        int i = bis.read(buff);
        while (i != -1) {

            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
    }

    /**文件下载*/
    public static void  download2( HttpServletResponse response,String filename) throws IOException {
        // 下载本地文件
        File pathRoot = new File(ResourceUtils.getURL("classpath:").getPath());
        if(!pathRoot.exists()) {
            pathRoot = new File("");
        }
        String osName = System.getProperty("os.name");
        String saveFile = null;
        String path = null;
        if (Pattern.matches("Linux.*", osName)) {
            saveFile = pathRoot.getAbsolutePath().replace("%20"," ").replace('/', '/')+"/files"+"/"+"template";
            path = saveFile+"/" + filename;
            //            bis = new BufferedInputStream(new FileInputStream(new File(saveFile+"/" + filename)));
        } else if (Pattern.matches("Windows.*", osName)) {
            saveFile = pathRoot.getAbsolutePath().replace("%20"," ").substring(0,pathRoot.getAbsolutePath().replace("%20"," ").length()-15).replace('/', '\\')+"\\files"+"\\"+"template";
            path = saveFile+"\\" + filename;
            //            bis = new BufferedInputStream(new FileInputStream(new File(saveFile+"\\" + filename)));
        }
//        InputStream inStream = new FileInputStream("F:\\Java\\foodcheck_routeopti\\files\\template\\templateexcel.xlsx");// 文件的存放路径
        InputStream inStream = new FileInputStream(path);// 文件的存放路径

        // 设置输出的格式
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename,"utf-8"));
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

}
