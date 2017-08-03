package com.enliple.crawler.common.util;

import java.io.*;

/**
 * Created by mkhwang on 2017-06-28.
 */
public class StringUtil {
    /**
     * Read all string.
     *
     * @param rd the rd
     * @return the string
     * @throws IOException the io exception
     */
    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String readAll(InputStreamReader inputStreamReader) throws IOException {
        char[] buffer = new char[1024];
        StringBuilder stringBuilder = new StringBuilder();
        int checkSum;
        while ((checkSum = inputStreamReader.read(buffer)) != -1){
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    public static String readAll(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        int checkSum;
        while((checkSum = inputStream.read(buffer)) != -1){
            result.write(buffer,0,checkSum);
        }
        return result.toString("UTF-8");
    }
}
