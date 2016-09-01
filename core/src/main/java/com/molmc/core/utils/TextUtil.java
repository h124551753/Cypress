package com.molmc.core.utils;

/**
 * author   YeMing(yeming_1001@163.com)
 * Date:    2015-01-26 23:03
 * version: V1.0
 * Description:  String 校验与处理
 */
public class TextUtil {

    /**
     * @param str
     * @return boolean
     * 校验邮箱格式
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    public static boolean isPhone(String str){
        boolean isPhone = true;
        String expr = "^[1-9]\\d*$";
        if(str.matches(expr)){
            isPhone = true;
        }
        return isPhone;
    }

    /**
     * 检测国内手机号码
     * @param str
     * @return
     */
    public static boolean isPhoneCN(String str){
        boolean isPhone = false;
        String expr = "^[1][1-9][0-9]{9}$";
        if(str.matches(expr)){
            isPhone = true;
        }
        return isPhone;
    }
    /**
     * @param target
     * @return 校验邮箱格式
     */
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    /**
     * 检查密码格式
     * @param pwd
     * @return
     */
    public static boolean checkPassword(String pwd){
        String regex = "((^(?![0-9]+$)(?![a-zA-Z_]+$))[0-9A-Za-z_]{6,20}$)|(|(^(?![0-9_]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,20}$))";
        return pwd.matches(regex);
    }

    /**
     * 检测用户名 只含有4-20位 汉字、数字、字母、下划线，下划线位置不限：
     * @param name
     * @return
     */
    public static boolean checkUserName(String name){
        String regex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]{4,20}$";
        return name.matches(regex);
    }

    public static boolean checkNumber(String num){
        String regex = "^[-+]?\\d+(\\.\\d+)?$";
        return num.matches(regex);
    }

}
