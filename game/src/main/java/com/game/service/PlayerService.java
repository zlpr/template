package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception.BadRequestException;
import com.game.exception.NotFoundException;
import com.game.repository.PlayerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player create(Player player) {

        return playerRepository.save(player);
    }

    public void   deletePlayer(Long id) {
        if (id == 0) {
            throw new BadRequestException("400");
        }

        Player player =  playerRepository.findById(id)
                                         .orElseThrow(() -> new NotFoundException("404"));
        playerRepository.delete(player);




    }

    public void updatePlayer(Player player) {
    }

    public Player getPlayer(Long id) {
        if (id == 0) {
            throw new BadRequestException("400");
        }
        Player player =  playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("404"));

        return player;
    }

    public List<Player> getAll(String name, String title, Race race, Profession profession, Long after, Long before,
                               Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                               Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSize) {

        if (pageSize == null) pageSize = 3;
        if (pageNumber == null) pageNumber = 0;
        if (order == null) order = PlayerOrder.ID;

        return playerRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()))).stream()
                    .filter(player -> name == null || player.getName().contains(name))
                    .filter(player -> title == null || player.getTitle().contains(title))
                    .filter(player -> race == null || player.getRace().equals(race))
                    .filter(player -> profession == null || player.getProfession().equals(profession))
                    .filter(player -> after == null || player.getBirthday().getTime() > after )
                    .filter(player -> before == null || player.getBirthday().getTime() < before)
                    .filter(player -> banned == null || player.getBanned().equals(banned))
                    .filter(player -> minExperience == null || player.getExperience() >= minExperience)
                    .filter(player -> maxExperience == null || player.getExperience() <= maxExperience)
                    .filter(player -> minLevel == null || player.getLevel() >= minLevel)
                    .filter(player -> maxLevel == null || player.getLevel() <= maxLevel)
                    .collect(Collectors.toList())
                    ;
    }
}
