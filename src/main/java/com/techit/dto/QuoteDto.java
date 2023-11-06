package com.techit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {
    private int quoteNo;
    private String quoteTxt;
    private String quoteAuthor;

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("quoteNo", quoteNo);
        json.put("quoteAuthor", quoteAuthor);
        json.put("quoteTxt", quoteTxt);

        return json;
    }
}
