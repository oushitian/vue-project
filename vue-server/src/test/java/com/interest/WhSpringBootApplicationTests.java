package com.interest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.interest.domain.PageDO;
import com.interest.domain.mall.MallDO;
import com.interest.domain.mall.Product;
import com.interest.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import java.util.*;

@Slf4j
public class WhSpringBootApplicationTests {

	String clientId = "G034E1L8XW";
	String clientSecret = "72b8b1799af8e8c3edd12e51525cefde5627d419";
	String accessToken = "6c568c57-58d9-4d92-9912-f5894ec7f113";
	List<String> numberList;

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
		pageDO.setPageSize("1");
		String result = OkHttpUtil.postJsonParams(url,JSON.toJSONString(pageDO));
		log.info(result);
		numberList = new ArrayList<>();
		JSONObject jsonObject = JSON.parseObject(result);
		JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("productList");
		for (Object obj : jsonArray) {
			JSONObject o = (JSONObject) obj;
			JSONObject o1 = o.getJSONObject("baseInfo");
			numberList.add(o1.getString("number"));
			System.out.println((o1.getString("productId")+","+o1.getString("number")+","+o1.getString("name")+","+o1.get("price")+","+o.get("costPrice")));
		}
	}

	@Test
	//获取商品详情
	public void getGoodsDetail(){
		String url = "https://testapi.sfbest.com/open-api/open/product/queryProductInfoDetails?app_key="+clientId+"&access_token="+accessToken;
		List<String> list = new ArrayList<>();
		list.add("1400001313");
		String result = OkHttpUtil.postJsonParams(url,JSON.toJSONString(list));
		log.info(result);
	}


	@Test
	//运费测试
	public void getFreight(){
		String url = "https://testapi.sfbest.com/open-api/open/shipping/getShipingFee?app_key="+clientId+"&access_token="+accessToken;
		MallDO mallDO = new MallDO();
		mallDO.setUserrank("10");
		List<Product> list = new ArrayList<>();
		Product product = new Product();
		product.setSellPrice(2600.5);
		product.setProductSn("9300201442");
		product.setProductIndex("1500020598");
		product.setWeight(50000.0);
		product.setMerchantNumber("16147");
		product.setRegionId("2");
		product.setCityId("50");
		product.setSfshipping("100");
		product.setProductId("201442");
		list.add(product);
		mallDO.setProductlist(list);
		System.out.println(JSON.toJSONString(mallDO));
		String result = OkHttpUtil.postJsonParams(url,JSON.toJSONString(mallDO));
		log.info(result);
	}
}
