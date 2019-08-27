package com.gramman75.demowebmvc;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @SessionAttributes 는 해당 값의 이름과 동일한 변수가 model에
 * 추가가 되면 동시에 Session에도 추가가 됨.
 * 하나의 Controller(Class)에서만 적용이 되고 사용한 후에는
 * SessionStatus.setComplete()를 통해서 삭제한다.
 *
 */
@Controller
@SessionAttributes("event")
public class MultiFormController {
    @GetMapping("/events/form/name")
    public String eventFormName(Model model) {
        model.addAttribute("event", new Event());
        return "/events/form-name";
    }

    @PostMapping("/events/form/name")
    public String eventFormNameSubmit(@Validated(Event.ValidateName.class) @ModelAttribute Event event,
                                      BindingResult bindingResult,
                                      Model model) {
        if (bindingResult.hasErrors()){
            return "/events/form-name";
        }
        model.addAttribute(event);

        return "redirect:/events/form/limit";
    }

    @GetMapping("/events/form/limit")
    public String eventFormLimit(@ModelAttribute Event event,
                                 Model model) {
        model.addAttribute(event);
        return "/events/form-limit";
    }

    @PostMapping("/events/form/limit")
    public String eventFormLimitSubmit(@Validated(Event.ValidateLimit.class) @ModelAttribute Event event,
                                       BindingResult bindingResult,
                                       SessionStatus sessionStatus,
                                       Model model,
                                       RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "/events/form-limit";
        }

        /**
         * model의 값이 primitive type일 경우 redirect를 하면 자동으로 전달이됨.
         * Spring Boot에서는 해당 아래 옵션이 true로 되어 있어서 전달되지 않음.
         * ignoreDefaultModelOnRedirect
         * false로 변경하면 전달이 됨.
         */
//        model.addAttribute("name", event.getName());
//        model.addAttribute("limit", event.getLimit());

        /**
         * default설정을 그대로 두고 명시적으로 전달을 하려면
         * RedirectAttributes의 addAttribute를 이용함(Get방식)
         *
         * 복합객체를 전달하기 위해서는 addFlashAttribute를 이용.
         * Session에 넣어서 전달을 함. 받는쪽에서는 Model이나 ModelAttribute로 전달받음.
         */
//        redirectAttributes.addAttribute("name", event.getName());
//        redirectAttributes.addAttribute("limit", event.getLimit());
        redirectAttributes.addFlashAttribute("name", event.getName());
        redirectAttributes.addFlashAttribute("limit", event.getLimit());
        sessionStatus.setComplete();

        return "redirect:/events/list";
    }

}
