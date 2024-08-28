package com.example.demo.Repository;

import com.example.demo.Domain.Moto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {

    List<Moto> findByNameStartingWith(String name);
}