package com.asarao.io;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
 * @ClassName: IODemo
 * @Description: TODO
 * @Author: Asarao
 * @Date: 2020/5/14 13:38
 * @Version: 1.0
 **/
public class IODemo {

    /**
     * buffer 实际就是一个容器 一个连续的数组
     * 往buffer中写数据：
     *      从Channel写到Buffer fileChannel.read(buffer);
     *      通过Buffer的put()方法 buffer.put();
     * 从buffer中读数据:
     *      从buffer读入到Channel fileChannel.write(buffer);
     *      使用get()方法从Buffer中读取数据 buf.get()
     */
    public static void nio(){
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("C:\\projects\\spring_security\\pom.xml", "rw");
            FileChannel fileChannel = aFile.getChannel();
            // 分配空间
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 写入数据到buffer中
            int bytesRead = fileChannel.read(buffer);
            System.out.println(bytesRead);
            while (bytesRead != -1){
                // 反转
                buffer.flip();
                /**
                 * Tells whether there are any elements between the current position and the limit.
                 */
                while(buffer.hasRemaining())
                {
                    // 从buffer中获取数据
                    System.out.print((char)buffer.get());
                }
                buffer.compact();
                bytesRead = fileChannel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public static void bio(){
        InputStream in = null;
        try{
            in = new BufferedInputStream(new FileInputStream("C:\\projects\\spring_security\\pom.xml"));
            byte [] buf = new byte[1024];
            int bytesRead = in.read(buf);
            while(bytesRead != -1)
            {
                for(int i=0;i<bytesRead;i++)
                    System.out.print((char)buf[i]);
                bytesRead = in.read(buf);
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally{
            try{
                if(in != null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        bio();
        nio();
    }
}
