package com.wc.command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class count{
	static int cntCode = 0, cntNode = 0, cntSpace = 0;
    static boolean flagNode = false;

    public static void command(String path, String type) throws IOException {
    	BufferedReader br = null;
        int countWord = 0;
        int countChar = 0;
        int countLine = 0;
        String str = "";
        String strCount = "";
        br = new BufferedReader(new FileReader(path));
        while((str = br.readLine()) != null ){
        	// 计算行数
        	countLine++;
        	// 计算特殊行的数目
        	if (type.equals("-a")||type.equals("-all")) {
        		pattern(str);
        	}
        	
        	str = str + " ";
            strCount += str;
        }
        // 根据空格切割单词数        
        for(int i=0;i<strCount.split(" ").length;i++){
        	if (type.equals("-c")||type.equals("-all")) {
        		// 根据切割完的词的长度计算字符数
            	countChar += strCount.split(" ")[i].length();
			}
        	if (type.equals("-w")||type.equals("-all")) { 
        		// 根据切割的词的数量计算词的数量，排除多余的空格误被计算的情况
        		// 把中英文标点符号都替换为“”
        		String s = strCount.split(" ")[i].replaceAll("[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , "");
        		boolean word = !s.equals("");
                if(word) {
                	countWord++;
    			}
            }
        }
        
        if (type.equals("-w")||type.equals("-all")) {
        	System.out.println("单词数：" + countWord);
		}
        if (type.equals("-c")||type.equals("-all")) {
        	System.out.println("字符数：" + countChar);
		}
        if (type.equals("-l")||type.equals("-all")) {
        	System.out.println("行数：" + countLine);
		}
        if (type.equals("-a")||type.equals("-all")) {
	        System.out.println("注释行： " + cntNode);
	        System.out.println("空行： " + cntSpace);
	        System.out.println("代码行： " + cntCode);
        }
        System.out.println();
        br.close();
        cntNode = 0;
        cntSpace = 0;
        cntCode = 0;
        flagNode = false;
    }
    
    // 计算特殊行的数量
    public static void pattern(String line) {
    	String regxNodeBegin = "\\s*/\\*.*";
        String regxNodeEnd = ".*\\*/\\s*";
        String regx = "//.*";
        String regxSpace = "\\s*";
        if(line.matches(regxNodeBegin) && line.matches(regxNodeEnd)){
            ++cntNode;
            return ;
        }
        if(line.matches(regxNodeBegin)){
            ++cntNode;
            flagNode = true;
        } else if(line.matches(regxNodeEnd)){
            ++cntNode;
            flagNode = false;
        } else if(line.matches(regxSpace))
            ++cntSpace;
        else if(line.matches(regx))
            ++cntNode;
        else if(flagNode)
            ++cntNode;
        else ++cntCode;
    }
}
