package mk.ukim.finki.wp.kol2023.g1.service.impl;

import mk.ukim.finki.wp.kol2023.g1.model.Player;
import mk.ukim.finki.wp.kol2023.g1.model.PlayerPosition;
import mk.ukim.finki.wp.kol2023.g1.model.Team;
import mk.ukim.finki.wp.kol2023.g1.model.exceptions.InvalidPlayerIdException;
import mk.ukim.finki.wp.kol2023.g1.model.exceptions.InvalidTeamIdException;
import mk.ukim.finki.wp.kol2023.g1.repository.PlayerRepository;
import mk.ukim.finki.wp.kol2023.g1.repository.TeamRepository;
import mk.ukim.finki.wp.kol2023.g1.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }


    @Override
    public List<Player> listAllPlayers() {
        return this.playerRepository.findAll();
    }

    @Override
    public Player findById(Long id) {
        return this.playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
    }

    @Override
    public Player create(String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Team team1 = this.teamRepository.findById(team).orElseThrow(InvalidTeamIdException::new);

        return this.playerRepository.save(new Player(name, bio, pointsPerGame, position, team1));
    }

    @Override
    public Player update(Long id, String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Team team1 = this.teamRepository.findById(team).orElseThrow(InvalidTeamIdException::new);

        Player player1 = this.findById(id);

        player1.setName(name);
        player1.setBio(bio);
        player1.setPointsPerGame(pointsPerGame);
        player1.setPosition(position);
        player1.setTeam(team1);

        return this.playerRepository.save(player1);
    }

    @Override
    public Player delete(Long id) {
        Player player = this.findById(id);
        this.playerRepository.delete(player);

        return null;
    }

    @Override
    public Player vote(Long id) {
        Player player = this.findById(id);
        Integer votes = player.getVotes();
        player.setVotes(++votes);

        return this.playerRepository.save(player);
    }

    @Override
    public List<Player> listPlayersWithPointsLessThanAndPosition(Double pointsPerGame, PlayerPosition position) {

        if(pointsPerGame != null && position != null) {
            return this.playerRepository.findPlayersByPointsPerGameLessThanAndPosition(pointsPerGame, position);
        }

        if(pointsPerGame != null) {
            return this.playerRepository.findPlayersByPointsPerGameLessThan(pointsPerGame);
        }

        if(position != null) {
            return this.playerRepository.findByPosition(position);
        }

        return this.playerRepository.findAll();

    }
}