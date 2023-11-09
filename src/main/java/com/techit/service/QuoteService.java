package com.techit.service;

import com.techit.dao.QuoteDao;
import com.techit.dto.QuoteDto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class QuoteService {
    private final QuoteDao quoteDao;

    public QuoteService() {
        quoteDao = new QuoteDao();
    }

    public int quoteInsert(final Scanner sc, final List<QuoteDto> quotes) {
        // 명언 리스트가 있다면 마지막 인덱스 값 id에 + 1, 없다면 1 (초기값 지정)
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

        quoteDao.save(new QuoteDto(quoteTxt, quoteAuthor));

        return quoteNo;
    }

    public QuoteDto quoteFindById(final int quoteNo) {
        return quoteDao.findById(quoteNo);
    }

    public List<QuoteDto> quoteFindAll() {
        return quoteDao.findAll();
    }

    public void printQuoteList(final List<QuoteDto> quotes) {
        validateList(quotes);

        System.out.println("  번호  |    작가    |          명언          ");
        System.out.println("================================================");

        quotes.stream()
                .sorted(Comparator.comparing(QuoteDto::getQuoteNo).reversed())
                .forEach(quote -> {
                    int insertNo = quote.getQuoteNo();
                    String author = quote.getQuoteAuthor();
                    String text = quote.getQuoteTxt();
                    System.out.printf("   %d    |    %s    |  %s \n", insertNo, author, text);
                });
    }

    public void quoteRemove(final List<QuoteDto> quotes, final int quoteNo) {
        validateList(quotes);
        noQuoteNo(quoteNo);

        quotes.stream()
                .filter(quote -> quote.getQuoteNo() == quoteNo)
                .findFirst()
                .map(quote -> {
                    quoteDao.remove(quoteNo);
                    return quote;
                })
                .orElseThrow(() -> new RuntimeException(quoteNo + "번 명언은 존재하지 않습니다."));
    }

    public void quoteUpdate(final Scanner sc, final List<QuoteDto> quotes, final int quoteNo) {
        validateList(quotes);
        noQuoteNo(quoteNo);

        quotes.stream()
                .filter(quote -> quote.getQuoteNo() == quoteNo)
                .findFirst()
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

                    quoteDao.update(new QuoteDto(quoteNo, editQuote, editAuthor));

                    return quoteFindById(quoteNo);
                }).orElseThrow(() -> new RuntimeException(quoteNo + "번 명언은 존재하지 않습니다."));
    }

    // 종료 시 DB Close
    public void close() {
        quoteDao.connectionClose();
    }

    // 명언 리스트가 존재하는지 체크
    private void validateList(final List<QuoteDto> quotes) {
        Optional.ofNullable(quotes)
                .filter(q -> !q.isEmpty())
                .orElseThrow(() -> new RuntimeException("등록된 명언이 없습니다."));
    }

    // 수정 시 명언 또는 작성자가 null 이라면 기존 값을 유지
    private String returnUpdateTxt(final String inputStr, final String editTxt) {
        return Optional.ofNullable(inputStr)
                .filter(str -> !str.isBlank())
                .orElse(editTxt);
    }

    // Null Or Blank 체크
    private boolean isNullOrBlank(final String inputStr) {
        return Optional.ofNullable(inputStr)
                .map(String::isBlank)
                .orElse(true);
    }

    // 쿼리스트링 없이 수정, 삭제만 입력 시 예외처리
    private void noQuoteNo(final int quoteNo) {
        Optional.of(quoteNo)
                .filter(q -> q != -1)
                .orElseThrow(() -> new IllegalArgumentException("명언 번호를 입력해 주세요."));
    }
}
