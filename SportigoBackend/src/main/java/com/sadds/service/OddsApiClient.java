package com.sadds.service;

import com.sadds.dto.OddsApiEventDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OddsApiClient {

    private final RestTemplate restTemplate;
    private final String apiKey = "c30013d9202a932d6311be7bf5ca2046";

    public OddsApiClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public OddsApiEventDto[] fetchOdds(String sportKey) {
        String url = "https://api.the-odds-api.com/v4/sports/"
                + sportKey +
                "/odds?apiKey="
                + apiKey
                + "&regions=us&markets=h2h,spreads,totals&oddsFormat=decimal";

        ResponseEntity<OddsApiEventDto[]> response = restTemplate
                .getForEntity(url, OddsApiEventDto[].class);

        // logging
        OddsApiEventDto[] body = response.getBody();
        System.out.println("Fetched " + (body != null ? body.length : 0) + " events.");
        if (body != null && body.length > 0) {
            System.out.println("Sample event sport_key: " + body[0].sport_key());
            System.out.println("Markets in first bookmaker: " +
                    body[0].bookmakers().get(0).markets().stream().map(m -> m.key()).toList());
        }

        return response.getBody();
    }
}
