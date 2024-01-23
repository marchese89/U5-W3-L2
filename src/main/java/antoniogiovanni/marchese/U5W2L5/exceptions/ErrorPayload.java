package antoniogiovanni.marchese.U5W2L5.exceptions;

import java.time.LocalDateTime;

public record ErrorPayload(
        String message,
        LocalDateTime timestamp) {
}
