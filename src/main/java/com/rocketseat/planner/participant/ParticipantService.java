package com.rocketseat.planner.participant;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.planner.trip.Trip;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository repository;

    public void registerParticipantsToTrip(List<String> participantsToInvite, Trip trip){
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();
        this.repository.saveAll(participants);

        System.out.println(participants.get(0).getId());
        System.out.println(participants.get(0).getEmail());
    }

    public ParticipantCreateResponse registerParticipantToTrip(String email, Trip trip){
        Participant participant = new Participant(email, trip);
        this.repository.save(participant);

        return new ParticipantCreateResponse(participant.getId());
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {
    };

    public void triggerConfirmationEmailToParticipant(String email) {
    }

   public List<ParticipantData> getAllParticipantsFromTrip(UUID id) {
        return this.repository.findByTripId(id).stream()
            .map(participant -> new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed()))
            .collect(Collectors.toList());
    }
}