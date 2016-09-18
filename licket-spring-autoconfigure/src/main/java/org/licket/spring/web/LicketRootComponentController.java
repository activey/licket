package org.licket.spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.SessionScope;

/**
 * @author activey
 */
@Controller
@RequestMapping("/index.html")
@SessionScope
public class LicketRootComponentController {



}
