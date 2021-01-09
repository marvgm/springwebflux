package com.apirest.webflux.controller;

import com.apirest.webflux.document.PlayList;
import com.apirest.webflux.services.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*")
@RestController
public class PlayListController {

    @Autowired
    PlayListService service;

    @GetMapping(value="/playlist")
    public Flux<PlayList> getPlaylist(){
        return service.findAll();
    }


    @GetMapping(value="/playlist/{id}")
    public Mono<PlayList> getPlaylistId(@PathVariable String id){
        return service.findById(id);
    }

    @PostMapping(value="/playlist")
    public Mono<PlayList> save(@RequestBody PlayList playlist){
        return service.save(playlist);
    }

    @GetMapping(value="/playlist/webflux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, PlayList>> getPlaylistByWebflux(){

        System.out.println("---Start get Playlists by WEBFLUX--- " + LocalDateTime.now());
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
        Flux<PlayList> playlistFlux = service.findAll();

        return Flux.zip(interval, playlistFlux);

    }

    @GetMapping(value="/playlist/mvc")
    public List<String> getPlaylistByMvc() throws InterruptedException {

        System.out.println("---Start get Playlists by MVC--- " + LocalDateTime.now());


        List<String> playlistList = new ArrayList<>();
        playlistList.add("Java 8");
        playlistList.add("Spring Security");
        playlistList.add("Github");
        playlistList.add("Deploy de uma aplicação java no IBM Cloud");
        playlistList.add("Bean no Spring Framework");
        TimeUnit.SECONDS.sleep(15);

        return playlistList;

    }

}