package com.devKOR_decuple.PasswordSaver;

import com.devKOR_decuple.PasswordSaver.Utilities.ReadFile;
import com.devKOR_decuple.PasswordSaver.Utilities.Security.SHA256;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Scanner;

public class PasswordSaverMain {

    public static String MAIN_PATH = System.getProperty("user.dir");
    public static String INPUTTED_PASSWORD;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        if (Objects.requireNonNull(new File(MAIN_PATH + "/Configuration/").listFiles()).length == 0) {
            new FirstConfiguration();
        } else {
            System.out.println("┌ ====================================== ┐");
            System.out.println("          #$# Password Saver #$#          ");
            System.out.println("└ ====================================== ┘");

            System.out.println();

            System.out.println("[ ! ] 복호화를 위해서 패스워드를 입력해 주세요.");
            System.out.print("[ @ ] Input password : ");

            Scanner sc = new Scanner(System.in);
            String inputtedPassword = sc.nextLine();
            String password = new SHA256().sha256(inputtedPassword);

            System.out.println();

            String hashedPassword = new ReadFile().readString(MAIN_PATH + "/Configuration/Password.decuple");

            if (!password.equals(hashedPassword)) {
                System.out.println();
                System.out.println("[ ! ] 비밀번호가 올바르지 않습니다.");
                return;
            } else {
                System.out.println();
                System.out.println("[ ! ] 비밀번호가 올바릅니다.");
            }

            if (inputtedPassword.length() < 16) {
                StringBuilder sb = new StringBuilder();
                char[] inputtedPasswordChars = inputtedPassword.toCharArray();

                for (int i = 0; i < 16; i++) {
                    if (i >= inputtedPasswordChars.length) {
                        sb.append("0");
                    } else {
                        sb.append(inputtedPasswordChars[i]);
                    }
                }

                inputtedPassword = sb.toString();
            }

            INPUTTED_PASSWORD = inputtedPassword;

            if (Objects.requireNonNull(new File(MAIN_PATH + "/Database/").listFiles()).length == 0) {
                System.out.println("\n\n\n\n\n\n\n");
                System.out.println("[ ! ] 저장된 이메일, 비밀번호가 없습니다. 새로 만들어 봅시다.");
                SavePassword.editSiteDatabase();
            } else {
                loadPasswords();
            }

        }
    }

    private static void loadPasswords() {
        System.out.println("\n\n\n\n\n\n\n=======================");
        System.out.println("[ ! ] 현재 저장된 비밀번호 목록들입니다.\n\n");
        File[] databaseFiles = new File(MAIN_PATH + "/Database/").listFiles();

        int index = 1;
        System.out.println("[ # ] 0. 새로 생성하기");
        for (File databaseFile : Objects.requireNonNull(databaseFiles)) {
            System.out.println("[ # ] " + index + ". " + databaseFile.getName());
            index++;
        }

        System.out.println("\n\n[ ! ] 알맞은 번호를 입력하고 [Enter]를 눌러 주세요.");
        System.out.print("[ @ ] Input database number : ");

        Scanner sc = new Scanner(System.in);
        int selectedIndex = Integer.parseInt(sc.nextLine()) - 1;

        if (selectedIndex == -1) {
            SavePassword.editSiteDatabase();
        } else {

            if (selectedIndex > databaseFiles.length) {
                loadPasswords();
                return;
            }

            System.out.println("\n\n\n\n\n\n\n");
            String selectedDB = databaseFiles[selectedIndex].getName();

            if (!new File(MAIN_PATH + "/Database/" + selectedDB).exists()) {
                System.out.println("[ ! ] 해당 데이터베이스가 없습니다. 다시 시도해 주세요.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                loadPasswords();
                return;
            }

            String email = SavePassword.getEmail(selectedDB);
            String password = SavePassword.getPassword(selectedDB);

            System.out.println("==================================== ");

            System.out.println("[ # ] 사이트 이름 : " + selectedDB);
            System.out.println("[ # ] 사이트 ID : " + email);
            System.out.println("[ # ] 사이트 PW : " + password);
            System.out.println("\n\n[ ! ] 메인으로 돌아가려면 1, 정보를 수정하려면 2를 눌러 주세요.");

            System.out.print("[ @ ] Input 1 or 2 : ");

            sc = new Scanner(System.in);
            int chosen = Integer.parseInt(sc.nextLine());

            if (chosen == 1) loadPasswords();
            if (chosen == 2) SavePassword.setSiteEmail(selectedDB);
        }
    }

}
