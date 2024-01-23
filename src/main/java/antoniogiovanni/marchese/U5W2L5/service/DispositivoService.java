package antoniogiovanni.marchese.U5W2L5.service;

import antoniogiovanni.marchese.U5W2L5.enums.StatoDispositivo;
import antoniogiovanni.marchese.U5W2L5.exceptions.NotFoundException;
import antoniogiovanni.marchese.U5W2L5.model.Dispositivo;
import antoniogiovanni.marchese.U5W2L5.payloads.NewDispositivoDTO;
import antoniogiovanni.marchese.U5W2L5.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;

    public Page<Dispositivo> getUsers(int page, int size, String orderBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());

        return dispositivoRepository.findAll(pageable);
    }

    public Dispositivo save(NewDispositivoDTO dispositivoDTO) {
        Dispositivo newDispositivo = new Dispositivo();
        newDispositivo.setStatoDispositivo(dispositivoDTO.statoDispositivo());
        newDispositivo.setTipologia(dispositivoDTO.tipologia());

        return dispositivoRepository.save(newDispositivo);
    }

    public Dispositivo findById(UUID id) {
        return dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        Dispositivo found = this.findById(id);
        dispositivoRepository.delete(found);
    }

    public Dispositivo findByIdAndUpdate(UUID id, NewDispositivoDTO dispositivo) {
        Dispositivo found = this.findById(id);
        found.setTipologia(dispositivo.tipologia());
        found.setStatoDispositivo(dispositivo.statoDispositivo());
        return dispositivoRepository.save(found);
    }
    public Dispositivo findByIdAndAddUser(UUID id, Dispositivo dispositivo) {
        Dispositivo found = this.findById(id);
        found.setUtente(dispositivo.getUtente());
        found.setStatoDispositivo(StatoDispositivo.ASSEGNATO);
        return dispositivoRepository.save(found);
    }

}
