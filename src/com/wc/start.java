package com.wc;

import java.io.IOException;
import java.util.Scanner;

import com.wc.command.count;
import com.wc.command.foundFile;

public class start {
	public static void main(String[] args) throws IOException {
		while (true) {
			System.out.println("**** -all [文件名]  返回文件的字符数 / 词数 / 行数");
	        System.out.println("**** -c   [文件名]  返回文件的字符数");
	        System.out.println("**** -w   [文件名]  返回文件词的数目");
	        System.out.println("**** -l   [文件名]  返回文件的行数");
	        System.out.println("**** -s   [文件夹名] 返回和处理符合条件的文件");
	        System.out.println("**** -a   [文件名]  返回文件的代码行 / 空行 / 注释行");
	        System.out.print("请输入命令：");
			Scanner s = new Scanner(System.in);
	        String m =s.nextLine();
	        String arr[]=m.split("\\s");
	        if (arr[0].equals("-s")) {
	        	System.out.print("请输入查询的文件名(可模糊查询)：");
	            Scanner name = new Scanner(System.in);
	            String fileName = name.nextLine();
	        	foundFile.foundFilePath(arr[1], fileName);
			}else {
				count.command(arr[1], arr[0]);	
			}
		}
	}
}
