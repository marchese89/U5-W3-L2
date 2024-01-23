package antoniogiovanni.marchese.U5W2L5.controller;

import antoniogiovanni.marchese.U5W2L5.exceptions.BadRequestException;
import antoniogiovanni.marchese.U5W2L5.model.Dispositivo;
import antoniogiovanni.marchese.U5W2L5.payloads.NewDispositivoDTO;
import antoniogiovanni.marchese.U5W2L5.payloads.ResponseDTO;
import antoniogiovanni.marchese.U5W2L5.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping
    public Page<Dispositivo> getUsers(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String orderBy) {
        return dispositivoService.getUsers(page, size, orderBy);
    }

    @GetMapping("/{deviceId}")
    public Dispositivo getUserById(@PathVariable UUID deviceId) {
        return dispositivoService.findById(deviceId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO createUser(@RequestBody @Validated NewDispositivoDTO newDispositivoDTO, BindingResult validation) {

        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors().stream().map(err -> err.getDefaultMessage()).toList().toString());
        } else {
            Dispositivo newUser = dispositivoService.save(newDispositivoDTO);

            return new ResponseDTO(newUser.getId());
        }
    }

    @PutMapping("/{deviceId}")
    public Dispositivo getUserByIdAndUpdate(@PathVariable UUID deviceId, @RequestBody NewDispositivoDTO newDispositivoDTO) {
        return dispositivoService.findByIdAndUpdate(deviceId, newDispositivoDTO);
    }

    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getUserByIdAndDelete(@PathVariable UUID deviceId) {
        dispositivoService.findByIdAndDelete(deviceId);
    }
}
