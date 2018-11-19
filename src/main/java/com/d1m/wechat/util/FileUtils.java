package com.d1m.wechat.util;

import com.d1m.wechat.exception.WechatException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    private static final Map<String, String> FILE_CONTENT_TYPE = new HashMap<String, String>() {
        /**
         *
         */
        private static final long serialVersionUID = 8490519861841433921L;

        {
            put("GIFImageReader", Constants.GIF);
            put("JPEGImageReader", Constants.JPG);
            put("PNGImageReader", Constants.PNG);
            put("BMPImageReader", Constants.BMP);
        }
    };

    public static String generateNewFileName(String fileName, String fileExt) {
        String format = DateUtil.yyyyMMddHHmmss.format(new Date());
        return format.substring(6) + "_" + Rand.getRandom(16)
                + (StringUtils.isNotBlank(fileName) ? ("_" + fileName) : "")
                + "." + fileExt;
    }

    public static File getUploadPathRoot(Integer wechatId, String type) throws WechatException {
        FileUploadConfigUtil instance = FileUploadConfigUtil.getInstance();
        String uploadPath = instance.getValue(wechatId, "upload_path");
        log.info("upload_path : " + uploadPath);
        File root = new File(uploadPath + File.separator + type);
        log.info("root exist : " + root.exists());
        if (root.exists()) {
            if (!root.isDirectory()) {
                log.error("root : " + root.getAbsolutePath()
                        + " is not directory.");
                throw new WechatException(Message.SYSTEM_ERROR);
            }
            if (!root.canWrite()) {
                log.error("root : " + root.getAbsolutePath()
                        + " can not write.");
                throw new WechatException(Message.SYSTEM_ERROR);
            }
        } else {
            log.info("mkdir root");
            root.mkdir();
        }
        return root;
    }

    public static File copyFile(String oldFileFullPath, String newFilePath,
                                String newFileName) {
        File oldFile = new File(oldFileFullPath);
        if (!oldFile.exists()) {
            return null;
        }
        File newFile = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(newFilePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String newFileFullPath = newFilePath + File.separator + newFileName;
            newFile = new File(newFileFullPath);
            if (newFile.exists()) {
                newFile.delete();
            }
            newFile.createNewFile();
            fileOutputStream = new FileOutputStream(newFileFullPath);
            byte[] buffer = new byte[1444];
            int read = 0;
            inputStream = new FileInputStream(oldFile);
            while ((read = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, read);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return newFile;
    }

    public static File copyRemoteFile(String remoteUrl, String newFilePath,
                                      String newFileName) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        File newFile = null;
        try {
            URL url = new URL(remoteUrl);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true);// 设置是否要从 URL 连接读取数据,默认为true
            uc.connect();
            inputStream = uc.getInputStream();
            File file = new File(newFilePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String newFileFullPath = newFilePath + File.separator + newFileName;
            newFile = new File(newFileFullPath);
            if (newFile.exists()) {
                newFile.delete();
            }
            newFile.createNewFile();
            fileOutputStream = new FileOutputStream(newFileFullPath);
            byte[] buffer = new byte[1444];
            int read = 0;
            while ((read = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, read);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return newFile;
    }

    public static String getRemoteImageSuffix(String remoteImageUrl,
                                              String defaultSuffix) {
        BufferedInputStream bis = null;
        HttpURLConnection urlconnection = null;
        URL url = null;
        ByteArrayInputStream bais = null;
        MemoryCacheImageInputStream mcis = null;
        String suffix = null;
        try {
            url = new URL(remoteImageUrl);
            urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.connect();
            bis = new BufferedInputStream(urlconnection.getInputStream());

            bais = new ByteArrayInputStream(IOUtils.toByteArray(bis));
            mcis = new MemoryCacheImageInputStream(bais);
            Iterator<ImageReader> itr = ImageIO.getImageReaders(mcis);
            while (itr.hasNext()) {
                ImageReader reader = (ImageReader) itr.next();
                String imageName = reader.getClass().getSimpleName();
                suffix = FILE_CONTENT_TYPE.get(imageName);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (mcis != null) {
                try {
                    mcis.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        if (StringUtils.isBlank(suffix)
                && StringUtils.isNotBlank(defaultSuffix)) {
            suffix = defaultSuffix;
        }
        return suffix;
    }

    /**
     * 判断文件的编码格式
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String codeString(String fileName) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
                fileName));
        int p = (bin.read() << 8) + bin.read();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }

        return code;
    }

    /**
     * Get file size of URL file.
     *
     * @param url URL
     * @return file length
     */
    public static int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }

    public static String generateWxFileName(String filePath) {
        String format = DateUtil.yyyyMMddHHmmssSSS.format(new Date());
        if (filePath.endsWith("wx_fmt=png")) {
            return format + ".png";
        }
        if (filePath.endsWith("wx_fmt=jpeg")) {
            return format + ".jpg";
        }
        if (filePath.endsWith("wx_fmt=gif")) {
            return format + ".gif";
        }
        return format;
    }

    public static File ExcelToCsv(File file, String csvFilePath) {
        StringBuilder buffer = new StringBuilder();
        try {
            // 设置读文件编码
            WorkbookSettings setEncode = new WorkbookSettings();
            setEncode.setEncoding("GB2312");
            // 从文件流中获取Excel工作区对象（WorkBook）
            Workbook wb = Workbook.getWorkbook(file, setEncode);
            Sheet sheet = wb.getSheet(0);

            for (int i = 0; i < sheet.getRows(); i++) {
                for (int j = 0; j < 11; j++) {
                    Cell cell = sheet.getCell(j, i);
                    buffer.append(cell.getContents().replaceAll("\n", " ")).append(",");
                }
                buffer = new StringBuilder(buffer.substring(0, buffer.lastIndexOf(",")).toString());
                buffer.append("\n");
            }
        } catch (BiffException | IOException e) {
            e.printStackTrace();
        }
        //write the string into the file
        File saveCSV = new File(csvFilePath);
        try {
            if (!saveCSV.exists())
                saveCSV.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveCSV));
            writer.write(buffer.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveCSV;
    }
}
