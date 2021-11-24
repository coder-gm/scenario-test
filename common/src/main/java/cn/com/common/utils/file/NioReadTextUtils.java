package cn.com.common.utils.file;

import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 读取text文件内容
 *
 * @author zgming
 */
@Slf4j
public class NioReadTextUtils {

    public static void main(String args[]) throws Exception {
        //读取指定文件夹下的所有文件
        List<String> readFileList = ReadFilePathUtils.readFile("E:\\test");

        if (readFileList.size() > 0) {
            for (String strPath : readFileList) {
                //一次读取的字节长度
                int bufSize = 1000000;
                //读取的文件
                File fin = new File("E:\\test\\" + strPath);
                //写出的文件
                File fout = new File("E:\\test_new\\" + strPath);
                //开始时间
                Date startDate = new Date();
                FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
                ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);
                FileChannel fcout = new RandomAccessFile(fout, "rws").getChannel();
                ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize);
                readFileByLine(bufSize, fcin, rBuffer, fcout, wBuffer);
                Date endDate = new Date();
                //测试执行时间
                System.out.print("开始时间:" + startDate + "|" + "结束时间" + endDate);
                if (fcin.isOpen()) {
                    fcin.close();
                }
                if (fcout.isOpen()) {
                    fcout.close();
                }
            }
        }
    }

    /**
     * @param bufSize
     * @param fcin
     * @param rBuffer
     * @param fcout
     * @param wBuffer
     */
    public static void readFileByLine(int bufSize, FileChannel fcin, ByteBuffer rBuffer, FileChannel fcout, ByteBuffer wBuffer) {
        String enter = "\n";
        //存储读取的每行数据
        List<String> dataList = new ArrayList<String>();
        byte[] lineByte = new byte[0];

        String encode = "GBK";
//		String encode = "UTF-8";
        try {
            //temp：由于是按固定字节读取，在一次读取中，第一行和最后一行经常是不完整的行，因此定义此变量来存储上次的最后一行和这次的第一行的内容，
            //并将之连接成完成的一行，否则会出现汉字被拆分成2个字节，并被提前转换成字符串而乱码的问题
            byte[] temp = new byte[0];
            //fcin.read(rBuffer)：从文件管道读取内容到缓冲区(rBuffer)
            while (fcin.read(rBuffer) != -1) {
                //读取结束后的位置，相当于读取的长度
                int rSize = rBuffer.position();
                //用来存放读取的内容的数组
                byte[] bs = new byte[rSize];
                //将position设回0,所以你可以重读Buffer中的所有数据,此处如果不设置,无法使用下面的get方法
                rBuffer.rewind();
                //相当于rBuffer.get(bs,0,bs.length())：从position初始位置开始相对读,读bs.length个byte,并写入bs[0]到bs[bs.length-1]的区域
                rBuffer.get(bs);
                rBuffer.clear();

                int startNum = 0;
                //换行符
                int LF = 10;
                //回车符
                int CR = 13;
                //是否有换行符
                boolean hasLF = false;
                for (int i = 0; i < rSize; i++) {
                    if (bs[i] == LF) {
                        hasLF = true;
                        int tempNum = temp.length;
                        int lineNum = i - startNum;
                        //数组大小已经去掉换行符
                        lineByte = new byte[tempNum + lineNum];
                        //填充了lineByte[0]~lineByte[tempNum-1]
                        System.arraycopy(temp, 0, lineByte, 0, tempNum);
                        temp = new byte[0];
                        //填充lineByte[tempNum]~lineByte[tempNum+lineNum-1]
                        System.arraycopy(bs, startNum, lineByte, tempNum, lineNum);
                        //一行完整的字符串(过滤了换行和回车)
                        String line = new String(lineByte, 0, lineByte.length, encode);

                        log.info("line=====1 >" + line);
                        //关键字提取
                        List<String> keywordList = HanLP.extractKeyword(line, 100);
                        //List<String> phraseList = HanLP.extractPhrase(line, 100);//提取短词
                        //List<String> sentenceList = HanLP.extractSummary(line, 5);//自动摘要
                        log.info("keywordList=======>" + keywordList);

                        dataList.add(line);
                        writeFileByLine(fcout, wBuffer, line + enter);

                        //过滤回车符和换行符
                        if (i + 1 < rSize && bs[i + 1] == CR) {
                            startNum = i + 2;
                        } else {
                            startNum = i + 1;
                        }

                    }
                }
                if (hasLF) {
                    temp = new byte[bs.length - startNum];
                    System.arraycopy(bs, startNum, temp, 0, temp.length);
                } else {//兼容单次读取的内容不足一行的情况
                    byte[] toTemp = new byte[temp.length + bs.length];
                    System.arraycopy(temp, 0, toTemp, 0, temp.length);
                    System.arraycopy(bs, 0, toTemp, temp.length, bs.length);
                    temp = toTemp;
                }
            }
            //兼容文件最后一行没有换行的情况
            if (temp != null && temp.length > 0) {
                String line = new String(temp, 0, temp.length, encode);
                //关键字提取
                List<String> keywordList = HanLP.extractKeyword(line, 100);
                //List<String> phraseList = HanLP.extractPhrase(line, 100);//提取短词
                //List<String> sentenceList = HanLP.extractSummary(line, 5);//自动摘要
                log.info("keywordList2=======>" + keywordList);

                for (String str : keywordList) {
                    if (line.indexOf(str) != -1) {
                        line = line.replaceAll(str, "<<我是被替换的>>");
                        // line = line.replace(',',':');//将字符串,替换成":"
                    }
                }

                log.info("line=====2 >" + line);
                dataList.add(line);
                writeFileByLine(fcout, wBuffer, line + enter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写到文件上
     *
     * @param fcout
     * @param wBuffer
     * @param line
     */
    @SuppressWarnings("static-access")
    public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer, String line) {
        try {
            fcout.write(ByteBuffer.wrap(line.getBytes("UTF-8")), fcout.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}