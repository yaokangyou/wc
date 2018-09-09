package com.wc.command;

import java.io.File;
import java.io.IOException;

import com.wc.command.count;

public class foundFile {
	
	public static void foundFilePath(String path, String fileName) throws IOException {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {                    
                        foundFilePath(file2.getAbsolutePath(), fileName);
                    } else if (file2.getName().contains(fileName)) {
                    	System.out.println("文件:" + file2.getAbsolutePath());
                    	count.command(file2.getAbsolutePath(), "-all");
					}
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
}
