package com.gramman75.demowebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("event") // value와 동일한 이름의 model이 세션에 자동 저장됨.
public class HandlerMethodController {

    /**
     * RequestParam 예제
     * @param name
     * @param limit
     * @return
     */
    @PostMapping("/events")
    @ResponseBody
    public Event getEvents(@RequestParam String name,
                            @RequestParam Integer limit,
                           Model model) {
        Event event = new Event();
        event.setName(name);
        event.setLimit(limit);
        model.addAttribute(event);

        return event;

    }

    /**
     * Model사용 예제
     * @param model
     * @return
     */
    @GetMapping("/events/form")
    public String getEventsForm(Model model) {
        Event event = new Event();
        model.addAttribute("event", event);
        return "/events/form";

    }

    /**
     * ㅇ @ModelAttribute는 Parameter key가 Model(Composite 객첵)의 property에 자동 mapping이 되어
     * 객체를 Handler에서 사용할 수 있도록 함.
     * ㅇ @Valid는 해당 Model의 java validate Annotation에 대해서 검증하고 그 결과를
     *    @BindResult로 전달해줌.
     * ㅇ BindingResult는 Binding시 에러 발생(Type등)시 해당 오류를 bindingResult에 전달하고
     * 정상 처리가 되도록 함.
     * @param event
     * @return
     */
    @PostMapping("/eventsModelAttr")
    @ResponseBody
    public Event getEventModelAttr(@Validated({Event.ValidateLimit.class, Event.ValidateName.class}) @ModelAttribute Event event, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(c->{
                System.out.println(c.toString());
            });
        }
        return event;
    }

    @PostMapping("/eventsModelView")
    public String getEventModelView(@Validated({Event.ValidateName.class, Event.ValidateLimit.class}) @ModelAttribute Event event,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()){
            return "/events/form";
        }
        // DB Save
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

        model.addAttribute(eventList);

        // post로 전달 받은 후 refresh를 하면 양식 다시 제출 확인을 피하기 위해서
        // redirect로 get 방식으로 전달 한 후에 목록을 보여주도록 함.
        return "redirect:/events/list";
    }


   @GetMapping("/events/list")
   public String getEventList(Model model,
                              @SessionAttribute LocalDateTime visitTime){
        Event event = new Event();
        event.setName("gramman75");
        event.setLimit(10);
        // DB Selecxt
        List<Event> eventList = new ArrayList<>();
        eventList.add(event);

//        Event newEvent = new Event();
//        newEvent.setName(name);
//        newEvent.setLimit(limit);
//        eventList.add(newEvent);
       Event newEvent = new Event();
       newEvent.setName((String)model.asMap().get("name"));
       newEvent.setLimit((Integer)model.asMap().get("limit"));
       eventList.add(newEvent);

        model.addAttribute(eventList);
        System.out.println("visitTime : " + visitTime);



        return "/events/list";
   }
}
