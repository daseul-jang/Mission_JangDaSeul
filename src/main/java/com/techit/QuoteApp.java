package com.techit;

import com.techit.controller.QuoteController;
import com.techit.dto.RequestDto;

import java.util.Scanner;

public class QuoteApp {
    private final Scanner sc;

    public QuoteApp() {
        sc = new Scanner(System.in);
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        QuoteController quoteController = new QuoteController(sc);

        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine();

            RequestDto requestDto = new RequestDto(cmd);

            switch (requestDto.getAction()) {
                case "종료":
                    return;
                case "등록":
                    quoteController.registerQuote();
                    break;
                case "목록":
                    quoteController.listOfQuote();
                    break;
                case "삭제":
                    quoteController.deleteQuote(requestDto.getQuoteNo());
                    break;
                case "수정":
                    quoteController.updateQuote(requestDto.getQuoteNo());
                    break;
            }
        }
    }
}
