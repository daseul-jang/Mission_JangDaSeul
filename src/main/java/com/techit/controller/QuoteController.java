package com.techit.controller;

import com.techit.dto.QuoteDto;
import com.techit.service.FileService;
import com.techit.service.QuoteService;

import java.util.List;
import java.util.Scanner;

public class QuoteController {
    private final QuoteService quoteService;
    private final FileService fileService;
    private final List<QuoteDto> quotes;
    private final Scanner sc;

    public QuoteController(Scanner sc) {
        this.sc = sc;
        quoteService = new QuoteService();
        fileService = new FileService();
        quotes = fileService.loadDataFromJson();
    }

    public void exitAndFileSave() {
        if (!quotes.isEmpty()) fileService.saveDataToJson(quotes);
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

    public void runBuild() {
        try {
            fileService.saveDataToJson(quotes);
            String fileName = fileService.getFileName();
            System.out.println(fileName + " 파일의 내용이 갱신되었습니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
