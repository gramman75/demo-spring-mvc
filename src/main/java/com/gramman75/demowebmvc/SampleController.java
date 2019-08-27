package com.gramman75.demowebmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;

@Controller
@RequestMapping(method = RequestMethod.GET)
public class SampleController {

    /**
     * URL Mapping
     * @param name
     * @return
     */
    @RequestMapping("/hello/{name:[a-z]+}")
    @ResponseBody
    public String hello(@PathVariable String name) {
        return "hello " + name;
    }

    /**
     * Media Type Mapping
     * cunsumes/produces는 class에 선언이 되어 있어도 Method에 설정된 값으로
     * Overwrite됨.
     * @return
     */
    @RequestMapping(
            value = "/helloJson",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ResponseBody
    public String helloJson() {
        return "hello json";
    }


    /**
     * Request Header Mapping
     * @return
     */
    @RequestMapping(
            value = "/helloHeader"
//           , headers = "!" + HttpHeaders.FROM // From이 없는 경우.
           , headers = HttpHeaders.FROM  +"="+"localhost"// From이 특정값과 일치하는 경우.
    )
    @ResponseBody
    public String helloHeader() {
        return "hello header";
    }

    /**
     * Request param mapping
     * @return
     */
    @RequestMapping(
            value =  "/helloParam",
            params = "name=gramman75"
    )
    @ResponseBody
    public String helloParam() {
        return "hello param";
    }

    @GetHelloMapping
    public String helloAnno() {
        return "hello anno";
    }
}
