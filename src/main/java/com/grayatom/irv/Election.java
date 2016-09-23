package com.grayatom.irv;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Election {

    private List<Candidate> candidates = new ArrayList<>();
    private List<Ballot> ballots = new ArrayList<>();

    public void registerCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void submitBallot(Ballot ballot) {
        ballots.add(ballot);
    }

    public Optional<Candidate> calculateWinner() {
        if (ballots.isEmpty()) {
            return Optional.empty();
        }
        Set<Candidate> remainingCandidates = ballots.stream()
                .map(Ballot::getCandidateRankings)
                .flatMap(List::stream)
                .collect(Collectors.toSet());

        int roundNumber = -1;
        while (remainingCandidates.size() > 1) {
            Round round = new Round(++roundNumber, remainingCandidates);
            Optional<Candidate> winnerOptional = round.count(ballots);
            if (winnerOptional.isPresent()) {
                return winnerOptional;
            }
            remainingCandidates.remove(round.getEliminatedCandidate());
        }

        return remainingCandidates.stream().findFirst();
    }

}
