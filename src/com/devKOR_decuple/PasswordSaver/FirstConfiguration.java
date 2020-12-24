package com.devKOR_decuple.PasswordSaver;

import com.devKOR_decuple.PasswordSaver.Utilities.DeleteFile;
import com.devKOR_decuple.PasswordSaver.Utilities.Security.SHA256;
import com.devKOR_decuple.PasswordSaver.Utilities.WriteFile;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class FirstConfiguration {

    public FirstConfiguration() {
        File[] files = new File(PasswordSaverMain.MAIN_PATH + "/Database/").listFiles();

        if (files != null && files.length != 0) {
            for (File f : files) {
                new DeleteFile().deleteFile(f);
            }
        }

        System.out.println("====================================");
        System.out.println(" ## 패스워드 세이버 초기 설정 마법사 ##");
        System.out.println("====================================");

        System.out.println();

        System.out.println("[ ! ] 암호화를 위해서 패스워드를 입력해 주세요.");
        System.out.print("> ");

        Scanner sc = new Scanner(System.in);
        String password = sc.nextLine();

        if (password.length() > 16) {
            System.out.println("[ ! ] 패스워드가 너무 깁니다.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\n\n\n\n\n\n\n");
            new FirstConfiguration();
        }

        try {
            new WriteFile().writeString(PasswordSaverMain.MAIN_PATH + "/Configuration/Password.decuple", new SHA256().sha256(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("설정을 저장했습니다. 프로그램을 재시작해 주세요.");
    }

}
