package com.grayatom.irv;

import java.util.*;

class Round {

    private final int roundNumber;
    private final Set<Candidate> startingCandidates;
    private Map<Candidate, Integer> standings = new HashMap<>();
    private Candidate eliminatedCandidate;

    Round(int roundNumber, Set<Candidate> remainingCandidates) {
        this.roundNumber = roundNumber;
        this.startingCandidates = remainingCandidates;
    }

    Optional<Candidate> count(List<Ballot> ballots) {

        // count votes
        for (Ballot ballot : ballots) {
            ballot.getFirstOptionConsideringOnly(startingCandidates).ifPresent(candidate ->
                    standings.merge(candidate, 1, Integer::sum)
            );
        }

        // check for single majority winner
        Integer mostVotes = Collections.max(standings.values());
        Integer totalVotes = standings.values().stream().mapToInt(Integer::intValue).sum();
        if ((double) mostVotes / totalVotes > 0.5) {
            return standings.keySet().stream()
                    .filter(candidate -> standings.get(candidate).equals(mostVotes))
                    .findFirst();
        }

        // There is no single accepted strategy for dealing with losers tie.
        // The only unarguably fair tie-breaker is to randomly eliminate one of the tied candidates.
        // This rule is always used in a final round when there are only two candidates left.
        Integer leastVotes = Collections.min(standings.values());
        eliminatedCandidate = startingCandidates.stream()
                .filter(candidate -> standings.get(candidate).equals(leastVotes))
                .findAny().get();

        return Optional.empty();
    }

    void print() {
        System.out.printf("Standings after round %d%n", roundNumber);
        standings.entrySet().stream()
                .map(e -> String.format("%s - %d", e.getKey(), e.getValue()))
                .forEach(System.out::println);
    }

    Candidate getEliminatedCandidate() {
        return eliminatedCandidate;
    }
}
