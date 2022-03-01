package com.treemen.daemon.services;

import com.treemen.daemon.services.dto.Addresses;
import com.treemen.daemon.services.dto.TokenHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class FullNodeService {

   Logger logger = LoggerFactory.getLogger(FullNodeService.class);

   private final String NODE_URL = "http://127.0.0.1:8080/v1a/";

   private final RestTemplate restTemplate;

   public FullNodeService(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
   }

   public TokenHistory getTokenHistory(String token) {
      try {
         MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
         ResponseEntity<TokenHistory> response = restTemplate.exchange(NODE_URL + "thin_wallet/token_history?count=1&id=" + token, HttpMethod.GET, new HttpEntity<>(headers),
                 TokenHistory.class);

         if (response.getStatusCode() == HttpStatus.OK) {
            TokenHistory history = response.getBody();
            if(history.isSuccess()){
               return history;
            } else {
               logger.warn("Could not get token history for token " + token);
            }
         }
      } catch (Exception ex) {
         logger.error("Unable to get addresses", ex);
      }

      return null;
   }
}
