package fr.payetonkawa.clients.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeMessage {
    private String exchangeId;
    private String routingKey;
    private String type;
    private String correlationId;
    private Object payload;
}
