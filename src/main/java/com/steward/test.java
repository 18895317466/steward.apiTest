package com.steward;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class test {
		public static void main(String[] args) {
			
		}

public void method1() {
	FileWriter fw = null;
	try {
		//如果文件存在，则追加内容；如果文件不存在，则创建文件
		File f=new File("E:/tools/book.txt");
		fw = new FileWriter(f, true);
		} 
	catch (IOException e) {
		e.printStackTrace();
	}
	PrintWriter pw = new PrintWriter(fw);
	pw.println("追加内容");
	pw.println("(124.11,56.11)");
	pw.flush();
	try {
		fw.flush();
		pw.close();
		fw.close();
	} catch (IOException e) {
		e.printStackTrace();
	
	}
	
	}
}