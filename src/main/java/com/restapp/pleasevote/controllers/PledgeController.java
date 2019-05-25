package com.restapp.pleasevote.controllers;

import com.restapp.pleasevote.model.Pledge;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

@RestController
public class PledgeController {
    private List<Pledge> pledgeList = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong();

    @GetMapping("/hello")
    public String getHelloMessage() {
        return "Hello Spring Boot";
    }

    @PostMapping("/pledges")
    public Pledge createNewPledge(@RequestBody Pledge pledge) {
        pledge.setId(nextId.incrementAndGet());
        pledgeList.add(pledge);
        return pledge;
    }

    @GetMapping("/pledges")
    public List<Pledge> getAllPledges() {
        return this.pledgeList;
    }

    @GetMapping("/pledges/{id}")
    public Pledge getPledgeById(@PathVariable("id") long pledgeId) {
        for(Pledge pledge: pledgeList) {
            if (pledge.getId() == pledgeId) { return pledge; };
        }
        throw new IllegalArgumentException();
    }

    @PostMapping("updatePledge/{id}")
    public Pledge updatePledgeById(
            @PathVariable("id") long pledgeId,
            @RequestBody Pledge newPledge
    ) {
        for(Pledge pledge: pledgeList) {
            if (pledge.getId() == pledgeId) {
                pledgeList.remove(pledge);
                newPledge.setId(pledgeId);
                pledgeList.add(newPledge);
                return newPledge;
            }
        }

        return null;
    }
}
