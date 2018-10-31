package com.interest.controller.template;

import com.interest.page.PageResult;
import com.interest.model.ReplyCardEntity;
import com.interest.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interest.service.ReplyCardService;

@RestController
public class ReplyCardController {

	@Autowired
	private ReplyCardService replyCardService;

	@GetMapping("/public/replycards")
	public PageResult replycardList(@RequestParam("postcardid") int postcardid) {
		return PageUtil.getPageInfo(replyCardService.replycardList(postcardid));
	}

	@PostMapping("/replycards/replycard")
	public ReplyCardEntity insertEntity(@RequestBody ReplyCardEntity replyCardEntity ) {
		replyCardService.insertEntity(replyCardEntity);
		return replyCardEntity;
	}

}
