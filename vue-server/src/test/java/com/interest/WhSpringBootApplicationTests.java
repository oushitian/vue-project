package com.interest;

import com.alibaba.fastjson.JSON;
import com.interest.domain.PageDO;
import com.interest.utils.HttpClientUtil;
import com.interest.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;

import com.interest.utils.DateUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
public class WhSpringBootApplicationTests {

	String clientId = "G034E1L8XW";
	String clientSecret = "72b8b1799af8e8c3edd12e51525cefde5627d419";
	String accessToken = "6c568c57-58d9-4d92-9912-f5894ec7f113";

	@Test
	//获取access_token
	public void getAccessToken(){
		String url = " https://testapi.sfbest.com/open-api/oauth/token?grant_type=client_credentials&client_id="+clientId+"&client_secret="+clientSecret;
		String result = OkHttpUtil.postJsonParams(url,"");
		log.info("access_token="+result);
	}

	@Test
	//获取商品信息
	public void getGoods() {
		String url = "https://testapi.sfbest.com/open-api/open/product/getProductList?app_key="+clientId+"&access_token="+accessToken;
		log.info("url="+url);
		PageDO pageDO = new PageDO();
		pageDO.setPage("1");
		pageDO.setPageSize("2");
		System.out.println(JSON.toJSONString(pageDO));
		String result = OkHttpUtil.postJsonParams(url,JSON.toJSONString(pageDO));
		log.info(result);
	}

	@Test
	//获取商品详情
	public void getGoodsDetail(){
		String url = "https://testapi.sfbest.com/open-api/open/product/queryProductInfoDetails?app_key="+clientId+"&access_token="+accessToken;
		List<String> list = new ArrayList<>();
		list.add("1400001313");
		list.add("1400001312");
		String result = OkHttpUtil.postJsonParams(url,JSON.toJSONString(list));
		log.info(result);
	}


}
