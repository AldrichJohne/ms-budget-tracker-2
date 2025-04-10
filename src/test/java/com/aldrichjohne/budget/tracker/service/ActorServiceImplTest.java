package com.aldrichjohne.budget.tracker.service;

import com.aldrichjohne.budget.tracker.model.entity.Actor;
import com.aldrichjohne.budget.tracker.repository.ActorRepo;
import com.aldrichjohne.budget.tracker.service.impl.ActorServiceImpl;
import com.aldrichjohne.budget.tracker.util.mapper.ActorMapper;
import io.vavr.control.Either;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({SpringExtension.class})
public class ActorServiceImplTest {
    @Mock
    ActorRepo actorRepoRepository;

    @InjectMocks
    ActorServiceImpl actorService;

    @Test
    public void success_adding_actor() {
        Actor actor = new Actor(UUID.randomUUID(), "Aldrich", "");
        Mockito.when(actorRepoRepository.save(actor)).thenReturn(actor);

        var result = actorService.addActor(ActorMapper.convert(Either.left(actor)).get());

        Assertions.assertNotNull(result.getResponse());
        Assertions.assertEquals("Successfully added an actor", result.getMessage());
        Assertions.assertEquals("Success", result.getStatus());
    }

    @Test
    public void failed_adding_actor_null_input() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            actorService.addActor(null);
        });

        Assertions.assertEquals("Add Actor: Input Body is null", exception.getMessage());
    }

    @Test
    public void success_deleting_actor() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.when(actorRepoRepository.findById(uuid)).thenReturn(Optional.of(actor));
        Mockito.doNothing().when(actorRepoRepository).delete(actor);


        var result = actorService.removeActor(uuid);

        Assertions.assertNotNull(result.getResponse());
        Assertions.assertEquals("Delete Actor: Success", result.getMessage());
        Assertions.assertEquals("Success", result.getStatus());
    }

    @Test
    public void failed_deleting_actor_EntityNotFoundException() {
        UUID uuid = UUID.randomUUID();

        Mockito.when(actorRepoRepository.findById(uuid)).thenReturn(Optional.empty());

        EntityNotFoundException thrownException = assertThrows(EntityNotFoundException.class, () -> {
            actorService.removeActor(uuid);
        });

        Assertions.assertEquals("Delete Actor: Actor with ID = " + uuid + " not found", thrownException.getMessage());
    }


    @Test
    public void failed_deleting_actor_null_input() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            actorService.removeActor(null);
        });

        Assertions.assertEquals("Delete Actor: Input ID is null", exception.getMessage());
    }

    @Test
    public void success_updating_actor() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.when(actorRepoRepository.findById(uuid)).thenReturn(Optional.of(actor));
        Mockito.when(actorRepoRepository.save(actor)).thenReturn(actor);

        var result = actorService.updateActor(ActorMapper.convert(Either.left(actor)).get());

        Assertions.assertNotNull(result.getResponse());
        Assertions.assertEquals("Successfully updated an actor", result.getMessage());
        Assertions.assertEquals("Success", result.getStatus());
    }

    @Test
    public void failed_updating_actor_EntityNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.when(actorRepoRepository.findById(uuid)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
           actorService.updateActor(ActorMapper.convert(Either.left(actor)).get());
        });

        Assertions.assertEquals("Update Actor: Actor with ID = "+uuid+" not found", exception.getMessage());
    }

    @Test
    public void failed_update_actor_null_input() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
           actorService.updateActor(null);
        });

        Assertions.assertEquals("Update Actor: Input Body is null", exception.getMessage());
    }

    @Test
    public void success_fetching_actor() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.when(actorRepoRepository.findById(uuid)).thenReturn(Optional.of(actor));

        var result = actorService.getActor(uuid);

        Assertions.assertNotNull(result.getResponse());
        Assertions.assertEquals("Successfully retrieved an actor", result.getMessage());
        Assertions.assertEquals("Success", result.getStatus());
    }

    @Test
    public void failed_retrieving_actor_EntityNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(actorRepoRepository.findById(uuid)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            actorService.getActor(uuid);
        });

        Assertions.assertEquals("Get Actor: Actor with ID = "+uuid+" not found", exception.getMessage());
    }

    @Test
    public void failed_fetching_actor_null_input() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            actorService.getActor(null);
        });

        Assertions.assertEquals("Retrieve Actor: Input ID is null", exception.getMessage());
    }

    @Test
    public void success_fetching_all_actor() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Actor actor1 = new Actor(uuid, "Nova", "");
        Mockito.when(actorRepoRepository.findAll()).thenReturn(List.of(actor, actor1));

        var result = actorService.getActors();

        Assertions.assertNotNull(result.getResponse());
        Assertions.assertEquals("Successfully fetched all actors", result.getMessage());
        Assertions.assertEquals("Success", result.getStatus());
    }

}
