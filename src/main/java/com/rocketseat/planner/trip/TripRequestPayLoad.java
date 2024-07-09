package com.rocketseat.planner.trip;

import java.util.List;

public record TripRequestPayLoad(String destination, String startsAt, String endsAt, List<String> emailsToInvite , String ownerName, String ownerEmail) {

}
