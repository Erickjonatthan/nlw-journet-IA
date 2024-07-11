package com.rocketseat.planner.trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketseat.planner.activitie.ActivityData;
import com.rocketseat.planner.activitie.ActivityRequestPayload;
import com.rocketseat.planner.activitie.ActivityResponse;
import com.rocketseat.planner.activitie.ActivityService;
import com.rocketseat.planner.link.LinkData;
import com.rocketseat.planner.link.LinkRequestPayload;
import com.rocketseat.planner.link.LinkResponse;
import com.rocketseat.planner.link.LinkService;
import com.rocketseat.planner.participant.ParticipantCreateResponse;
import com.rocketseat.planner.participant.ParticipantData;
import com.rocketseat.planner.participant.ParticipantRequestPayload;
import com.rocketseat.planner.participant.ParticipantService;

//OBS: O banco de dados é em memória, então os dados são perdidos ao reiniciar a aplicação. 

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TripRepository repository;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayLoad payLoad) {
        Trip newTrip = new Trip(payLoad);

        this.repository.save(newTrip);

        this.participantService.registerParticipantsToTrip(payLoad.emailsToInvite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));


    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id){
        Optional<Trip> trip = this.repository.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayLoad payLoad) {
        Optional<Trip> trip = this.repository.findById(id);

        if(trip.isPresent()){
            Trip tripToUpdate = trip.get();

            tripToUpdate.setEndsAt(LocalDateTime.parse(payLoad.startsAt(), DateTimeFormatter.ISO_DATE_TIME));
            tripToUpdate.setStartsAt(LocalDateTime.parse(payLoad.endsAt(), DateTimeFormatter.ISO_DATE_TIME));
            tripToUpdate.setDestination(payLoad.destination());
            tripToUpdate.setOwnerName(payLoad.ownerName());
            tripToUpdate.setOwnerEmail(payLoad.ownerEmail());
            
            this.repository.save(tripToUpdate);

            return ResponseEntity.ok(tripToUpdate);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.repository.findById(id);

        if (trip.isPresent()) {
            Trip tripToUpdate = trip.get();

            tripToUpdate.setIsConfirmed(true);

            this.repository.save(tripToUpdate);
            this.participantService.triggerConfirmationEmailToParticipants(id);

            return ResponseEntity.ok(tripToUpdate);
        }

        return ResponseEntity.notFound().build();}

        @PostMapping("/{id}/invite")
        public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payLoad) {
            Optional<Trip> trip = this.repository.findById(id);

            if (trip.isPresent()) {
                Trip tripToUpdate = trip.get();
            
                ParticipantCreateResponse participantResponse = this.participantService.registerParticipantToTrip(payLoad.email(), tripToUpdate);

                if(tripToUpdate.getIsConfirmed()){
                    this.participantService.triggerConfirmationEmailToParticipant(payLoad.email());
                }

                return ResponseEntity.ok(participantResponse);
            }
            return ResponseEntity.notFound().build();
        }

        @GetMapping("/{id}/participants")
        public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id){
            List<ParticipantData> participantsList = this.participantService.getAllParticipantsFromTrip(id);
            return ResponseEntity.ok(participantsList);
        }

        @PostMapping("/{id}/activities")
        public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id,
                @RequestBody ActivityRequestPayload payLoad) {
            Optional<Trip> trip = this.repository.findById(id);

            if (trip.isPresent()) {
                Trip tripToUpdate = trip.get();

                ActivityResponse activityResponse = this.activityService.registerActivity(payLoad, tripToUpdate);

                return ResponseEntity.ok(activityResponse);
            }
            return ResponseEntity.notFound().build();
        }

        @GetMapping("/{id}/activities")
        public ResponseEntity<List<ActivityData>> getActivities(@PathVariable UUID id) {
            List<ActivityData> activitiesList = this.activityService.getAllActivitiesFromId(id);
            return ResponseEntity.ok(activitiesList);
        }

        @PostMapping("/{id}/links")
        public ResponseEntity<LinkResponse> addLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payLoad) {
            Optional<Trip> trip = this.repository.findById(id);

            if (trip.isPresent()) {
                Trip tripToUpdate = trip.get();

                LinkResponse linkResponse = this.linkService.addLink(payLoad, tripToUpdate);

                return ResponseEntity.ok(linkResponse);
            }
            return ResponseEntity.notFound().build();
        }

        @GetMapping("/{id}/links")
        public ResponseEntity<List<LinkData>> getLinks(@PathVariable UUID id) {
            List<LinkData> linkList = this.linkService.getAllLinksFromId(id);
            return ResponseEntity.ok(linkList);
        }
        
}
