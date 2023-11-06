package com.techit;

import java.util.Scanner;

public class QuoteApp {
    Scanner sc = new Scanner(System.in);
    public void run() {
        System.out.println("== 명언 앱 ==");

        int num = 1;

        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine();

            switch (cmd) {
                case "종료" :
                    return;
                case "등록" :
                    System.out.print("명언 : ");
                    String quoteTxt = sc.nextLine();

                    System.out.print("작가 : ");
                    String author = sc.nextLine();

                    System.out.printf("%d 번 명언이 등록되었습니다.\n", num);
                    num++;

                    break;
            }
        }
    }
}
