package com.abao.as;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 版权：(C) 版权所有 ****分公司
 * <简述>
 * <详细描述>
 *
 * @author sjk
 * @see
 * @since
 */
public class ParseBookContentUtil {

    //    ------------
//
//    第二章 指导战
    public static void main(String[] args) {
        String fileNamedirs = "E:\\Project\\xyDemo\\AudiobookSystem\\backend\\storage\\仙缘剑路.txt";
        try {
            // 编码格式
            String encoding = "utf8";
            // 文件路径
            File file = new File(fileNamedirs);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                // 输入流
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                Long count = (long) 0;
                //小说内容类
                Content content = null;
                boolean isFirstChapterShow = false;
                boolean findFlag = true;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //小说名称
                    // 正则表达式
                    Pattern p = Pattern.compile("(^\\s*第)(.{1,9})[章节卷集部篇回](\\s{1})(.*)($\\s*)");
                    Matcher matcher = p.matcher(lineTxt);
                    boolean tmpFlag = findFlag;
                    findFlag = matcher.find();
                    // 上一行没找到，这一行找到了
                    if (findFlag && !tmpFlag) {
                        if (content != null) {
                            System.out.println(content);
                        }
                        isFirstChapterShow = true;
                        String chapterName = matcher.group().trim();
                        content = new Content();
                        content.setName("宇宙职业选手");
                        content.setId(count++);
                        content.setChapter(chapterName);
                        content.setContent("");
                        continue;
                    }
                    if (isFirstChapterShow) {
                        if (content != null && content.getContent() != null) {
                            if (lineTxt.trim().length() == 0) {
                                content.setContent(content.getContent() + '\n');
                            } else {
                                content.setContent(content.getContent() + lineTxt);
                            }
                        }

                    }

                }
                bufferedReader.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

}

