package com.rocketseat.planner.link;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, UUID>{

    Collection<LinkData> findByTripId(UUID id);

}
