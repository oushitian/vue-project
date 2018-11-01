package com.interest;

import com.interest.utils.HttpClientUtil;
import com.interest.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import com.interest.utils.DateUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class WhSpringBootApplicationTests {

	@Test
	public void contextLoads() {
		String url = "https://testapi.sfbest.com/open-api/open/product/getProductList?app_key=client-test&access_token=e5ce41cc-84f8-4f4f-9a0d-6f32761861a0&timestamp=2016-05-06 19:49:05&format=json&version=1.0";
		String req = "{page:1,pageSize:10,numbe:10001}" ;
		Map<String, String> map = new HashMap<>();
		map.put("page","1");
		map.put("pageSize","10");
		map.put("numbe","10001");
//		String result = HttpClientUtil.doPost(url, map, "utf-8");
		String result = OkHttpUtil.postJsonParams(url,req);
		log.info(result);
	}

}
