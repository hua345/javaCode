package com.github.chenjianhua.springbootexcel.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @author chenjianhua
 * @date 2020/12/31
 */
@Slf4j
public class TestFileUtil {

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static String getPath() {
        return TestFileUtil.class.getResource("/").getPath();
    }

    public static void testUploadFile(File tempFile) {
        log.info("tempFile Path:{}", tempFile.getAbsolutePath());
        String fileName = TestFileUtil.getPath() + tempFile.getName();
        File outFile = new File(fileName);
        try {
            TestFileUtil.copyFileFileChannel(tempFile, outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("upload File Path:{}", outFile.getAbsolutePath());
    }

    public static void copyFileFileChannel(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    public static File createNewFile(String pathName) {
        File file = new File(getPath() + pathName);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    public static File readFile(String pathName) {
        return new File(getPath() + pathName);
    }

    public static File readUserHomeFile(String pathName) {
        return new File(System.getProperty("user.home") + File.separator + pathName);
    }
}
