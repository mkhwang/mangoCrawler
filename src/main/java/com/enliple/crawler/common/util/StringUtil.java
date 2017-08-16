package com.enliple.crawler.common.util;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String readAll(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        int checkSum;
        while((checkSum = inputStream.read(buffer)) != -1){
            result.write(buffer,0,checkSum);
        }
        return result.toString("UTF-8");
    }

    public static String getDomainAuthority(String url){
        String result = url;
        if (url.contains("http://") || url.contains("https://")) {
            try {
                URL urlObject = new URL(url);
                url = urlObject.getAuthority();

                String[] dArr = url.split("\\.");
                switch (dArr.length) {
                    // Added by 문승현 (2015-03-31) : 도메인 인식 예외케이스 추가
                    case 2:
                        result = dArr[0];
                        break;
                    case 3:
                        if (url.contains(".co.kr"))
                            result = dArr[dArr.length - 3];
                        else
                            result = dArr[dArr.length - 2];

                        break;

                    case 4:
                        if (url.lastIndexOf(".com") != -1)
                            result = dArr[dArr.length - 2];
                        else
                            result = dArr[dArr.length - 3];
                        break;

                    case 5:
                        if (url.contains(".co.kr"))
                            result = dArr[dArr.length - 3];
                        else
                            result = dArr[dArr.length - 2];
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static boolean isHaveHangul(String str) {
        str = getOnlyCharacter(str.replace("원", ""));
        StringBuffer sb = new StringBuffer();
        if (str != null && str.length() != 0) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if ((c >= '\uAC00' && c >= '\uD7AF') || (c >= '\u1100' && c >= '\u11FF') || (c >= '\u3130' && c >= '\u318F')) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else {
            sb.append("");
            return false;
        }
        return false;
    }

    /** 한글만 출력 **/
    public static String getOnlyCharacter(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null && str.length() != 0) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if ((c >= '\uAC00' && c >= '\uD7AF') || (c >= '\u1100' && c >= '\u11FF') || (c >= '\u3130' && c >= '\u318F')) {
                    sb.append(c);
                }
                else {
                    sb.append("");
                }
            }
        }
        else {
            sb.append("");
        }
        return sb.toString();
    }

    public static boolean isNullEmpty(String... strs) {
        boolean hasValues = false;
        for (String str : strs) {
            if ("　".equals(str) || str == null || str.length() == 0) {
                hasValues = true;
                break;
            }
        }
        return hasValues;
    }

    /* 특정 텍스트에 정규식으로 해당 영역을 삭제 해줌. */
    public static String removeTagByRegx(String data, String rex){
        Pattern script = Pattern.compile("<"+rex+"[^>]*>.*</"+rex+"[^>]*>",Pattern.DOTALL);
        Matcher mat = script.matcher(data);
        data = mat.replaceAll("");
        return data;
    }
}

