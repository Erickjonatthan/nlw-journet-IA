package com.rocketseat.planner.participant;

import java.util.UUID;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

    public void registerParticipantsToTrip(List<String> participantsToInvite, UUID tripId){
        
    };

    public void triggerConfirmationEmailToParticipants(UUID tripId) {
    };
}
