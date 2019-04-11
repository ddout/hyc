package com.ddout.hyc.file;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <pre>
 *     JSONObject param = new JSONObject();
 *         param.put("targetPath", "D:/BBB/zip/");
 *         param.put("targetFileName", "testZIP.zip");
 *         JSONArray arr = new JSONArray();
 *         //
 *         JSONObject item1 = new JSONObject();
 *         item1.put("source", "D:/BBB/zip/test/222.jpg");
 *         item1.put("target", "222.jpg");
 *         arr.add(item1);
 *         //
 *         item1 = new JSONObject();
 *         item1.put("source", "D:/BBB/zip/test/91610900MA70J05L7Y.gif");
 *         item1.put("target", "91610900MA70J05L7Y.gif");
 *         arr.add(item1);
 *         //
 *         item1 = new JSONObject();
 *         item1.put("source", "D:/BBB/zip/test/MMM/dddd_aaaa.pdf");
 *         item1.put("target", "MMM/dddd_aaaa.pdf");
 *         arr.add(item1);
 *         //
 *         item1 = new JSONObject();
 *         item1.put("source", "D:/BBB/zip/test/MMM/x.jpg");
 *         item1.put("target", "MMM/x.jpg");
 *         arr.add(item1);
 *         //
 *         item1 = new JSONObject();
 *         item1.put("source", "D:/BBB/zip/test/bbb/test3.png");
 *         item1.put("target", "bbb/test3_1239876789.png");
 *         arr.add(item1);
 *         //
 *         param.put("targetArr", arr);
 *
 *         ZipMultiFile(param);
 * </pre>
 */
public class ZipUtil {


    /**
     * 多文件文件压缩
     *
     * @param param <pre>
     *              param.targetPath     zip文件的目录
     *              param.targetFileName zip文件的名称
     *              param.targetArr      [{ source:"源文件绝对路径" target:"在zip文件中的相对路径" }]
     *              </pre>
     */
    public static void ZipMultiFile(JSONObject param) {
        JSONArray targetArr = param.getJSONArray("targetArr");
        ZipOutputStream zipOut = null;
        try {
            if (targetArr.size() == 0) {
                throw new RuntimeException("targetArr size is 0");
            }
            //
            String targetPath = param.getString("targetPath");
            String targetFileName = param.getString("targetFileName");
            File zipFile = new File(targetPath);
            if (!zipFile.isDirectory()) {
                throw new RuntimeException("targetPath is not Directory");
            }
            if (!zipFile.exists()) {
                zipFile.mkdirs();
            }
            zipOut = new ZipOutputStream(new FileOutputStream(targetPath + "/" + targetFileName));
            //
            for (int i = 0; i < targetArr.size(); i++) {
                JSONObject item = targetArr.getJSONObject(i);
                String source = item.getString("source");
                String target = item.getString("target");
                String sourceFile = source;
                InputStream input = new FileInputStream(sourceFile);
                zipOut.putNextEntry(new ZipEntry(target));
                int len = -1;
                byte[] b = new byte[1024];
                while ((len = input.read(b)) != -1) {
                    zipOut.write(b, 0, len);
                }
                input.close();
                zipOut.flush();
            }
            zipOut.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != zipOut) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
