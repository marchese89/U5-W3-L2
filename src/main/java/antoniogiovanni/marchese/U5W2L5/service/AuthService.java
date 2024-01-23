package antoniogiovanni.marchese.U5W2L5.service;

import antoniogiovanni.marchese.U5W2L5.exceptions.UnauthorizedException;
import antoniogiovanni.marchese.U5W2L5.model.Utente;
import antoniogiovanni.marchese.U5W2L5.payloads.UserLoginDTO;
import antoniogiovanni.marchese.U5W2L5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO body) {
        // 1. Verifichiamo che l'email dell'utente sia nel db
        Utente user = utenteService.findByEmail(body.email());

        // 2. In caso affermativo, verifichiamo se la password fornita corrisponde a quella trovata nel db
        if (bcrypt.matches(body.password(),user.getPassword())) {
            // 3. Se le credenziali sono OK --> Genere un token JWT e lo ritorno
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401 (Unauthorized)
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }
}
