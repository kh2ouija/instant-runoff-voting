package com.grayatom.irv;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * https://en.wikipedia.org/wiki/Instant-runoff_voting#Examples
 */
public class ElectionTest {

    @Test
    public void testThreeCandidatesFiveVoters() throws Exception {
        Candidate BOB = new Candidate<>("Bob");
        Candidate SUE = new Candidate<>("Sue");
        Candidate BILL = new Candidate<>("Bill");

        Election election = new Election();

        election.registerCandidate(BOB);
        election.registerCandidate(SUE);
        election.registerCandidate(BILL);

        election.submitBallot(new Ballot(BOB, BILL, SUE));
        election.submitBallot(new Ballot(SUE, BOB, BILL));
        election.submitBallot(new Ballot(BILL, SUE, BOB));
        election.submitBallot(new Ballot(BOB, BILL, SUE));
        election.submitBallot(new Ballot(SUE, BOB, BILL));

        Optional<Candidate> winner = election.calculateWinner();
        assertTrue(winner.isPresent());
        assertEquals(SUE, winner.get());
    }

    @Test
    public void testTennesseeCapitalElection() throws Exception {
        Candidate MEMPHIS = new Candidate("Memphis");
        Candidate NASHVILLE = new Candidate("Nashville");
        Candidate CHATTANOOGA = new Candidate("Chattanooga");
        Candidate KNOXVILLE = new Candidate("Knoxville");

        Election election = new Election();
        election.registerCandidate(MEMPHIS);
        election.registerCandidate(NASHVILLE);
        election.registerCandidate(CHATTANOOGA);
        election.registerCandidate(KNOXVILLE);

        IntStream.rangeClosed(1, 42).forEach(i -> election.submitBallot(new Ballot(MEMPHIS, NASHVILLE, CHATTANOOGA, KNOXVILLE)));
        IntStream.rangeClosed(1, 26).forEach(i -> election.submitBallot(new Ballot(NASHVILLE, CHATTANOOGA, KNOXVILLE, MEMPHIS)));
        IntStream.rangeClosed(1, 15).forEach(i -> election.submitBallot(new Ballot(CHATTANOOGA, KNOXVILLE, NASHVILLE, MEMPHIS)));
        IntStream.rangeClosed(1, 17).forEach(i -> election.submitBallot(new Ballot(KNOXVILLE, CHATTANOOGA, NASHVILLE, MEMPHIS)));

        Optional<Candidate> winner = election.calculateWinner();
        assertTrue(winner.isPresent());
        assertEquals(KNOXVILLE, winner.get());
    }

}