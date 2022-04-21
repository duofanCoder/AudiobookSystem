package com.abao.as;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    /**
     *〈简述〉解析章节
     *〈详细描述〉
     * @author sjk
     * @param book Book book
     */
    public static ArrayList<BookContent> parse(Book book) {
        String path = book.getBookDownPath();
        ArrayList<BookContent> list = new ArrayList<>();
        if (!path.contains("G:/books/")) {
            path = "G:/books" + path.substring(6);
        }
        try {
            // 编码格式
            String encoding = "GBK";
            // 文件路径
            File file = new File(path);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                // 输入流
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                Long count = (long) 0;
                boolean bflag=false;
                int n=0;
                String newStr=null;
                String titleName=null;
                String newChapterName = null;//新章节名称
                String beforeChapterName = null; // 记录上一章 章节名
                String substring=null;
                int indexOf=0;
                int indexOf1=0;

                int line=0;
                //小说内容类
                BookContent content;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    content=new BookContent();
                    //小说名称
                    // content.setName(book.getBookDownName());
                    count++;
                    // 正则表达式
                    Pattern p = Pattern.compile("(^\\s*第)(.{1,9})[章节卷集部篇回](\\s{1})(.*)($\\s*)");
                    Matcher matcher = p.matcher(lineTxt);
                    Matcher matcher1 = p.matcher(lineTxt);
                    newStr=newStr + lineTxt;
                    while (matcher.find()) {
                        titleName = matcher.group();
                        //章节去空
                        newChapterName = titleName.trim();
                        //获取章节
                        //System.out.println(newChapterName);
                        content.setChapter(beforeChapterName);
                        beforeChapterName = newChapterName;
                        indexOf1=indexOf;
                        //System.out.println(indexOf);
                        indexOf = newStr.indexOf(newChapterName);
                        // System.out.println(newChapterName + ":" + "第" + count + "行"); // 得到返回的章
                        if(bflag) {
                            bflag=false;
                            break;
                        }
                        if(n==0) {
                            indexOf1 = newStr.indexOf(newChapterName);
                        }
                        n=1;
                        bflag=true;
                        //System.out.println(chapter);
                    }
                    while(matcher1.find()) {
                        // 若indexOf1 < indexOf 就说明图书有章节出错，此时跳过 到时在后台补加
                        //  if(indexOf!=indexOf1 ) {
                        if (indexOf1 < indexOf) {
                            //根据章节数量生成
                            if (line > 300) {
                                return list;
                            }
                            content.setNumber(++line);
                            System.out.println("--" + indexOf1 + "截取" + indexOf + "--");
                            substring = newStr.substring(indexOf1, indexOf);
                            //System.out.println(substring);
                            content.setContent(substring);
                            content.setBookId(book.getId());
                            list.add(content);
                            //bookContentManager.save(content);
                            System.out.println(content.toString());
                        } else {
                            System.out.println("错误章节：" + newChapterName);
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
        return list;
    }
}

