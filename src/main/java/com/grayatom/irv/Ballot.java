package com.grayatom.irv;

import java.util.*;

public class Ballot {

    private List<Candidate> candidateRankings = new ArrayList<>();

    public Ballot(Candidate... candidates) {
        candidateRankings.addAll(Arrays.asList(candidates));
    }

    List<Candidate> getCandidateRankings() {
        return candidateRankings;
    }

    Optional<Candidate> getFirstOptionConsideringOnly(Collection<Candidate> remainingCandidates) {
        return candidateRankings.stream().filter(remainingCandidates::contains).findFirst();
    }

}
