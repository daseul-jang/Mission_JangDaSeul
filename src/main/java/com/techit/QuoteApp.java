package com.techit;

import com.techit.dto.QuoteDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class QuoteApp {
    Scanner sc = new Scanner(System.in);

    public void run() {
        System.out.println("== 명언 앱 ==");

        List<QuoteDto> quoteList = new ArrayList<>();
        int num = 1;

        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine();
            String paramName = null;
            String paramValue = null;

            if (cmd.contains("?")) {
                String[] cmdBits = cmd.split("\\?", 2);
                cmd = cmdBits[0].trim();
                String queryStr = cmdBits[1].trim();

                String[] paramBits = queryStr.split("=", 2);
                paramName = paramBits[0];
                paramValue = paramBits[1];
            }

            switch (cmd) {
                case "종료":
                    return;
                case "등록":
                    System.out.print("명언 : ");
                    String quoteTxt = sc.nextLine();

                    System.out.print("작가 : ");
                    String quoteAuthor = sc.nextLine();

                    QuoteDto quoteDto = new QuoteDto(num, quoteTxt, quoteAuthor);
                    quoteList.add(quoteDto);
                    System.out.printf("%d번 명언이 등록되었습니다.\n", num);
                    num++;

                    break;
                case "목록":
                    System.out.println("  번호  |    작가    |          명언          ");
                    System.out.println("================================================");

                    for (int i = quoteList.size() - 1; i >= 0; i--) {
                        int no = quoteList.get(i).getQuoteNo();
                        String author = quoteList.get(i).getQuoteAuthor();
                        String text = quoteList.get(i).getQuoteTxt();

                        System.out.printf("   %d    |    %s    |  %s \n", no, author, text);
                    }

                    break;
                case "삭제":
                    assert paramValue != null;
                    int no = Integer.parseInt(paramValue);
                    if(quoteList.removeIf(quote -> quote.getQuoteNo() == no )) {
                        System.out.printf("%d번 명언이 삭제되었습니다.\n", no);
                    }
                    break;
            }
        }
    }
}
