package com.rocketseat.planner.participant;

import java.util.UUID;

public record ParticipantData(UUID tripId, String name, String email, Boolean isConfirmed) {

}
