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
    public void failed_adding_actor_DataIntegrityViolationException() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.doThrow(new DataIntegrityViolationException("")).when(actorRepoRepository).save(actor);

        var result = actorService.addActor(ActorMapper.convert(Either.left(actor)).get());

        Assertions.assertEquals("Error occurred on saving new actor due to Data Integrity Violation", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void failed_adding_actor_GenericException() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.doThrow(new NoSuchElementException("")).when(actorRepoRepository).save(actor);

        var result = actorService.addActor(ActorMapper.convert(Either.left(actor)).get());

        Assertions.assertEquals("Error occurred on saving new actor", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void failed_adding_actor_null_input() {
        var result = actorService.addActor(null);

        Assertions.assertEquals("Add actor input is null", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void success_deleting_actor() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.when(actorRepoRepository.findById(uuid)).thenReturn(Optional.of(actor));
        Mockito.doNothing().when(actorRepoRepository).delete(actor);


        var result = actorService.removeActor(uuid);

        Assertions.assertNotNull(result.getResponse());
        Assertions.assertEquals("Successfully deleted an actor", result.getMessage());
        Assertions.assertEquals("Success", result.getStatus());
    }

    @Test
    public void failed_deleting_actor_EntityNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Mockito.doThrow(new EntityNotFoundException("")).when(actorRepoRepository).findById(uuid);

        var result = actorService.removeActor(uuid);

        Assertions.assertEquals("Error occurred while removing actor", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void failed_deleting_actor_GenericException() {
        UUID uuid = UUID.randomUUID();
        Mockito.doThrow(new NoSuchElementException("")).when(actorRepoRepository).findById(uuid);

        var result = actorService.removeActor(uuid);

        Assertions.assertEquals("Error occurred while removing an actor", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void failed_deleting_actor_null_input() {
        var result = actorService.removeActor(null);

        Assertions.assertEquals("Delete actor input is null", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void success_updating_actor() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.when(actorRepoRepository.findById(uuid)).thenReturn(Optional.of(actor));
        Mockito.when(actorRepoRepository.save(actor)).thenReturn(actor);

        var result = actorService.updateActor(uuid, ActorMapper.convert(Either.left(actor)).get());

        Assertions.assertNotNull(result.getResponse());
        Assertions.assertEquals("Successfully updated an actor", result.getMessage());
        Assertions.assertEquals("Success", result.getStatus());
    }

    @Test
    public void failed_updating_actor_EntityNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.doThrow(new EntityNotFoundException("")).when(actorRepoRepository).findById(uuid);

        var result = actorService.updateActor(uuid, ActorMapper.convert(Either.left(actor)).get());

        Assertions.assertEquals("Error occurred while updating actor", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void failed_updating_actor_GenericException() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.doThrow(new NoSuchElementException("")).when(actorRepoRepository).findById(uuid);

        var result = actorService.updateActor(uuid, ActorMapper.convert(Either.left(actor)).get());

        Assertions.assertEquals("Error occurred while updating an actor", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void failed_update_actor_null_input() {
        Actor actor = new Actor(null, "Aldrich", "");
        var result = actorService.updateActor(null, ActorMapper.convert(Either.left(actor)).get());

        Assertions.assertEquals("Update actor input is null", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void success_fetching_actor() {
        UUID uuid = UUID.randomUUID();
        Actor actor = new Actor(uuid, "Aldrich", "");
        Mockito.when(actorRepoRepository.findById(uuid)).thenReturn(Optional.of(actor));

        var result = actorService.getActor(uuid);

        Assertions.assertNotNull(result.getResponse());
        Assertions.assertEquals("Successfully fetched an actor", result.getMessage());
        Assertions.assertEquals("Success", result.getStatus());
    }

    @Test
    public void failed_fetching_actor_EntityNotFoundException() {
        UUID uuid = UUID.randomUUID();
        Mockito.doThrow(new EntityNotFoundException("")).when(actorRepoRepository).findById(uuid);

        var result = actorService.getActor(uuid);

        Assertions.assertEquals("Error occurred while fetching actor", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void failed_fetching_actor_null_input() {
        var result = actorService.getActor(null);

        Assertions.assertEquals("Fetch actor input is null", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }

    @Test
    public void failed_fetching_actor_GenericException() {
        UUID uuid = UUID.randomUUID();
        Mockito.doThrow(new NoSuchElementException("")).when(actorRepoRepository).findById(uuid);

        var result = actorService.getActor(uuid);

        Assertions.assertEquals("Error occurred while fetching an actor", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
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

    @Test
    public void failed_fetching_all_actors() {
        Mockito.doThrow(new EntityNotFoundException("")).when(actorRepoRepository).findAll();

        var result = actorService.getActors();

        Assertions.assertEquals("Error occurred while fetching all actors", result.getMessage());
        Assertions.assertEquals("Error", result.getStatus());
    }


}
