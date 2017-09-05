package com.ywd.tool.idCard;

/**
 * Created by admin on 2017/9/5.
 */
public class ID15to18 {

    public static void main(String[] args) {
        System.out.println(transIDCard15to18("370986890623212"));
        System.out.println(transIDCard15to18("370725881105149"));
    }

    public static String transIDCard15to18(String IdCardNO) {
        String cardNo = null;
        if (null != IdCardNO && IdCardNO.trim().length() == 15) {
            IdCardNO = IdCardNO.trim();
            StringBuffer sb = new StringBuffer(IdCardNO);
            sb.insert(6, "19");
            sb.append(transCardLastNo(sb.toString()));
            cardNo = sb.toString();
        }
        return cardNo;
    }

    private static String transCardLastNo(String newCardId) {
        char[] ch = newCardId.toCharArray();
        int m = 0;
        int[] co = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] verCode = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        for (int i = 0; i < newCardId.length(); i++) {
            m += (ch[i] - '0') * co[i];
        }
        int residue = m % 11;
        return String.valueOf(verCode[residue]);

    }
}
