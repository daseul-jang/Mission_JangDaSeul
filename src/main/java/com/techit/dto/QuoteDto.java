package com.techit.dto;

import lombok.*;
import org.json.JSONObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuoteDto {
    private int quoteNo;
    private String quoteTxt;
    private String quoteAuthor;

    public QuoteDto(String quoteTxt, String quoteAuthor) {
        this.quoteTxt = quoteTxt;
        this.quoteAuthor = quoteAuthor;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("quoteNo", quoteNo);
        json.put("quoteAuthor", quoteAuthor);
        json.put("quoteTxt", quoteTxt);

        return json;
    }
}
