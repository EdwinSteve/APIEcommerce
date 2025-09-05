package com.example.ecommerce.dto; import lombok.Data; import java.util.UUID;
@Data public class OrderDtos {
    @Data public static class AddItem {
        private UUID productId; private int quantity;
    }
}