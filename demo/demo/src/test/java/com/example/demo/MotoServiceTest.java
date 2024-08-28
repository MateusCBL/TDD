package com.example.demo;

import com.example.demo.Domain.Moto;
import com.example.demo.Domain.Status;
import com.example.demo.Repository.MotoRepository;
import com.example.demo.Service.MotoService;
import com.example.demo.exception.MotoNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MotoServiceTest {

    @Mock
    private MotoRepository motoRepository;

    @InjectMocks
    private MotoService motoService;

    @Test
    @DisplayName("Success - Should Save Moto With Success")
    void shouldSaveMotoWithSuccess() {

        when(motoRepository.save(ArgumentMatchers.any(Moto.class))).thenReturn(createMoto());
        Moto created = motoService.createMoto(createMoto());
        assertThat(created.getName()).isSameAs(createMoto().getName());
        assertNotNull(created.getId());
        assertEquals(created.getId(), 1);
    }

    @Test
    @DisplayName("Success - should return the list of motos with success")
    void shouldReturnTheListOfMotosWithSuccess() {
        when(motoRepository.findAll()).thenReturn(List.of(createMoto()));
        List<Moto> motos = this.motoService.listAllMotos();
        assertEquals(1, motos.size());
    }

    @Test
    @DisplayName("Success - should find a moto by id with success")
    void ShouldFindMotoByIdWithSuccess() throws MotoNotFoundException {
        Moto moto = new Moto();
        moto.setId(1L);
        moto.setName("Honda CB500X");
        moto.setStatus(Status.NOT_STARTED);
        moto.setCategory(Category.ADVENTURE);

        when(motoRepository.findById(moto.getId())).thenReturn(Optional.of(createMoto()));
        Optional<Moto> expected = motoService.getMotoById(moto.getId());
        assertThat(expected.get().getId()).isEqualTo(createMoto().getId());
        assertThat(expected.get().getName()).isEqualTo(createMoto().getName());
        assertThat(expected.get().getStatus()).isEqualTo(createMoto().getStatus());
        assertThat(expected.get().getCategory()).isEqualTo(createMoto().getCategory());
        assertDoesNotThrow(() -> {
            motoService.getMotoById(moto.getId());
        });
    }

    @Test
    @DisplayName("Error - should throw exception when try to find a moto by id")
    void ShouldThrowExceptionWhenTryToFindAMoto() throws MotoNotFoundException {
        Moto moto = new Moto();
        moto.setId(1L);
        moto.setName("Honda CB500X");
        moto.setStatus(Status.NOT_STARTED);
        moto.setCategory(Category.ADVENTURE);

        when(motoRepository.findById(200L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(MotoNotFoundException.class,
                () -> motoService.getMotoById(200L));
        assertEquals("Moto with id " + 200L + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Success - should delete moto with success")
    void shouldDeleteMotoWithSuccess() {
        when(motoRepository.findById(createMoto().getId())).thenReturn(Optional.empty());

        ResponseEntity<Object> expected = motoService.deleteMotoById(createMoto().getId());
        assertThat(expected.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Success - should find a moto by partial name with success")
    void ShouldFindMotoByPartialName() {
        Moto moto = new Moto();
        moto.setId(1L);
        moto.setName("Kawasaki");
        moto.setStatus(Status.IN_PROGRESS);
        moto.setCategory(Category.SPORT);

        when(motoRepository.findByNameStartingWith("Kaw")).thenReturn(List.of(moto));
        List<Moto> expected = motoService.listMotosThatStartsWithPartialName("Kaw");
        assertThat(expected.get(0).getId()).isEqualTo(moto.getId());
        assertThat(expected.get(0).getName()).isEqualTo(moto.getName());
        assertThat(expected.get(0).getCategory()).isEqualTo(moto.getCategory());
        assertThat(expected.get(0).getStatus()).isEqualTo(moto.getStatus());
        assertThat(expected.get(0).getName()).isNotEqualTo(createMoto().getName());
    }

    private Moto createMoto() {
        Moto moto = new Moto();
        moto.setId(1L);
        moto.setName("Honda CB500X");
        moto.setStatus(Status.NOT_STARTED);
        moto.setCategory(Category.ADVENTURE);
        return moto;
    }
}


