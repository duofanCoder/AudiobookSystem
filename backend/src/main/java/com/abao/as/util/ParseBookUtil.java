package com.abao.as.util;


import com.abao.as.dto.model.business.ChapterDto;
import com.abao.as.exception.type.OwnerException;
import com.abao.as.model.business.Chapter;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;


public class ParseBookUtil {

    public static List<ChapterDto> parseBook(String bookPath) {
        LinkedList<ChapterDto> chapterLinkedList = new LinkedList<>();
        try {
            // 编码格式
            String encoding = "utf8";
            // 文件路径
            File file = new File(bookPath);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                // 输入流
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int count = 0;
                //小说内容类
                ChapterDto content = null;
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
                            chapterLinkedList.add(content);
                        }
                        isFirstChapterShow = true;
                        String chapterName = matcher.group().trim();
                        content = new ChapterDto();
                        content.setQueue(count++);
                        content.setName(chapterName);
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
                throw new OwnerException("找不到指定的文件");
            }
        } catch (Exception e) {
            throw new OwnerException("读取文件内容出错");
        }
        return chapterLinkedList;
    }

}

