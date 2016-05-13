package net.xinhong.commons.utils;

import java.io.*;

/**
 * Description: <br>
 * Company: <a href=www.xinhong.net>新宏高科</a><br>
 *
 * @author 作者 邓帅
 * @version 创建时间：2016/4/6 0006.
 */
public class FileUtil {

    /**
     * 指定的文件中添加内容
     * @param file
     * @param content
     */
    public static void appendFile(String file, String content,boolean append) {

        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append)));
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String loadFile(String file){
        StringBuffer sb = new StringBuffer();
        BufferedReader bufferedReader =  null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String str = null;

            while ((str = bufferedReader.readLine())!=null){
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
