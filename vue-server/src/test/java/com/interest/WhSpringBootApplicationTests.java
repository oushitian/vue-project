package com.interest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.interest.dao.sf.SfProductDetailsMapper;
import com.interest.dao.sf.SfProductMapper;
import com.interest.dao.sf.SfRegionMapper;
import com.interest.domain.PageDO;
import com.interest.domain.mall.MallDO;
import com.interest.domain.mall.Product;
import com.interest.domain.order.OrderCancelDO;
import com.interest.domain.order.OrderDO;
import com.interest.domain.order.OrderProduct;
import com.interest.domain.stock.StockDO;
import com.interest.domain.stock.StockProduct;
import com.interest.model.sf.SfCategory;
import com.interest.dao.sf.SfCategoryMapper;
import com.interest.model.sf.SfProduct;
import com.interest.model.sf.SfProductDetails;
import com.interest.model.sf.SfRegion;
import com.interest.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
	@Autowired
    SfRegionMapper sfRegionMapper;
	@Autowired
    SfProductMapper sfProductMapper;
	@Autowired
    SfProductDetailsMapper sfProductDetailsMapper;

	String clientId = "G034E1L8XW";
	String clientSecret = "72b8b1799af8e8c3edd12e51525cefde5627d419";
	String accessToken = "9dc18248-ac3e-401e-a00f-9db16c0e7bd1";

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
		JSONObject jsonObject = JSON.parseObject(result);
		Integer totalCount = jsonObject.getJSONObject("data").getInteger("totalCount");
		Integer count = totalCount/10+1;
		for (int i = 1420 ; i < count ; i ++) {
            PageDO page = new PageDO();
            page.setPage(i+1+"");
            page.setPageSize("10");
            String results = OkHttpUtil.postJsonParams(url, JSON.toJSONString(page));
            JSONObject jsonObjects = JSON.parseObject(results);
            if (jsonObjects == null || jsonObjects.getJSONObject("data") == null) {
                continue;
            }
            JSONArray jsonArray = jsonObjects.getJSONObject("data").getJSONArray("productList");
            if (jsonArray == null) {
                continue;
            }
            for (Object obj : jsonArray) {
                JSONObject o = (JSONObject) obj;
                JSONObject o1 = o.getJSONObject("baseInfo");
                JSONArray p = o.getJSONArray("picture");
                JSONObject c1 = o.getJSONObject("categoryOne");
                JSONObject c2 = o.getJSONObject("categoryTwo");
                JSONObject c3 = o.getJSONObject("categoryThree");
                JSONObject b = o.getJSONObject("brand");
                JSONObject f = o.getJSONObject("finance");
                JSONObject sf = o.getJSONObject("sfshipping");
                //插入商品
                SfProduct sfProduct = JSON.parseObject(o1.toJSONString(),SfProduct.class);
                sfProduct.setSalePrice(o1.getInteger("price") == null ? 0 : o1.getInteger("price"));
                sfProduct.setCostPrice(o.getInteger("costPrice"));
                sfProduct.setCategoryOneName(c1.getString("categoryName") == null ? StringUtils.EMPTY : c1.getString("categoryName"));
                sfProduct.setCategoryOneCode(c1.getString("categoryCode") == null ? StringUtils.EMPTY : c1.getString("categoryCode"));
                sfProduct.setCategoryTwoName(c2.getString("categoryName") == null ? StringUtils.EMPTY : c2.getString("categoryName"));
                sfProduct.setCategoryTwoCode(c2.getString("categoryCode") == null ? StringUtils.EMPTY : c2.getString("categoryCode"));
                sfProduct.setCategoryThreeName(c3.getString("categoryName") == null ? StringUtils.EMPTY : c3.getString("categoryName"));
                sfProduct.setCategoryThreeCode(c3.getString("categoryCode") == null ? StringUtils.EMPTY : c3.getString("categoryCode"));
                if (b!=null) {
                    sfProduct.setBrandName(b.getString("brandName"));
                    sfProduct.setBrandEnglishName(b.getString("englishName") == null ? StringUtils.EMPTY : b.getString("englishName"));
                }
                sfProduct.setSfairline(sf.getString("sfairline"));
                sfProduct.setSfshipping(sf.getString("sfshipping"));
                if (f!=null) {
                    sfProduct.setTax(f.getInteger("tax") == null ? 0 : f.getInteger("tax"));
                    sfProduct.setInTax(f.getInteger("inTax") == null ? 0 : f.getInteger("inTax"));
                    sfProduct.setOutTax(f.getInteger("outTax") == null ? 0 : f.getInteger("outTax"));
                }
                String pStr = "";
                if (p!=null) {
                    for (Object p1 : p) {
                        if (!((JSONObject) p1).getBoolean("delete")) {
                            pStr += ((JSONObject) p1).getString("filename") + "#";
                        }
                    }
                }
                sfProduct.setFilename(pStr);
                SfProduct hasP = sfProductMapper.selectByPrimaryKey(sfProduct.getProductId());
                if (hasP!=null) {
                    continue;
                }
                sfProductMapper.insertSelective(sfProduct);
                //根据商品编号插入详情
//                getGoodsDetail(sfProduct.getNumber());
            }
		}
	}

	//获取商品详情
    @Test
	public void getGoodsDetail(){
	    Integer total = sfProductMapper.countTotal();
	    for (int i = 0 ; i < total/10+1 ; i ++) {
            PageHelper.startPage(i + 1, 10);
            List<SfProduct> lists = sfProductMapper.findAll(null);
            Set<String> list = new HashSet<>();
            for (SfProduct sfProduct : lists) {
                list.add(sfProduct.getNumber());
            }
            String url = "https://testapi.sfbest.com/open-api/open/product/queryProductInfoDetails?app_key=" + clientId + "&access_token=" + accessToken;
            String result = OkHttpUtil.postJsonParams(url, JSON.toJSONString(list));
            JSONObject jsonObjects = JSON.parseObject(result);
            JSONArray jsonArray = jsonObjects.getJSONObject("data").getJSONArray("productInfoDetails");
            for (Object obj : jsonArray) {
                JSONObject o = (JSONObject) obj;
                SfProductDetails sfProductDetails = JSON.parseObject(o.toJSONString(), SfProductDetails.class);
                SfProductDetails hasExist = sfProductDetailsMapper.selectByPrimaryKey(sfProductDetails.getId());
                if (hasExist != null) {
                    continue;
                }
                sfProductDetailsMapper.insertSelective(sfProductDetails);
            }
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
		String url = "https://testapi.sfbest.com/open-api/open/product/stockInfo?app_key="+clientId+"&access_token="+accessToken;
		StockDO stockDO = new StockDO();
		stockDO.setInvokeSource(0);
		List<StockProduct> stockProductList = new ArrayList<>();
		StockProduct stockProduct = new StockProduct();
		stockProduct.setProductId(18788);
		stockProduct.setProductNum(1);
		stockProduct.setRegionId(503);
		stockProduct.setSfairline("0");
		stockProduct.setSfshipping(10000);
		stockProductList.add(stockProduct);
		stockDO.setStockInfoRegionParamList(stockProductList);
		String result = OkHttpUtil.postJsonParams(url,JSON.toJSONString(stockDO));
		log.info(result);
	}

	@Test
	//获取区域字典
	public void getRegion(){
		String url = "https://testapi.sfbest.com/open-api/open/product/queryRegionList?app_key="+clientId+"&access_token="+accessToken;
		String result = OkHttpUtil.postJsonParams(url,"");
//		log.info(result);
		JSONObject jsonObject = JSON.parseObject(result);
		JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("regionList");
		for (Object obj : jsonArray) {
			JSONObject o = (JSONObject) obj;
			SfRegion sfRegion = JSON.parseObject(o.toJSONString(),SfRegion.class);
            SfRegion hasExist = sfRegionMapper.selectByPrimaryKey(sfRegion.getRegionId());
			if (hasExist != null) {
				continue;
			}
            sfRegionMapper.insertSelective(sfRegion);
		}
	}

	@Test
    //创建订单
    public void createOrder(){
        String url = "https://testapi.sfbest.com/open-api/open/order/createOrder?app_key="+clientId+"&access_token="+accessToken;
        OrderDO orderDO = new OrderDO();
        orderDO.setOuterId("3141592654875");
        orderDO.setOrderAmount(13000);          //订单总额
        orderDO.setProductAmount(12300);        //商品总额
        orderDO.setCity(52);
        orderDO.setProvince(2);
        orderDO.setDistrict(507);
        orderDO.setArea(0);
        orderDO.setPayTime(12300);
        orderDO.setAddress("浙江宁波");
        orderDO.setConsignee("a");
        orderDO.setMobile("13116665898");
        orderDO.setShippingFee(700);        //运费 （可以自己填写  也可以后期和顺丰结算）
        orderDO.setMoneyPaid(13000);        //已支付金额
        List<OrderProduct> list = new ArrayList<>();
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductSn("1700017653");
        orderProduct.setProductNum(1);
        orderProduct.setProductType(0);
        orderProduct.setSellPrice(12300);
        list.add(orderProduct);
        orderDO.setOrderProducts(list);
        System.err.println(JSON.toJSONString(orderDO));
        String result = OkHttpUtil.postJsonParams(url,JSON.toJSONString(orderDO));
        log.info(result);
    }

    @Test
    //查询订单的接口
    public void queryOrder(){
        String url = "https://testapi.sfbest.com/open-api/open/order/getOrderList?app_key="+clientId+"&access_token="+accessToken;
        PageDO pageDO = new PageDO();
        pageDO.setPage("1");
        pageDO.setPageSize("10");
        String result = OkHttpUtil.postJsonParams(url, JSON.toJSONString(pageDO));
        log.info(result);
    }

    @Test
    //取消订单的接口
    public void cancelOrder(){
        String url = "https://testapi.sfbest.com/open-api/open/order/cancelOrder?app_key="+clientId+"&access_token="+accessToken;
        OrderCancelDO orderCancelDO = new OrderCancelDO();
        orderCancelDO.setCancelType(6);     //6代表开放平台
        orderCancelDO.setFrontShow(1);      //展示日志
        orderCancelDO.setNotes("取消订单");
        orderCancelDO.setOperator("fd");
        orderCancelDO.setOrderSn("11");     //订单编号
        orderCancelDO.setReturnDirection(1);//哪里来哪里去
        String result = OkHttpUtil.postJsonParams(url, JSON.toJSONString(orderCancelDO));
        log.info(result);
    }
}
