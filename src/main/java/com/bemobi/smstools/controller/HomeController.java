package com.bemobi.smstools.controller;

import com.bemobi.smstools.domain.SMS;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by rodrigo.bacellar on 07/07/2015.
 */
@RestController
@RequestMapping("/home")
public class HomeController
{

    @RequestMapping(value = {"","/"}, method = RequestMethod.GET)
    public ModelAndView getHome()
    {
        ModelAndView view = new ModelAndView("template");
        view.addObject("pageName", "index");
        return view;
    }
}
