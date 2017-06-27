package controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ueditor.ActionEnter;


/**
 * @author chenyifei
 * @date 2017-06-21
 */
@Controller
@RequestMapping("ueditor")
public class EditController {
	
	@RequestMapping("config")
	@ResponseBody
	public Object config(HttpServletRequest request){
		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println(path);
		return new ActionEnter( request, path).exec();
	}
	
	
	@GetMapping("edit")
	public String editView(){
		return "index";
	}
	
	
}
