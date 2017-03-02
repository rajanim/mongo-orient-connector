package com.bio.cip.indexer.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bio.cip.indexer.vo.WikiOrganizationVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>
 * This class is a util class to fetch details/data from wiki by passing the
 * organization name.
 * </p>
 * 
 * @version 1.0
 * 
 */
@SuppressWarnings("deprecation")
public class WikiUtil {
	private static Logger log = LoggerFactory.getLogger(WikiUtil.class);

	public static WikiOrganizationVO getWikiDetail(String name) {
		HttpClient httpclient = new DefaultHttpClient();
		StringBuilder queryParam = new StringBuilder();
		RegexBoilerplateRemoval bRemoval = new RegexBoilerplateRemoval();
		try {
			queryParam.append("titles=");
			queryParam.append(URLEncoder.encode(name, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			log.error(e1.getMessage(), e1);
		}

		HttpGet httpget = new HttpGet(
				"http://en.wikipedia.org/w/api.php?action=query&prop=extracts&format=json&exintro=&"
						+ queryParam);
		HttpResponse response;
		WikiOrganizationVO orgVo = null;
		try {
			response = httpclient.execute(httpget);
			orgVo = new WikiOrganizationVO();
			String jsonString = EntityUtils.toString(response.getEntity());
			JsonNode arrNode = new ObjectMapper().readTree(jsonString).get(
					"query");
			JsonNode pageNode = new ObjectMapper().readTree(arrNode
					.get("pages").toString());
			Iterator<Map.Entry<String, JsonNode>> iterator = pageNode.fields();
			while (iterator.hasNext()) {
				Map.Entry<String, JsonNode> cNode = iterator.next();
				if (Integer.valueOf(cNode.getKey()) != -1) {
					Iterator<JsonNode> childs = pageNode.elements();
					while (childs.hasNext()) {
						JsonNode object = childs.next();
						orgVo.setTitle(object.get("title").toString());
						orgVo.setExtractedContent(bRemoval.removeBoilerplate(object.get("extract")
								.toString()));
					}
				}
			}
			if (orgVo != null && orgVo.getTitle()!=null) {
				orgVo.setUrl("http://en.wikipedia.org/wiki/"
						+ orgVo.getTitle().replace("\"", ""));
			}
			httpclient.getConnectionManager().shutdown();
		} catch (IllegalArgumentException | IOException e) {
			log.error(e.getMessage(), e);
		}
		return orgVo;
	}
}
