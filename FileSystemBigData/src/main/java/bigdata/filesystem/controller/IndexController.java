package bigdata.filesystem.controller;

import bigdata.filesystem.comn.base.BaseController;
import bigdata.filesystem.config.PassToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {

    @PassToken
    @RequestMapping({"", "/", "/index", "/index.html"})
    public String index() {
        return "index";
    }


    @PassToken
    @RequestMapping({"/js/lay-module/constant/constant.js"})
    public String constant() {
        return "js/lay-module/constant/constant";
    }

//  @RequestMapping({"/error", "/404"})
//  public String error() {
//    return "/pages/error/404";
//  }
}
