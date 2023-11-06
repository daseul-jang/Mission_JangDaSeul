package com.techit.controller;

import com.techit.dto.QuoteDto;
import com.techit.service.QuoteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuoteController {
    private final QuoteService quoteService;
    private final List<QuoteDto> quotes;
    private final Scanner sc;

    public QuoteController(Scanner sc) {
        this.sc = sc;
        quoteService = new QuoteService();
        quotes = new ArrayList<>();
    }

    public void registerQuote() {
        try {
            QuoteDto quoteDto = quoteService.quoteInsert(sc, quotes);
            quotes.add(quoteDto);
            System.out.printf("%s번 명언이 등록되었습니다.\n", quoteDto.getQuoteNo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("등록을 다시 진행해 주세요.");
        }
    }

    public void listOfQuote() {
        try {
            quoteService.quoteList(quotes);
        } catch (Exception e) {
            System.out.println("             " + e.getMessage());
        }
    }

    public void deleteQuote(int quoteNo) {
        try {
            quoteService.quoteRemove(quotes, quoteNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateQuote(int quoteNo) {
        try {
            quoteService.quoteUpdate(sc, quotes, quoteNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
