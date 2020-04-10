package com.lamandys.proguardrulegenerate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by lamandys on 2020/4/10 9:49 AM.
 * 查阅字码网站： https://www.cnblogs.com/csguo/p/7401874.html
 */
public class ProguardGenerateClass {

    private static final String ROOT_PATH = System.getProperty("user.dir") + "/app/";
    private static Random random = new Random();

    public static void main(String[] args) {
        int start = 0x0100;
        int end = 0xDFFF;
        List<String> unicodeList = new ArrayList<>(end - start);
        for (int i = start; i < end; i++) {
            char c = (char) i;
            String s = String.valueOf(c);
            unicodeList.add(s);
        }
        Collections.sort(unicodeList);
        File file = new File(ROOT_PATH, "output_dict.txt");
        if (file.exists()) {
            System.out.println("文件已存在，删除");
            file.delete();
        } else {
            System.out.println("文件不存在");
        }

        String filePath = ROOT_PATH + "comm_dict.txt";
        List<String> commList = new ArrayList<>();
        String encoding = "UTF-8";
        try {
            File file1 = new File(filePath);
            if (file1.isFile() && file1.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file1), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    commList.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (int i = 0; i < commList.size(); i++) {
                String aa = commList.get(i);
                String tmp;
                if (aa.length() > 2) {
                    int index = (int) Math.floor(aa.length() / 2.0);
                    tmp = aa.substring(index) + unicodeList.get(i) + commList.get(i).substring(index, aa.length()) + getRandomString(unicodeList) + unicodeList.get(i);
                } else {
                    tmp = commList.get(i) + unicodeList.get(i) + getRandomString(unicodeList) + unicodeList.get(i);
                }
                fileOutputStream.write(tmp.getBytes(encoding));
                fileOutputStream.write('\n');
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRandomString(List<String> list) {
        String tm;
        while (true) {
            int s = random.nextInt(4000);
            if (s < list.size()) {
                tm = list.get(s);
                break;
            }
        }
        return tm;
    }
}
