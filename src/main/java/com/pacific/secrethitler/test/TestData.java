package com.pacific.secrethitler.test;

import com.google.common.collect.ImmutableList;

import com.pacific.secrethitler.game.shuffle.CollectionsShuffler;
import com.pacific.secrethitler.game.shuffle.Shuffler;
import com.pacific.secrethitler.player.Player;
import com.pacific.secrethitler.types.Policy;
import com.pacific.secrethitler.types.Role;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class has methods for generating test data.
 */
public class TestData {

    private final Player firstPresident = Player.newPlayer("playerLiberal1",
            Role.LIBERAL);

    private final int numOfPlayers;
    private final Shuffler shuffler;

    private TestData(final int numOfPlayers, final Shuffler shuffler) {
        this.numOfPlayers = numOfPlayers;
        this.shuffler = shuffler;
    }

    public static TestData newTestData(final int numOfPlayers, final Shuffler
            shuffler) {
        return new TestData(numOfPlayers, shuffler);
    }

    public List<Policy> getInitialPolicies() {
        final ImmutableList.Builder<Policy> policies = ImmutableList.builder();
        policies.addAll(IntStream.range(0, 11).mapToObj(i -> Policy.FASCIST)
                .collect(Collectors.toList()));
        policies.addAll(IntStream.range(0, 6).mapToObj(i -> Policy.LIBERAL)
                .collect(Collectors.toList()));
        return shuffler.shuffle(policies.build());
    }

    public List<Player> getInitialPlayers() {
        final ImmutableList.Builder<Player> players = ImmutableList.builder();
        players.addAll(IntStream.range(0, 3).mapToObj(i -> Player.newPlayer
                ("playerLiberal" + i, Role.LIBERAL)).collect(Collectors
                .toList()));
        players.addAll(IntStream.range(0, 1).mapToObj(i -> Player.newPlayer
                ("playerFascist" + i, Role.FASCIST)).collect(Collectors
                .toList()));
        players.addAll(IntStream.range(0, 1).mapToObj(i -> Player.newPlayer
                ("playerHitler" + i, Role.HITLER)).collect(Collectors.toList
                ()));
        return shuffler.shuffle(players.build());
    }

    public Player getFirstPresident() {
        return firstPresident;
    }
}
