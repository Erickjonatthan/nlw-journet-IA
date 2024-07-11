package com.rocketseat.planner.link;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.planner.trip.Trip;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public LinkResponse addLink(LinkRequestPayload payLoad, Trip tripToUpdate) {
        Link link = new Link(payLoad.title(), payLoad.url(), tripToUpdate);
        this.linkRepository.save(link);
        return new LinkResponse(link.getId());
    }

    public List<LinkData> getAllLinksFromId(UUID id) {
        return this.linkRepository.findByTripId(id).stream()
            .map(link -> new LinkData(link.id(), link.title(), link.url()))
            .toList();
    }

}
