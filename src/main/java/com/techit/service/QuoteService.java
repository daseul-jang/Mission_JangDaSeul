package com.techit.service;

import com.techit.dto.QuoteDto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class QuoteService {

    public QuoteDto quoteInsert(Scanner sc, List<QuoteDto> quotes) {
        int quoteNo = Optional.ofNullable(quotes)
                .filter(q -> !q.isEmpty())
                .map(q -> q.getLast().getQuoteNo() + 1)
                .orElse(1);

        System.out.print("명언 : ");
        String quoteTxt = sc.nextLine();

        System.out.print("작가 : ");
        String quoteAuthor = sc.nextLine();

        if (isNullOrBlank(quoteTxt) || isNullOrBlank(quoteAuthor)) {
            throw new RuntimeException("명언과 작가는 비워둘 수 없습니다.");
        }

        return new QuoteDto(quoteNo, quoteTxt, quoteAuthor);
    }

    public void quoteList(List<QuoteDto> quotes) {
        System.out.println("  번호  |    작가    |          명언          ");
        System.out.println("================================================");

        validateList(quotes);

        quotes.stream()
                .sorted(Comparator.comparing(QuoteDto::getQuoteNo).reversed())
                .forEach(quote -> {
                    int insertNo = quote.getQuoteNo();
                    String author = quote.getQuoteAuthor();
                    String text = quote.getQuoteTxt();
                    System.out.printf("   %d    |    %s    |  %s \n", insertNo, author, text);
                });
    }

    public void quoteRemove(List<QuoteDto> quotes, int quoteNo) {
        validateList(quotes);

        if (quotes.removeIf(quote -> quote.getQuoteNo() == quoteNo)) {
            System.out.printf("%d번 명언이 삭제되었습니다.\n", quoteNo);
        } else {
            throw new RuntimeException(quoteNo + "번 명언은 존재하지 않습니다.");
        }
    }

    public List<QuoteDto> quoteUpdate(Scanner sc, List<QuoteDto> quotes, int quoteNo) {
        validateList(quotes);

        return quotes.stream()
                .filter(quote -> quote.getQuoteNo() == quoteNo)
                .map(quote -> {
                    System.out.printf("명언(기존) : %s\n", quote.getQuoteTxt());
                    System.out.print("명언 : ");
                    String editQuote = sc.nextLine();

                    System.out.printf("작가(기존) : %s\n", quote.getQuoteAuthor());
                    System.out.print("작가 : ");
                    String editAuthor = sc.nextLine();

                    if (isNullOrBlank(editQuote) && isNullOrBlank(editAuthor)) {
                        throw new RuntimeException("수정할 내용이 없습니다.");
                    }

                    editQuote = returnUpdateTxt(editQuote, quote.getQuoteTxt());
                    editAuthor = returnUpdateTxt(editAuthor, quote.getQuoteAuthor());

                    System.out.printf("%d번 명언이 수정되었습니다.\n", quoteNo);
                    return new QuoteDto(quoteNo, editQuote, editAuthor);
                })
                .toList();
    }

    private void validateList(List<QuoteDto> quotes) {
        Optional.ofNullable(quotes)
                .filter(q -> !q.isEmpty())
                .orElseThrow(() -> new RuntimeException("등록된 명언이 없습니다."));
    }

    private String returnUpdateTxt(String inputStr, String editTxt) {
        return Optional.ofNullable(inputStr)
                .filter(str -> !str.isBlank())
                .orElse(editTxt);
    }

    private boolean isNullOrBlank(String inputStr) {
        return Optional.ofNullable(inputStr)
                .map(String::isBlank)
                .orElse(true);
    }
}
