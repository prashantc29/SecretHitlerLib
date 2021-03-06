package com.pacific.secrethitler.game;

import com.google.common.collect.Lists;

import com.pacific.secrethitler.player.Player;
import com.pacific.secrethitler.types.Policy;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Game State object used during the game. The class contains the whole context
 * of the game. <p> Following information is maintained by {@link GameState}:
 * <ul> <li>The deck of policies.</li> <li>The liberal and the fascist
 * track.</li> <li>The number of players.</li> </ul>
 *
 * @author prashantchaudhary
 */
public class GameState {

    private final DrawPolicyDeck drawPolicyDeck;
    private final PolicyTrack liberalPolicyTrack;
    private final PolicyTrack fascistPolicyTrack;
    private final Map<String, Player> players;
    private final Queue<Player> presidentQueue;
    private final int numOfPlayers;

    private Government currentGovernment;
    private Government lastGovernment;

    private GameState(final List<Policy> initialShuffledPolicies, final
    List<Player> initialPlayers, final String firstPresident) {
        drawPolicyDeck = QueueDrawPolicyDeck.newDrawPolicyDeck
                (initialShuffledPolicies);
        liberalPolicyTrack = PolicyTrack.newPolicyTrack();
        fascistPolicyTrack = PolicyTrack.newPolicyTrack();
        players = initialPlayers.stream().collect(Collectors.toMap
                (Player::getPlayerId, Function.identity()));
        presidentQueue = createInitialPresidentQueue(initialPlayers,
                players.get(firstPresident));
        numOfPlayers = initialPlayers.size();

    }

    public static GameState newGameState(final List<Policy>
                                                 initialShuffledPolicies,
                                         final List<Player> initialPlayers,
                                         final String firstPresident) {
        return new GameState(initialShuffledPolicies, initialPlayers,
                firstPresident);
    }

    public Policy drawPolicy() {
        return drawPolicyDeck.drawPolicy();
    }

    public void putDiscardedPolicy(final Policy policy) {
        drawPolicyDeck.putDiscardedPolicy(policy);
    }

    public void enactPolicy(final Policy policy) {
        if (Policy.LIBERAL.equals(policy)) {
            liberalPolicyTrack.enactPolicy();
        } else {
            fascistPolicyTrack.enactPolicy();
        }
    }

    public void setCurrentGovernment(final Government government) {
        this.currentGovernment = government;
    }

    public Government getCurrentGovernment() {
        return currentGovernment;
    }

    public void setLastGovernment(final Government government) {
        this.lastGovernment = government;
    }

    public Government getLastGovernment() {
        return lastGovernment;
    }

    public Player getNextPresident() {
        final Player nextPresident = presidentQueue.poll();
        presidentQueue.add(nextPresident);
        return nextPresident;
    }

    public List<Player> getPlayers() {
        return Lists.newArrayList(players.values());
    }

    private Queue<Player> createInitialPresidentQueue(List<Player>
                                                              initialPlayers,
                                                      Player firstPresident) {
        final Queue<Player> queue = new LinkedList<>();
        boolean foundFirstPresident = false;
        for (final Player player : initialPlayers) {
            if (player.equals(firstPresident)) {
                queue.add(player);
                foundFirstPresident = true;
                continue;
            }
            if (foundFirstPresident) {
                queue.add(player);
            }
        }
        for (final Player player : initialPlayers) {
            if (player.equals(firstPresident)) {
                break;
            }
            queue.add(player);
        }
        return queue;
    }

    @Override
    public String toString() {
        return "GameState{" + "drawPolicyDeck=" + drawPolicyDeck + ", " +
                "liberalPolicyTrack=" + liberalPolicyTrack + ", " +
                "fascistPolicyTrack=" + fascistPolicyTrack + ", " +
                "currentGovernment=" + currentGovernment + ", " +
                "lastGovernment=" + lastGovernment + ',' +
                "presidentQueue=" + presidentQueue;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public int getNumberOfEnactedFascistPolicies() {
        return fascistPolicyTrack.getNumberOfPoliciesEnacted();
    }

    public int getNumberOfEnactedLiberalPolicies() {
        return liberalPolicyTrack.getNumberOfPoliciesEnacted();
    }
}
