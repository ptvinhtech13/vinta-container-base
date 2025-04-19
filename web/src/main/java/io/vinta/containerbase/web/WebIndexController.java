package io.vinta.containerbase.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebIndexController implements BaseWebController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/settings")
	public String getSettingPage() {
		return "settings";
	}

}