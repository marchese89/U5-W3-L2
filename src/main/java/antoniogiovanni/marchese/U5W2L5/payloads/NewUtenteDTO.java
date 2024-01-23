package antoniogiovanni.marchese.U5W2L5.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewUtenteDTO(
        @NotEmpty(message = "lo username non può essere vuoto")
        @NotNull(message = "lo username non può essere null")
        String username,
        @NotEmpty(message = "il nome non può essere vuoto")
        @NotNull(message = "il nome non può essere null")
        String nome,
        @NotEmpty(message = "il cognome non può essere vuoto")
        @NotNull(message = "il cognome non può essere null")
        String cognome,
        @NotEmpty(message = "l'email non può essere vuota")
        @NotNull(message = "l'email non può essere null")
        @Email(message = "email non formattata correttamente")
        String email,
        @NotEmpty(message = "la password non può essere vuota")
        @NotNull(message = "la password non può essere null")
        String password
) {
}
