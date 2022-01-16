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
    public void updatePlayer(@RequestBody Player player,@PathVariable Long id){
        player.setId(id);
        playerService.updatePlayer(player);
    }
    @GetMapping("/rest/players/count")
    public Integer getCount(@RequestParam String name,
                            @RequestParam String title,
                            @RequestParam Race race,
                            @RequestParam Profession profession,
                            @RequestParam Long after,
                            @RequestParam Long before,
                            @RequestParam Boolean banned,
                            @RequestParam Integer minExperience,
                            @RequestParam Integer maxExperience,
                            @RequestParam Integer minLevel,
                            @RequestParam Integer maxLevel
                            ){

        return playerService.getCount(name,
                                      title,
                                      race,
                                      profession,
                                      after,
                                      before,
                                      banned,
                                      minExperience,
                                      maxExperience,
                                      minLevel,
                                      maxLevel);



    }
    @GetMapping("/rest/players")
    public List<Player> getAll(@RequestParam String name,
                         @RequestParam String title,
                         @RequestParam Race race,
                         @RequestParam Profession profession,
                         @RequestParam Long after,
                         @RequestParam Long before,
                         @RequestParam Boolean banned,
                         @RequestParam Integer minExperience,
                         @RequestParam Integer maxExperience,
                         @RequestParam Integer minLevel,
                         @RequestParam Integer maxLevel,
                         @RequestParam PlayerOrder order,
                         @RequestParam Integer pageNumber,
                         @RequestParam Integer pageSize
                            ){
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
