package com.techit.controller;

import com.techit.dto.QuoteDto;
import com.techit.service.QuoteService;

import java.util.List;
import java.util.Scanner;

public class QuoteController {
    private final Scanner sc;
    private final QuoteService quoteService;
    private List<QuoteDto> quotes;

    public QuoteController(Scanner sc) {
        this.sc = sc;
        quoteService = new QuoteService();
        quotes = quoteService.quoteFindAll();
    }

    public void registerQuote() {
        try {
            int quoteNo = quoteService.quoteInsert(sc, quotes);
            System.out.printf("%s번 명언이 등록되었습니다.\n", quoteNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("등록을 다시 진행해 주세요.");
        }
    }

    public void listOfQuote() {
        try {
            quotes = quoteService.quoteFindAll();
            quoteService.printQuoteList(quotes);
        } catch (Exception e) {
            System.out.println("             " + e.getMessage());
        }
    }

    public void deleteQuote(final int quoteNo) {
        try {
            quoteService.quoteRemove(quotes, quoteNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateQuote(final int quoteNo) {
        try {
            quoteService.quoteUpdate(sc, quotes, quoteNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeQuote() {
        quoteService.close();
    }
}
