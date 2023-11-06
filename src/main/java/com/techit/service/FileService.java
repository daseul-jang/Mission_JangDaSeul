package com.techit.service;

import com.techit.dto.QuoteDto;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class FileService {
    private final String FILE_DIR = "H:/workspace/techit/file";
    private final String FILE_PATH = FILE_DIR + "/data.json";

    private final Path dirPath = Paths.get(FILE_DIR);
    private final Path jsonFilePath = Paths.get(FILE_PATH);

    public String getFileName() {
        return jsonFilePath.getFileName().toString();
    }

    public void saveDataToJson(List<QuoteDto> quotes) {
        if (quotes.isEmpty()) {
            return;
        }

        JSONArray jsonArray = new JSONArray();

        for (QuoteDto quote : quotes) {
            jsonArray.put(quote.toJson());
        }

        try {
            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Files.write(jsonFilePath, jsonArray.toString().getBytes());
        } catch (Exception e) {
            log.error(e.getStackTrace());
        }
    }

    public List<QuoteDto> loadDataFromJson() {
        List<QuoteDto> quotes = new ArrayList<>();

        try {
            if (Files.exists(jsonFilePath)) {
                String jsonStr = Files.readString(jsonFilePath);

                JSONArray jsonArray = new JSONArray(jsonStr);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    QuoteDto quoteDto = new QuoteDto();
                    quoteDto.setQuoteNo(jsonData.getInt("quoteNo"));
                    quoteDto.setQuoteAuthor(jsonData.getString("quoteAuthor"));
                    quoteDto.setQuoteTxt(jsonData.getString("quoteTxt"));
                    quotes.add(quoteDto);
                }
            }

        } catch (Exception e) {
            log.error(e.getStackTrace());
        }

        return quotes;
    }
}
