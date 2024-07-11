package com.rocketseat.planner.activitie;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.planner.trip.Trip;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip) {
        LocalDateTime occursAt = LocalDateTime.parse(payload.occursAt(), DateTimeFormatter.ISO_DATE_TIME);
        Activity newActivity = new Activity(payload.title(), occursAt, trip);

        this.repository.save(newActivity);

        return new ActivityResponse(newActivity.getId());
    }

    public List<ActivityData> getAllActivitiesFromId(UUID id) {
        return this.repository.findByTripId(id).stream()
            .map(activity -> new ActivityData(activity.id(), activity.title(), activity.occursAt()))
            .toList();
    }
}
