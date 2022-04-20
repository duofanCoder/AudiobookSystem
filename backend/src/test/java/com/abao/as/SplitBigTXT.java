package com.abao.as;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SplitBigTXT {
    /**
     * 需要传入的参数
     */
    // 源文件路径
    private String srcPath ;
    // 目标文件夹
    private String destDirPath;
    // 每几章分割一部分
    private int chaps;

    /**
     * 需要计算的值
     */
    // 文件名称
    private String fileName;
    // 文件的大小
    private long fileSize;
    // 存储每一部分起始位置和终点位置的
    private List<Long> rowsList;
    // 目标路径的集合
    private List<String> destFiles;
    // 分多少个子文件
    private int size;

    /**
     * 有参构造器
     * @param srcPath 源文件的路径字符串
     * @param destDirPath 目标目录的路径字符串
     * @param chaps 每几章分一个子包
     */
    public SplitBigTXT(String srcPath,int chaps) {
        // 方法重载
        this(srcPath,getDestDir(srcPath),chaps);
    }

    /**
     * 根据源文件路径生成目标文件夹
     * @param srcPath
     * @return
     */
    private static String getDestDir(String srcPath) {
        String[] srcNames = srcPath.split("\\.");
        return srcNames[0];
    }

    /**
     * 重载构造器
     * 有参构造器
     * @param srcPath 源文件的路径字符串
     * @param destDirPath 目标目录的路径字符串
     * @param chaps 每几章分一个子包
     */
    public SplitBigTXT(String srcPath,String destDirPath,int chaps) {
        // 赋值
        this.srcPath = srcPath;
        this.destDirPath = destDirPath;
        this.chaps = chaps;
        // 其余另外需要计算的值另外用方法
        init(chaps,srcPath,destDirPath);
    }

    /**
     * 初始化的辅助方法。
     * 		用来初始化需要根据传入的参数计算出初始值的属性
     */
    private void init(int chaps, String srcPath, String destDirPath) {


        // 文件名称
        this.fileName = new File(this.srcPath).getName();

        // fileSize 初始化
        File srcFile = new File(this.srcPath);
        this.fileSize = srcFile.length();

        // rowsList 位置行数集合
        getChapsRows();

        // 一共分割成多少个子文件
        size = (int)Math.ceil(rowsList.size() * 1.0 / chaps);

        // destFiles 初始化
        this.destFiles = new ArrayList<>();
        // 分割源文件存放的路径和子文件的命名
        String[] fileNames = this.fileName.split("\\.");

        for (int i=0; i<size; i ++) {
            // 将分割的子文件名，存入到 destFiles 集合中。
            this.destFiles.add(destDirPath + File.separator + fileNames[0] + "-part_" + (i+1) + "." + fileNames[1]);
        }

    }

    /**
     * 辅助方法：初始化 rowsList
     * 章节名的正则为： 第.{1,7}[章节回][\s\n]
     */
    private void getChapsRows() {
        // 初始化 rowsList
        this.rowsList = new ArrayList<>();

        /**
         * 思路：使用 BufferedReader 的 readLine() 方法读取，匹配章节正则。
         */
        // 创建计数器：记录行数
        long row = 0L;
        // 创建BufferedReader 流
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.srcPath),"UTF-8"))) {
            // 创建正则表达式
            Pattern p = Pattern.compile("第.{1,8}[章节回][\\s\\n]");
            // 匹配对象
            Matcher m = null;
            // 读取数据
            String line = null;
            // 读取并匹配
            while((line=br.readLine())!=null) {
                // 行数 + 1
                row += 1;
                // 如能匹配上，则为章节行
                m = p.matcher(line);
                if (m.find()) {
                    // 满足条件，记录行数
                    this.rowsList.add(row);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分割子文件
     */
    private void split() {
        // 创建目标文件夹
        File destDir = new File(this.destDirPath);
        if(!destDir.exists()) {
            destDir.mkdirs();
        }

        // 每个分割成的子文件包括的行数
        for(int i=1; i<=size; i++) {
            if(i == 1) { // 第一个文件的大小
                splitDetail(i,1L,rowsList.get(i*chaps)-1);
            } else if (i == size) { // 最后一个子文件
                splitDetail(i,rowsList.get((i-1)*chaps));
            }else { // 普通子文件的行数
                splitDetail(i,rowsList.get((i-1)*chaps),rowsList.get(i*chaps)-1);
            }
        }
    }

    /**
     * 分割细节
     * @param longs 起始和终止行
     */
    private void splitDetail(int num,Long...longs) {


        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(this.srcPath),"UTF-8"));
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(this.destFiles.get(num-1)),"UTF-8"))) {

            // 行数计数器
            long row = 0L;
            // 读取数据
            String line = null;

            // 判断一下是否是最后一个子文件
            if(num==size) { // 但是最后一个时，从给定的起始位置一致写到文档最后
                while((line=br.readLine())!=null) {
                    // 行数 + 1
                    row += 1;
                    if(row>=longs[0]) { // 从给定的起始位置一致写到最后即可
                        bw.write(line + "\n"); // 手动添加换行符
                    }
                }
                // 刷滞留数据
                bw.flush();
            } else {
                while((line=br.readLine())!=null) {
                    // 行数 + 1
                    row += 1;
                    if(row>=longs[0] && row<=longs[1]) { // 只写入给定的起始位置之间的内容
                        bw.write(line + "\n"); // 手动添加换行符
                    }
                }
                // 刷滞留数据
                bw.flush();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    /**
     * 程序入口 ： main 方法
     * @param args
     */
    public static void main(String[] args) {
        // 源文件的路径字符串
        String srcPathStr = "F:\\splitTxt\\世界百科全书.txt";
        // 每多少章分割成一个
        int chaps = 1000;

        // 初始化实例
        SplitBigTXT sbt = new SplitBigTXT(srcPathStr,chaps);
        // 开始时间
        long start = System.currentTimeMillis();
        // 分割
        sbt.split();
        // 结束时间
        long end = System.currentTimeMillis();
        System.out.println("完成！\n共耗时：" + (end - start) + " 毫秒！");

    }

}


