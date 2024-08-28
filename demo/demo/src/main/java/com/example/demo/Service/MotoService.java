package com.example.demo.Service;

import com.example.demo.Domain.Moto;
import com.example.demo.Repository.MotoRepository;
import com.example.demo.exception.MotoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public Moto createMoto(Moto moto) {
        return motoRepository.save(moto);
    }

    public List<Moto> listAllMotos() {
        return motoRepository.findAll();
    }

    public Optional<Moto> getMotoById(Long id) throws MotoNotFoundException {
        if (motoRepository.findById(id).isEmpty()) {
            throw new MotoNotFoundException(id);
        }
        return motoRepository.findById(id);
    }

    public ResponseEntity<Moto> updateMotoById(Moto moto, Long id) {
        return motoRepository.findById(id).map(motoToUpdate -> {
            motoToUpdate.setName(moto.getName());
            motoToUpdate.setCategory(moto.getCategory());
            motoToUpdate.setStatus(moto.getStatus());
            Moto updated = motoRepository.save(motoToUpdate);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> deleteMotoById(Long id) {
        return motoRepository.findById(id).map(motoToDelete -> {
            motoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    public List<Moto> listMotosThatStartsWithPartialName(String partialName) {
        return motoRepository.findByNameStartingWith(partialName);
    }
}
