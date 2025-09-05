package com.example.ecommerce.dto; import lombok.Data;
@Data public class PaymentDtos {
    @Data public static class PayRequest {
        private String acceptanceToken;
        private String sessionId;
        private String customerEmail;
    }
}