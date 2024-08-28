package com.example.demo.Domain;

import com.example.demo.Service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class Moto {

    @Autowired
    MotoService motoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Moto createMoto(@RequestBody Moto moto) {
        return motoService.createMoto(moto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Moto> listAllMotos() {
        return motoService.listAllMotos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Moto> getMotoById(@PathVariable(value = "id") Long id) throws Exception {
        return motoService.getMotoById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Moto> updateMotoById(@PathVariable(value = "id") Long id, @RequestBody Moto moto) {
        return motoService.updateMotoById(moto, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteMotoById(@PathVariable(value = "id") Long id) {
        return motoService.deleteMotoById(id);
    }

    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    public List<Moto> listMotosThatStartsWithPartialName(@RequestParam String name) {
        return motoService.listMotosThatStartsWithPartialName(name);
    }
}


