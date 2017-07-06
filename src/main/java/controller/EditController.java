package controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ueditor.ActionEnter;
import ueditor.define.UploadType;


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
		String rootPath = request.getServletContext().getRealPath("/");
		System.out.println(rootPath);
		return new ActionEnter(request, rootPath, UploadType.ALIYUN_OSS).exec();
	}
	
	
	@GetMapping("edit")
	public String editView(){
		return "index";
	}
	
	
}
