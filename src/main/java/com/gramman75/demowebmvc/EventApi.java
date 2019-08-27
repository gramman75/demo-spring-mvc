package com.gramman75.demowebmvc;


import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventApi {

    @PostMapping
//    public Event createEvent(@RequestBody Event event){
//        return event;
//    }
    public Event createEvent(HttpEntity<Event> request){
        MediaType mediaType =  request.getHeaders().getContentType();
        System.out.println(mediaType);
        return request.getBody();

    }

}
