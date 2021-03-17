package com.hezk.reg;

import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegTest {
    @Test
    public void test1() {
        Pattern pattern = Pattern.compile("ab{2,5}c");
        String str = "abc abbc abbbc abbbbc abbbbbc abbbbbbc";
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    @Test
    public void test2() {
        Pattern pattern = Pattern.compile("a[123]b");
//        Pattern pattern = Pattern.compile("a[^123]b");
        String str = "a0b a1b a2b a3b a4b";
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    @Test
    public void test3() {
//        Pattern pattern = Pattern.compile("\\d{2,5}"); //贪婪模式
        Pattern pattern = Pattern.compile("\\d{2,5}?");  //惰性模式
        String str = "123 1234 12345 123456";
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    /**
     * 分支结构是惰性的
     * 用good|goodbye去匹配字符串goodbye时，匹配的是good
     * 可以调整成goodbye|good
     */
    @Test
    public void test4() {
        Pattern pattern = Pattern.compile("good|idea");
        String str = "good idea, nice try.";
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    /**
     * 匹配文件夹
     */
    @Test
    public void test5() {
        String reg = "^[a-zA-Z]\\:([^\\:*<>|\"<>\n\r/]+)*([^\\:*<>|\"<>\n\r/]+)?$";
        String str = "F:\\study\\javascript\\regex\\regular expression.pdf";
        System.out.println(str.matches(reg));
        str = "F:\\study\\javascript\\regex\\";
        System.out.println(str.matches(reg));
        str = "F:\\study\\javascript";
        System.out.println(str.matches(reg));
        str = "F:\\";
        System.out.println(str.matches(reg));
    }

    @Test
    public void test6() {
//        String reg = "id=\".*\"";
//        String reg = "id=\".*?\"";
        String reg = "id=\"[^\"]*\"";
        String html = "<div id=\"container\" class=\"main\"></div>";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    @Test
    public void test7() {
        String reg = "^|$";
        System.out.println("hello".replaceAll(reg, "#"));
    }


    @Test
    public void test8() {
        String reg = "^|$";
        System.out.println("hello\njava\njavascript".replaceAll(reg, "#"));
    }

    @Test
    public void test9() {
        String reg = "\\b";
        System.out.println("[JS] Lesson_01.mp4".replaceAll(reg, "#"));
        //[#JS#] #Lesson_01#.#mp4#
    }

    @Test
    public void test10() {
        String reg = "\\B";
        System.out.println("[JS] Lesson_01.mp4".replaceAll(reg, "#"));
        //#[J#S]# L#e#s#s#o#n#_#0#1.m#p#4
    }

    @Test
    public void test11() {
        String reg = "(?=l)";
        String str = "hello";
        System.out.println(str.replaceAll(reg, "#"));
    }

    @Test
    public void test12() {
        String reg = "(?!l)";
        String str = "hello";
        System.out.println(str.replaceAll(reg, "#"));
    }

    @Test
    public void test13() {
        String str = "12345679";
        String reg = "(?=\\d{3}$)";
        System.out.println(str.replaceAll(reg, ","));
    }

    @Test
    public void test14() {
        String str = "123456789";
        String reg = "(?=(\\d{3})+$)";
        System.out.println(str.replaceAll(reg, ","));
    }

    @Test
    public void test15() {
        String str = "123456789";
        String reg = "(?!^)(?=(\\d{3})+$)";
        System.out.println(str.replaceAll(reg, ","));
    }

    @Test
    public void test16() {
        String str = "123456789 123456789";
//        String reg = "(?!\\b)(?=(\\d{3})+\\b)";
        String reg = "\\B(?=(\\d{3})+\\b)";
        System.out.println(str.replaceAll(reg, ","));
    }

    @Test
    public void test17() {
        String str = "1188.00";
        String reg = "\\B(?=\\d{3}+\\b)";
        System.out.println(str.replaceAll(reg, ","));
    }


    @Test
    public void test18() {
        String reg = "(\\d{4})-(\\d{2})-(\\d{2})";
        String str = "2020-02-03";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.groupCount());
        matcher.find();
        System.out.println(matcher.group(0));
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));
        System.out.println(matcher.group(3));
    }

    @Test
    public void test19() {
//        String reg = "(\\d{4})-(\\d{2})-(\\d{2})";
        String reg = "(\\d{4})\\|(\\d{2})\\|(\\d{2})";
//        String str = "2020-02-03";
        String str = "2020|02|03";
        System.out.println(str.replaceAll(reg, "$2/$3/$1"));
    }


    @Test
    public void test20() {
        String str1 = "2020-02-03";
        String str2 = "2020.02.03";
        String str3 = "2020/02/03";
        String str4 = "2020/02.03";
        String str5 = "2020|02|03";
        String reg = "\\d{4}(-|/|.)\\d{2}\\1\\d{2}";
        System.out.println(Pattern.compile(reg).matcher(str1).matches());
        System.out.println(Pattern.compile(reg).matcher(str2).matches());
        System.out.println(Pattern.compile(reg).matcher(str3).matches());
        System.out.println(Pattern.compile(reg).matcher(str4).matches());
        System.out.println(Pattern.compile(reg).matcher(str5).matches());
    }

    //trim
    @Test
    public void test21() {
        String str = "   hello world    ";
        System.out.println(str.replaceAll("^\\s+|\\s$", ""));
    }

    //trim
    @Test
    public void test22() {
        String str = "   hello world    ";
        System.out.println(str.replaceAll("^\\s*(.*?)\\s*$", "$1"));
    }

    //将每个单词首字母变成大写
    //TODO error
    @Test
    public void test23() {
        String str = "my name is hezk";
        System.out.println(str.toLowerCase().replaceAll("(?:^|\\s)(\\w)", " $1".toUpperCase()));
        Pattern pattern = Pattern.compile("(?:^|\\s)(\\w)");
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.find());
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }

    //驼峰
    //TODO error
    @Test
    public void test24() {
        String str = "_moz_transform";
        System.out.println(str.replaceAll("[-_]([a-z])", String.valueOf("$1").toUpperCase()));
    }

    //TODO error
    @Test
    public void test25() {
        String str = "MozTransform";
        System.out.println(str.replaceAll("([A-Z])", "-$1").replaceAll("([A-Z])", String.valueOf("$1").toLowerCase()));
    }

    @Test
    public void test26() {
        String str = "2020-10-10";
        str = "2020|10|10";
        Arrays.asList(str.split("\\|")).stream().forEach(System.out::println);
    }

    @Test
    public void test27() {
        String password = "Windows";
        String regex = "^(?=W)\\w+(?<=s)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        boolean isMatch = m.matches();
        System.out.println(isMatch);
        System.out.println();

        String password2 = "Windows";
        String regex2 = "^\\w{1}(?=i)\\w+$";
        Pattern p2 = Pattern.compile(regex2);
        Matcher m2 = p2.matcher(password2);
        boolean isMatch2 = m2.matches();
        System.out.println(isMatch2);

        String password3 = "Windows";
        String regex3 = "^(?!a)\\w+(?<!a)$";
        Pattern p3 = Pattern.compile(regex3);
        Matcher m3 = p3.matcher(password3);
        boolean isMatch3 = m3.matches();
        System.out.println(isMatch3);

        String password4 = "Windows";
        String regex4 = "^\\w{1}(?!a)\\w+$";
        Pattern p4 = Pattern.compile(regex4);
        Matcher m4 = p4.matcher(password4);
        boolean isMatch4 = m4.matches();
        System.out.println(isMatch4);
    }

    //https://blog.csdn.net/yh18668197127/article/details/85956811
}
