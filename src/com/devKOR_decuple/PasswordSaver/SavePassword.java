package com.devKOR_decuple.PasswordSaver;

import com.devKOR_decuple.PasswordSaver.Utilities.ReadFile;
import com.devKOR_decuple.PasswordSaver.Utilities.Security.AES128;
import com.devKOR_decuple.PasswordSaver.Utilities.WriteFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SavePassword {

    private static String siteName;
    private static String siteEmail;
    private static String sitePassword;

    public static void editSiteDatabase() {
        setSiteName();
    }

    private static void setSiteName() {
        System.out.println("[ ! ] 사이트의 이름을 입력해 주세요. (영어로)");
        System.out.print("[ @ ] Input site name : ");

        Scanner sc = new Scanner(System.in);
        siteName = sc.nextLine();

        if (!Pattern.matches("^[a-zA-Z0-9]*$", siteName)) {
            System.out.println("[ ! ] 영어나 숫자 이외의 다른 문자가 포함되어 있습니다.");
            System.out.println("\n\n\n\n\n\n\n");
            setSiteName();
        }

        System.out.println();

        setSiteEmail();
    }

    public static void setSiteEmail(String newSiteName) {
        siteName = newSiteName;

        System.out.println("[ ! ] 사이트에 회원가입할 때 입력한 ID를 입력해 주세요.");
        System.out.print("[ @ ] Input site ID : ");

        Scanner sc = new Scanner(System.in);
        siteEmail = sc.nextLine();

        System.out.println();

        setSitePassword();
    }

    private static void setSiteEmail() {
        System.out.println("[ ! ] 사이트에 회원가입할 때 입력한 ID를 입력해 주세요.");
        System.out.print("[ @ ] Input site ID : ");

        Scanner sc = new Scanner(System.in);
        siteEmail = sc.nextLine();

        System.out.println();

        setSitePassword();
    }

    private static void setSitePassword() {
        System.out.println("[ ! ] 사이트에 회원가입할 때 입력한 비밀번호를 입력해 주세요.");
        System.out.print("[ @ ] Input site PW : ");

        Scanner sc = new Scanner(System.in);
        sitePassword = sc.nextLine();

        System.out.println();
        check();
    }

    private static void check() {
        System.out.println("[ ! ] 정말 다음 정보가 맞습니까?");
        System.out.println("[ @ ] * 사이트 이름 : " + siteName);
        System.out.println("[ @ ] * 사이트 이메일(ID) : " + siteEmail);
        System.out.println("[ @ ] * 사이트 비밀번호(PW) : " + sitePassword);

        System.out.println("\n[ ! ] 이 정보가 맞다면 Y를, 아니면 N을 입력하십시오.");
        System.out.print("[ @ ] Input Y or N : ");

        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        if (input.equalsIgnoreCase("Y")) {
            savePassword(siteName, siteEmail, sitePassword);
            System.out.println("\n[ ! ] 사이트 정보를 성공적으로 저장했습니다.");
            return;
        } else if (input.equalsIgnoreCase("N")) {
            System.out.println("\n\n\n\n\n\n\n");
            setSiteName();
        } else {
            System.out.println("\n\n\n\n\n\n\n");
            check();
        }
    }

    public static void savePassword(String databaseName, String email, String password) {
        AES128 aes = new AES128(PasswordSaverMain.INPUTTED_PASSWORD);
        try {
            String encryptedEmail = aes.encrypt(email);
            String encryptedPassword = aes.encrypt(password);

            File databaseFile = new File(PasswordSaverMain.MAIN_PATH + "/Database/" + databaseName);
            if (!databaseFile.exists()) databaseFile.mkdir();

            new WriteFile().writeString(databaseFile.getPath() + "/ID.decuple", encryptedEmail);
            new WriteFile().writeString(databaseFile.getPath() + "/PW.decuple", encryptedPassword);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    public static String getPassword(String databaseName) {
        File databaseFile = new File(PasswordSaverMain.MAIN_PATH + "/Database/" + databaseName);

        String encryptedPassword = new ReadFile().readString(databaseFile + "/PW.decuple");

        AES128 aes = new AES128(PasswordSaverMain.INPUTTED_PASSWORD);

        String decryptedPassword = "";

        try {
           decryptedPassword = aes.decrypt(encryptedPassword);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return decryptedPassword;
    }

    public static String getEmail(String databaseName) {
        File databaseFile = new File(PasswordSaverMain.MAIN_PATH + "/Database/" + databaseName);

        String encryptedEmail = new ReadFile().readString(databaseFile + "/ID.decuple");

        AES128 aes = new AES128(PasswordSaverMain.INPUTTED_PASSWORD);

        String decryptedEmail = "";

        try {
            decryptedEmail = aes.decrypt(encryptedEmail);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        return decryptedEmail;
    }

}
