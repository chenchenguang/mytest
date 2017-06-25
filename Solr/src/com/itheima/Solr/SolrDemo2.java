package com.itheima.Solr;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrDemo2 {
	/**
	 * 复杂查询
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInsert() throws Exception {
		// 设置solr 服务接口 浏览器地址栏的地址
		String baseURL = "http://localhost:8080/solr";
		// 1、 创建HttpSolrServer对象，通过它和Solr服务器建立连接。
		SolrServer SolrServer = new HttpSolrServer(baseURL);
		
		SolrQuery solrQuery = new SolrQuery();
//		solrQuery.set("q", "product_name:钻石");
		solrQuery.setQuery("product_name:钻石");
		//过滤条件 solrQuery.set("fq", "product_pricce:[9 TO *]"); 无穷大
		//											[* TO 9]" 无穷小
		solrQuery.set("fq", "product_price:[4.2 TO 9]");
		
		//价格降
		solrQuery.setSort("product_price", ORDER.asc);
		//分页 从0开始
		solrQuery.setStart(0);
		//每页显示条数
		solrQuery.setRows(5);
		//显示的东西  field list
		solrQuery.set("fl", "id,product_name");
		
		//设置默认域  field list
		solrQuery.set("df", "product_name");
		
		//高亮
		//打开高亮的开关
		solrQuery.setHighlight(true);
		//设置高亮的域 product_name
		solrQuery.addHighlightField("product_name");
		//设置高亮的前缀 后缀
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		//执行查询
		QueryResponse response = SolrServer.query(solrQuery);
		//获取高亮
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//第一个map k id v map
		//第二个map k 域名 v 域值 (list 多值)
		
		//结果集
		SolrDocumentList docs = response.getResults();
		//总条数
		long numFound = docs.getNumFound();
		System.out.println("总条数:"+numFound);
		
		for (SolrDocument doc : docs) {
			System.out.println(doc.get("id"));
			System.out.println(doc.get("product_name"));
			System.out.println("高亮++++++++++++++++++++++++");
			Map<String, List<String>> map = highlighting.get(doc.get("id"));
			List<String> list = map.get("product_name");
			String string = list.get(0);
			System.out.println(string);
			
		}
	}
	
}
