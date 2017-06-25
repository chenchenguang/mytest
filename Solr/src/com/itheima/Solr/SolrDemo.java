package com.itheima.Solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrDemo {
	/**
	 * 创建
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInsert() throws Exception {
		// 设置solr 服务接口 浏览器地址栏的地址
		String baseURL = "http://localhost:8080/solr";
		// 1、 创建HttpSolrServer对象，通过它和Solr服务器建立连接。
		HttpSolrServer httpSolrServer = new HttpSolrServer(baseURL);
		// 2、 创建SolrInputDocument对象，然后通过它来添加域。
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("id", "2");
		solrInputDocument.addField("name", "闫春庚");
		// 3、 通过HttpSolrServer对象将SolrInputDocument添加到索引库。
		httpSolrServer.add(solrInputDocument);
		// 4、 提交。
		httpSolrServer.commit();
	}

	/**
	 * 删除
	 */
	@Test
	public void testdelete() throws Exception {
		String baseURL = "http://localhost:8080/solr";
		HttpSolrServer httpSolrServer = new HttpSolrServer(baseURL);
		httpSolrServer.deleteById("6");
		httpSolrServer.commit();
	}
	
	/**
	 * 简单的查询
	 */
	@Test
	public void testelect() throws Exception {
		String baseURL = "http://localhost:8080/solr";
		HttpSolrServer httpSolrServer = new HttpSolrServer(baseURL);
		//查询
		SolrQuery solrQuery = new SolrQuery();
		//查询全部
//		solrQuery.setQuery("*:*");
		//根据id 查询一条
		solrQuery.set("q","id:1");
		
		QueryResponse response = httpSolrServer.query(solrQuery);
		SolrDocumentList docs = response.getResults();
		
		long numFound = docs.getNumFound();
		System.out.println("总条数:"+numFound);
		
		for (SolrDocument doc : docs) {
			System.out.println("ID:"+doc.get("id"));
			System.out.println("NAME:"+doc.get("name"));
		}
	}
}
