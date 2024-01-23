package antoniogiovanni.marchese.U5W2L5.service;

import antoniogiovanni.marchese.U5W2L5.exceptions.NotFoundException;
import antoniogiovanni.marchese.U5W2L5.model.Dispositivo;
import antoniogiovanni.marchese.U5W2L5.model.Role;
import antoniogiovanni.marchese.U5W2L5.model.Utente;
import antoniogiovanni.marchese.U5W2L5.payloads.NewDispositivoDTO;
import antoniogiovanni.marchese.U5W2L5.payloads.NewUtenteDTO;
import antoniogiovanni.marchese.U5W2L5.repository.UtenteRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired DispositivoService dispositivoService;

    @Autowired
    private Cloudinary cloudinaryUploader;

//    @Autowired
    private PasswordEncoder bcrypt =  new BCryptPasswordEncoder(11);

    public Page<Utente> getUsers(int page, int size, String orderBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());

        return utenteRepository.findAll(pageable);
    }

    public Utente save(NewUtenteDTO utenteDTO) {
        Utente newUtente = new Utente();
        newUtente.setNome(utenteDTO.nome());
        newUtente.setCognome(utenteDTO.cognome());
        newUtente.setUsername(utenteDTO.username());
        newUtente.setEmail(utenteDTO.email());
        newUtente.setPassword(bcrypt.encode(utenteDTO.password()));
        newUtente.setRole(Role.USER);
        return utenteRepository.save(newUtente);
    }

    public Utente findById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        Utente found = this.findById(id);
        utenteRepository.delete(found);
    }

    public Utente findByIdAndUpdate(UUID id, NewUtenteDTO utente) {
        Utente found = this.findById(id);
        found.setEmail(utente.email());
        found.setUsername(utente.username());
        found.setNome(utente.nome());
        found.setCognome(utente.cognome());
        return utenteRepository.save(found);
    }

    public void addDeviceToUser(UUID userId, UUID deviceId) {
        Utente foundUtente = this.findById(userId);
        Dispositivo foundDispositivo = dispositivoService.findById(deviceId);
        foundUtente.getDispositivoList().add(foundDispositivo);
        foundDispositivo.setUtente(foundUtente);
        dispositivoService.findByIdAndAddUser(deviceId,foundDispositivo);
    }

    public Utente uploadAvatar(UUID userId, MultipartFile file)  throws IOException {
        Utente found = this.findById(userId);
        String avatarURL = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(avatarURL);
        return utenteRepository.save(found);
    }

    public Utente findByEmail(String email) throws NotFoundException {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

}
