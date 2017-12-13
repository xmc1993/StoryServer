package cn.edu.nju.software.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by zhangsong on 2017/12/12.
 */
public class SensitiveWordsUtil {
    private static String ENCODING = "GBK";    //字符编码

    private static HashMap sensitiveWordMap;

    /*匹配规则介绍：
    比如说我是中国人，铭感词库里面有“中国”、“中国人”，最小规则就变成了：我是**人、最大规则就是：我是***。
    所以最小规则就是：找到敏感词就结束，最大规则就是：找到最底层的那个敏感词。两个深度不一样！*/
    private static int minMatchTYpe = 1;      //最小匹配规则
    private static int maxMatchType = 2;      //最大匹配规则


    /**
     * 判断文字是否包含敏感字符
     * 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     */
    public static boolean isContaintSensitiveWord(String txt,int matchType){
        boolean flag = false;
        for(int i = 0 ; i < txt.length() ; i++){
            int matchFlag = CheckSensitiveWord(txt, i, matchType); //判断是否包含敏感字符
            if(matchFlag > 0){    //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取文字中的敏感词
     * 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     */
    public Set<String> getSensitiveWord(String txt , int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();

        for(int i = 0 ; i < txt.length() ; i++){
            int length = CheckSensitiveWord(txt, i, matchType);    //判断是否包含敏感字符
            if(length > 0){    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }

        return sensitiveWordList;
    }

    /**
     * 替换敏感字字符
     * 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     */
    public String replaceSensitiveWord(String txt,int matchType,String replaceChar){
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt, matchType);     //获取所有的敏感词
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 获取替换字符串
     * 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     */
    private String getReplaceChars(String replaceChar,int length){
        String resultReplace = replaceChar;
        for(int i = 1 ; i < length ; i++){
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     * 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     */
    public static int CheckSensitiveWord(String txt,int beginIndex,int matchType){
        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if(nowMap != null){     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if(minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            }
            else{     //不存在，直接返回
                break;
            }
        }
        if(matchFlag < 2 || !flag){        //长度必须大于等于1，为词
            matchFlag = 0;
        }
        return matchFlag;
    }

    public static Map initKeyWord() {
        try {
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：
     **/


    private static void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while (iterator.hasNext()) {
            key = iterator.next();    //关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if (wordMap != null) {        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else {     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String, String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合
     */
    private static Set<String> readSensitiveWordFile() throws Exception {
        Set<String> set = null;
        String webAppReourcePath = SensitiveWordsUtil.class.getResource("/").getPath();

        File file = new File(webAppReourcePath+"sensitivelexicon/lexicon.txt");    //读取文件
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
        try {
            if (file.isFile() && file.exists()) {      //文件流是否存在
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while ((txt = bufferedReader.readLine()) != null) {    //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
            } else {         //不存在抛出异常信息
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            read.close();     //关闭文件流
        }
        return set;
    }
}