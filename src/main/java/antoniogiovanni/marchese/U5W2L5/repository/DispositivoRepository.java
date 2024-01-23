package antoniogiovanni.marchese.U5W2L5.repository;

import antoniogiovanni.marchese.U5W2L5.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, UUID> {
}
