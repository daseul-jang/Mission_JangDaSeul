package com.techit.service;

import com.techit.dto.QuoteDto;

import java.util.List;
import java.util.Scanner;

public class QuoteService {

    public void validateList(List<QuoteDto> quotes) {
        if (quotes.isEmpty()) {
            throw new RuntimeException("등록된 명언이 없습니다.");
        }
    }

    public QuoteDto quoteInsert(Scanner sc, List<QuoteDto> quotes) {
        int quoteNo = 1;

        if (!quotes.isEmpty()) {
            quoteNo = quotes.getLast().getQuoteNo() + 1;
        }

        System.out.print("명언 : ");
        String quoteTxt = sc.nextLine();

        System.out.print("작가 : ");
        String quoteAuthor = sc.nextLine();

        if (quoteTxt.isBlank() || quoteAuthor.isBlank()) {
            throw new RuntimeException("명언과 작가는 비워둘 수 없습니다.");
        }

        return new QuoteDto(quoteNo, quoteTxt, quoteAuthor);
    }

    public void quoteList(List<QuoteDto> quotes) {
        System.out.println("  번호  |    작가    |          명언          ");
        System.out.println("================================================");

        validateList(quotes);

        for (int i = quotes.size() - 1; i >= 0; i--) {
            int insertNo = quotes.get(i).getQuoteNo();
            String author = quotes.get(i).getQuoteAuthor();
            String text = quotes.get(i).getQuoteTxt();

            System.out.printf("   %d    |    %s    |  %s \n", insertNo, author, text);
        }
    }

    public void quoteRemove(List<QuoteDto> quotes, int quoteNo) {
        validateList(quotes);

        if (quotes.removeIf(quote -> quote.getQuoteNo() == quoteNo)) {
            System.out.printf("%d번 명언이 삭제되었습니다.\n", quoteNo);
        } else {
            throw new RuntimeException(quoteNo + "번 명언은 존재하지 않습니다.");
        }
    }

    public void quoteUpdate(Scanner sc, List<QuoteDto> quotes, int quoteNo) {
        validateList(quotes);

        for (QuoteDto quote : quotes) {
            if (quote.getQuoteNo() == quoteNo) {
                System.out.printf("명언(기존) : %s\n", quote.getQuoteTxt());
                System.out.print("명언 : ");
                String editQuote = sc.nextLine();

                System.out.printf("작가(기존) : %s\n", quote.getQuoteAuthor());
                System.out.print("작가 : ");
                String editAuthor = sc.nextLine();

                boolean isEditQuote = editQuote == null || editQuote.isBlank();
                boolean isEditAuthor = editAuthor == null || editAuthor.isBlank();

                if (isEditQuote && isEditAuthor) {
                    throw new RuntimeException("수정할 내용이 없습니다.");
                }

                if (isEditQuote || isEditAuthor) {
                    if (isEditQuote) {
                        editQuote = quote.getQuoteTxt();
                    } else {
                        editAuthor = quote.getQuoteAuthor();
                    }
                }

                quote.setQuoteTxt(editQuote);
                quote.setQuoteAuthor(editAuthor);

                System.out.printf("%d번 명언이 수정되었습니다.\n", quoteNo);
            }
        }
    }
}
