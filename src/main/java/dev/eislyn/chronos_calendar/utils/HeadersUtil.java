package dev.eislyn.chronos_calendar.utils;

import org.springframework.http.HttpHeaders;

public class HeadersUtil {
    public static HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
//        headers.add("access-control-allow-credentials", "true");
//        headers.add("access-control-allow-origin", "*");
//        headers.add("cache-control", "no-cache");
//        headers.add("Access-Control-Allow-Origin", "*");
//        headers.add("Access-Control-Allow-Headers", "Content-Type,Authorization");
//        headers.add("Access-Control-Allow-Methods", "OPTIONS,POST,GET,PUT,DELETE");
//        headers.add("Access-Control-Allow-Credentials", "true");
//        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}