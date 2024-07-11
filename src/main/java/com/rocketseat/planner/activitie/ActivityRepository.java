package com.rocketseat.planner.activitie;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, UUID>{

    List<ActivityData> findByTripId(UUID id);

}
