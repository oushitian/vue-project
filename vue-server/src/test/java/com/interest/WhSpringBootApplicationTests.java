package com.interest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.interest.dao.UserDao;
import com.interest.domain.PageDO;
import com.interest.domain.mall.MallDO;
import com.interest.domain.mall.Product;
import com.interest.domain.stock.StockDO;
import com.interest.domain.stock.StockProduct;
import com.interest.model.sf.SfCategory;
import com.interest.dao.sf.SfCategoryMapper;
import com.interest.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class WhSpringBootApplicationTests {

	@Autowired
	SfCategoryMapper sfCategoryMapper;

	String clientId = "G034E1L8XW";
	String clientSecret = "72b8b1799af8e8c3edd12e51525cefde5627d419";
	String accessToken = "59acbdd8-6b5e-4ef6-b7fb-1b1b9a11d748";

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
		PageDO pageDO = new PageDO();
		pageDO.setPage("1");
		pageDO.setPageSize("1");
		String result = OkHttpUtil.postJsonParams(url,JSON.toJSONString(pageDO));
		log.info(result);
		JSONObject jsonObject = JSON.parseObject(result);
		Integer totalCount = jsonObject.getJSONObject("data").getInteger("totalCount");
		Integer count = totalCount/10+1;
		for (int i = 0 ; i < count ; i ++) {
			PageDO page = new PageDO();
			page.setPage("1");
			page.setPageSize("10");
			String results = OkHttpUtil.postJsonParams(url,JSON.toJSONString(pageDO));
			JSONObject jsonObjects = JSON.parseObject(results);
			JSONArray jsonArray = jsonObjects.getJSONObject("data").getJSONArray("productList");
			for (Object obj : jsonArray) {
				JSONObject o = (JSONObject) obj;
				JSONObject o1 = o.getJSONObject("baseInfo");
				JSONObject p = o.getJSONObject("picture");
				JSONObject c1 = o.getJSONObject("categoryOne");
				JSONObject c2 = o.getJSONObject("categoryTwo");
				JSONObject c3 = o.getJSONObject("categoryThree");
				JSONObject b = o.getJSONObject("brand");
				JSONObject f = o.getJSONObject("finance");
				JSONObject sf = o.getJSONObject("sfshipping");
				System.out.println((o1.getString("productId")+","+o1.getString("number")+","+o1.getString("name")+","+o1.get("price")+","+o.get("costPrice")));
				//插入商品
				//根据商品编号插入详情
			}
		}
	}

	@Test
	//获取商品详情
	public void getGoodsDetail(){
		String url = "https://testapi.sfbest.com/open-api/open/product/queryProductInfoDetails?app_key="+clientId+"&access_token="+accessToken;
		List<String> list = new ArrayList<>();
//		list.add(goodsNumber);
		String result = OkHttpUtil.postJsonParams(url,JSON.toJSONString(list));
//		log.info(result);
		JSONObject jsonObjects = JSON.parseObject(result);
		JSONArray jsonArray = jsonObjects.getJSONObject("data").getJSONArray("productInfoDetails");
		for (Object obj : jsonArray) {
			JSONObject o = (JSONObject) obj;
//			SfGoodsDetail sfGoodsDetail = JSON.parseObject(o.toJSONString(),SfGoodsDetail.class);
//			SfCategory hasExist = sfCategoryMapper.selectByPrimaryKey(sfGoodsDetail.getCategoryId());

		}
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

	@Test
	//插入商品品类
	public void getCategory(){
		String url = "https://testapi.sfbest.com/open-api/open/product/queryCategoryList?app_key="+clientId+"&access_token="+accessToken;
		String result = OkHttpUtil.postJsonParams(url,"");
//		log.info(result);
		JSONObject jsonObject = JSON.parseObject(result);
		JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("categoryList");
		for (Object obj : jsonArray) {
			JSONObject o = (JSONObject) obj;
			SfCategory sfCategory = JSON.parseObject(o.toJSONString(),SfCategory.class);
			SfCategory hasExist = sfCategoryMapper.selectByPrimaryKey(sfCategory.getCategoryId());
			if (hasExist != null) {
				continue;
			}
			sfCategoryMapper.insertSelective(sfCategory);
		}
	}

	@Test
	//获取库存接口
	public void getStock(){
		String url = "https://testapi.sfbest.com/open-api/open/product/queryCategoryList?app_key="+clientId+"&access_token="+accessToken;
		StockDO stockDO = new StockDO();
		stockDO.setInvokeSource(0);
		List<StockProduct> stockProductList = new ArrayList<>();
		StockProduct stockProduct = new StockProduct();
		stockProduct.setProductId(2022);
		stockProduct.setProductNum(1);
		stockProductList.add(stockProduct);
		stockDO.setStockInfoRegionParamList(stockProductList);
		String result = OkHttpUtil.postJsonParams(url,JSON.toJSONString(stockDO));
		log.info(result);
	}
}
