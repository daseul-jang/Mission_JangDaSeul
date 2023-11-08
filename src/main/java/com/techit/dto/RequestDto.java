package com.techit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private String cmd;
    private String action;
    private String queryString;
    private Map<String, String> paramsMap;

    public RequestDto(final String cmd) {
        this.cmd = cmd;
        this.action = cmd;
        paramsMap = new HashMap<>();

        if (!cmd.contains("?")) return;

        String[] cmdBits = cmd.split("\\?", 2);
        action = cmdBits[0].trim();
        queryString = cmdBits[1].trim();

        String[] queryStringBits = queryString.split("&");

        for (String param : queryStringBits) {
            String[] paramBits = param.split("=", 2);
            paramsMap.put(paramBits[0], paramBits[1]);
        }
    }

    // 쿼리스트링 중 id의 값을 가져와 null 이 아니면 int 로 변환한 값을 리턴, null 이면 -1 리턴
    public int getQuoteNo() {
        return Optional.ofNullable(paramsMap.get("id"))
                .map(Integer::parseInt)
                .orElse(-1);
    }
}
