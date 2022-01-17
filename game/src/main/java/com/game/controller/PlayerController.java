package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PlayerController {

    private final PlayerService playerService;


    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/rest/players")
    public Player createPlayer(@RequestBody Player player) {

        return playerService.create(player);
    }

    @GetMapping("/rest/players/{id}")
    public Player getPlayer(@PathVariable Long id){
        return playerService.getPlayer(id);
    }

    @PostMapping("/rest/players/{id}")
    public Player updatePlayer(@RequestBody Player player,@PathVariable Long id){
        player.setId(id);
      return   playerService.updatePlayer(player, id);
    }
    @GetMapping("/rest/players/count")
    public Integer getCount(@RequestParam(required = false) String name,
                            @RequestParam(required = false) String title,
                            @RequestParam(required = false) Race race,
                            @RequestParam(required = false) Profession profession,
                            @RequestParam(required = false) Long after,
                            @RequestParam(required = false) Long before,
                            @RequestParam(required = false) Boolean banned,
                            @RequestParam(required = false) Integer minExperience,
                            @RequestParam(required = false) Integer maxExperience,
                            @RequestParam(required = false) Integer minLevel,
                            @RequestParam(required = false) Integer maxLevel) {
        return playerService.getAllCount(name, title, race, profession, after, before, banned,  minExperience,
                maxExperience, minLevel, maxLevel);

    }



    @GetMapping("/rest/players")
    public List<Player> getAll(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String title,
                         @RequestParam(required = false) Race race,
                         @RequestParam(required = false) Profession profession,
                         @RequestParam(required = false) Long after,
                         @RequestParam(required = false) Long before,
                         @RequestParam(required = false) Boolean banned,
                         @RequestParam(required = false) Integer minExperience,
                         @RequestParam(required = false) Integer maxExperience,
                         @RequestParam(required = false) Integer minLevel,
                         @RequestParam(required = false) Integer maxLevel,
                         @RequestParam(required = false) PlayerOrder order,
                         @RequestParam(required = false) Integer pageNumber,
                         @RequestParam(required = false) Integer pageSize ) {
      return playerService.getAll(name,
                             title,
                             race,
                             profession,
                             after,
                             before,
                             banned,
                             minExperience,
                             maxExperience,
                             minLevel,
                             maxLevel,
                             order,
                             pageNumber,
                             pageSize);



    }






    @DeleteMapping("/rest/players/{id}")
    public void deletePlayer(@PathVariable Long id) {

        playerService.deletePlayer(id);

    }


}
