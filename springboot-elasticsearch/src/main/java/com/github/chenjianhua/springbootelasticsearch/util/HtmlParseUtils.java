package com.github.chenjianhua.springbootelasticsearch.util;

/**
 * @author chenjianhua
 * @date 2020/9/2
 */

import com.github.chenjianhua.springbootelasticsearch.model.JdProduct;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : ywb
 * @createdDate : 2020/5/31
 * @updatedDate
 */
@Slf4j
public class HtmlParseUtils {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 爬取京东商城搜索数据
     */
    public static List<JdProduct> listJdGoods(String productName) throws IOException {
        String url = "https://search.jd.com/Search?keyword=" + productName;
        Response response = OkHttpUtil.getSync(url);
        List<JdProduct> list = new ArrayList<>();
        if (response.isSuccessful()) {
            Document document = Jsoup.parse(response.body().string());
            Element element = document.getElementById("J_goodsList");
            Elements li = element.getElementsByTag("li");
            for (Element item : li) {
                String sku = item.attr("data-sku");
                String price = item.getElementsByClass("p-price").first().getElementsByTag("i").text();
                String title = item.getElementsByClass("p-name").eq(0).text();
                String shopName = item.getElementsByClass("p-shop").first().getElementsByTag("a").text();
                JdProduct product = new JdProduct();
                product.setSku(sku);
                product.setPrice(price);
                product.setProductName(title);
                product.setShopName(shopName);
                list.add(product);
            }
        }
        return list;
    }

}