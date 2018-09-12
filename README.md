## 项目要求 ##
wc.exe 是一个常见的工具，它能统计文本文件的字符数、单词数和行数。这个项目要求写一个命令行程序，模仿已有wc.exe 的功能，并加以扩充，给出某程序设计语言源文件的字符数、单词数和行数。

实现一个统计程序，它能正确统计程序文件中的字符数、单词数、行数，以及还具备其他扩展功能，并能够快速地处理多个文件。

**基本功能列表**

  -c    [文件名]  返回文件的字符数  

  -w   [文件名]  返回文件词的数目 
 
  -l     [文件名]  返回文件的行数  

**扩展功能列表**

  -s   递归处理目录下符合条件的文件。  

  -a   返回更复杂的数据（代码行 / 空行 / 注释行）。
 
## PSP开发耗时 ##
| PSP2.1| Personal Software Process Stages| 预估耗时（分钟） | 实际耗时（分钟） |
|-----------------|----------------------|-----------------|----------------|
| Planning        | 计划                     |30              |50            |
| · Estimate        | · 估计这个任务需要多少时间    |· 30        |· 50          |
| Development     | 开发                      |690             |740          |
| · Analysis        | · 需求分析 (包括学习新技术)   |· 100       |· 180        |
| · Design Spec     | · 生成设计文档               |· 60        |· 100         |
| · Design Review   | · 设计复审 (和同事审核设计文档)|· 30        |· 30         |
| · Coding Standard | · 代码规范 (为目前的开发制定合适的规范)|· 30 |· 30         |
| · Design          | · 具体设计              |· 120            |· 60         |
| · Coding          | · 具体编码              |· 210            |· 250        |
| · Code Review     | · 代码复审              |· 40             |· 30         |
| · Test            | · 测试（自我测试，修改代码，提交修改）|· 100  |· 60         |
| Reporting         | 报告                   |80               |80          |
| · Test Report     | · 测试报告              |· 30             |· 40         |
| · Size Measurement| · 计算工作量            |· 20              |· 10        |
| · Postmortem & Process Improvement Plan|· 事后总结, 并提出过程改进计划|· 30|· 30|
| 合计              |                        |800               |870         |

 
## 设计 ##

主函数思路：通过输入的命令来判断执行各个功能函数，将输入的命令分割为两部分，第一部分是指令，第二部分是文件路径，把两者同时传到count文件，用指令来判断需要执行的方法，文件路径作为参数。

词数统计函数：把readLine读取的数据记录下来，把多行拼接为一行，根据空格切割，用正则过滤一些特殊词；

字符统计函数：把切割的词的长度统计；

行数统计函数：根据JAVA的readLine功能记录；

空行 / 注释行 / 代码行：根据正则判断记录；



 

## 用户使用说明 ##

**需求举例：** 
 
-all [文件名]    返回该文件的代码行数、字符数、词数、代码数、空行数、注释行数。

-c   [文件名]    返回该文件的字符数。

-w  [文件名]    返回该文件的代码词数。                                                                        

-l   [文件名]    返回该文件的代码行数。

-s  [文件夹]    返回 "请输入查询的文件名(可模糊查询):"，再输入文件名，返回目录下符合条件的全部文件的 -all。

-a  [文件名]    返回文件的代码行 / 空行 / 注释行。

##  代码 ##


**主函数：（启动程序）start.java**

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
 

**逻辑函数代码：（基本功能整合）**

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
 
**(拓展功能)**

**-s  递归处理目录下符合条件的文件**

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
 

**-a  返回更复杂的数据（代码行 / 空行 / 注释行）**


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
 

 

 
## 总结 ##

> 
> 先是从软件工程的课堂上了解到工程设计的流程和要点。因为自己的学习方向是前端和ui，本身对JAVA的了解也不是很多，原本想用JavaScript语言完成这次作业的，但经过研究发现难度太高了，就决定还是采用JAVA完成。通过这次作业，我重新回顾了JAVA的知识，加深了对正则表达式的认知，同时也学会了代码覆盖率这个新知识点，也学会了一些java编译器插件的安装等等，同时，通过这次作业，自己主要是学会怎么从零开始一个程序的设计，对时间的安排，对功能的评估等等工程知识，我相信这次的项目作业对我以后的项目开发会有蛮大的帮助的。
