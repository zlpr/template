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

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player create(Player player) {

        if (player.getName() == null)throw new BadRequestException("400");
        if (player.getTitle() == null)throw new BadRequestException("400");
        if (player.getRace() == null)throw new BadRequestException("400");
        if (player.getProfession() == null)throw new BadRequestException("400");
        if (player.getBirthday() == null)throw new BadRequestException("400");
        if (player.getBanned() == null)
            player.setBanned(false);
        if (player.getExperience() == null)throw new BadRequestException("400");

                invalidPlayer(player);
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

    public Player updatePlayer(Player player, Long id) {

        if (id == 0) {
            throw new BadRequestException("400");
        }

        Player playerUp =  playerRepository.findById(id)
                                           .orElseThrow(() -> new NotFoundException("404"));

        if (player.getName() != null)
            playerUp.setName(player.getName());
        if (player.getTitle() != null)
            playerUp.setTitle(player.getTitle());
        if (player.getRace() != null)
            playerUp.setRace(player.getRace());
        if (player.getProfession() != null)
            playerUp.setProfession(player.getProfession());
        if (player.getBirthday() != null)
            playerUp.setBirthday(player.getBirthday());
        if (player.getBanned() != null)
            playerUp.setBanned(player.getBanned());
        if (player.getExperience() != null)
            playerUp.setExperience(player.getExperience());

        playerUp.setLevel(currentLevl(playerUp.getExperience()));
        playerUp.setUntilNextLevel(untilNextLevel(playerUp.getLevel(),playerUp.getExperience()));
        invalidPlayer(playerUp);
        playerRepository.save(playerUp);

        return playerUp;
    }
    private boolean invalidPlayer(Player player){

        if (player.getName().length() > 12 || player.getName().isEmpty())
            throw new BadRequestException("Name > 12");
        else if (player.getTitle().length() > 30)
            throw new BadRequestException("Title > 30");
        else if (player.getExperience() < 0 || player.getExperience() > 10000000)
            throw new BadRequestException("Experience not [0-10000000]");

             //if (player.getBirthday().getTime() < 60904915200000L||
           // player.getBirthday().getTime() > 92461910400000L)
           // throw new BadRequestException("Birthday not [2000-3000]");
        if (player.getBirthday().getTime() < 0L) throw new BadRequestException("Birthday < 0");
        return true;


    }


    private Integer currentLevl(Integer exp){

        return (int)(Math.sqrt(2500 + (200 * exp)) - 50) / 100;

    }
    private Integer untilNextLevel(Integer lvl, Integer exp){

        return  50 * (lvl + 1) * (lvl + 2) - exp;
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
