package com.jyyjr.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	
	/**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
            // 设置通用的请求属性
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "keep-alive");
            httpURLConnection.setRequestProperty("Content-type", "text/html;charset=UTF-8");
            httpURLConnection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpURLConnection.connect();
            // 建立实际的连接
            connection.connect();
           
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    
	
	/**
	 * 接收post请求
	 * @param url 请求地址
	 * @param jsonBody 请求参数 json 字符串
	 * @return
	 */
    public static String postBody(String url,String jsonBody) {
		 //创建httpclient工具对象   
       HttpClient client = new HttpClient();    
       //创建post请求方法   
       PostMethod myPost = new PostMethod(url);    
       //设置请求超时时间   
       //client.setConnectionTimeout(300*1000);
       String responseString = null;    
       try{    
           //设置请求头部类型   
           myPost.setRequestHeader("Content-Type","text/json");  
           myPost.setRequestHeader("charset","utf-8");  
             
           //设置请求体，即xml文本内容，注：这里写了两种方式，一种是直接获取xml内容字符串，一种是读取xml文件以流的形式   
//         myPost.setRequestBody(xmlString);   
             
           //InputStream body=this.getClass().getResourceAsStream("/"+xmlFileName);  
           //myPost.setRequestBody(jsonBody);  
          myPost.setRequestEntity(new StringRequestEntity(jsonBody,"text/json","utf-8"));     
           int statusCode = client.executeMethod(myPost);    
           if(statusCode == HttpStatus.SC_OK){    
               BufferedInputStream bis = new BufferedInputStream(myPost.getResponseBodyAsStream());    
               byte[] bytes = new byte[1024];    
               ByteArrayOutputStream bos = new ByteArrayOutputStream();    
               int count = 0;    
               while((count = bis.read(bytes))!= -1){    
                   bos.write(bytes, 0, count);    
               }    
               byte[] strByte = bos.toByteArray();    
               responseString = new String(strByte,0,strByte.length,"utf-8");    
               bos.close();    
               bis.close();    
           }    
       }catch (Exception e) {    
           e.printStackTrace();    
       }    
       myPost.releaseConnection();    
       return responseString;
		
	}
	
	/**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    /**
	 * @param url :请求的url
	 * @Param paraMap :请求参数
	 * @param contentType :请求头类型(不是必须)
	 * @param reponseType :设置响应数据格式（不是必须）
	 * @Description: 发送post请求
	 * @time:2018年2月6日下午6:45:07
	 */
	public static String sendPost(String url,Map<String,String> paramMap ,String contentType,String responseType){
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = "" ; //返回的结果集
        try {
        	httpClient = HttpClients.createDefault();
        	httpPost = new HttpPost(url);
        	if (contentType!=""&&contentType!=null) {
				httpPost.setHeader("Content-Type",contentType); //"application/json;charset=utf-8"...application/x-www-form-urlencoded
			}
        	if (responseType!=""&&responseType!=null) {
        		httpPost.setHeader("Accept-Encoding", contentType);
        	}
        	//设置参数:建立一个NameValuePair数组，用于存储准备传送的参数
        	List<NameValuePair> params = new ArrayList<NameValuePair>();
        	if (paramMap!=null&&paramMap.keySet().size()!=0) { //如果传送的参数不为空
				for (Entry<String, String> paramKeyValue : paramMap.entrySet()) {
					params.add(new BasicNameValuePair(paramKeyValue.getKey(), paramKeyValue.getValue()));
				}
			}
        		//将参数添加到请求对象中，并指定字符编码
        	httpPost.setEntity(new UrlEncodedFormEntity(params,"utf-8")); 
        		//执行httpPost请求并拿到结果(同步阻塞)
        	CloseableHttpResponse response = httpClient.execute(httpPost);
        		//如果返回的是普通字符串的格式执行
        	HttpEntity entity = response.getEntity();
        	if (entity!=null) {
        		result = EntityUtils.toString(entity, "utf-8");
        	}
        	EntityUtils.consume(entity); //释放实体对象
        	httpPost.releaseConnection();//释放链接
        	return result;
		} catch (Exception e) {
			e.printStackTrace();
			httpPost.releaseConnection();
			return "";//返回null
		}
	}
    
	public static void main(String[] args) {
		String jsonParam = "{\r\n" + 
    			"	\"mobile\": 15555555555,\r\n" + 
    			"	\"mobilebook\": [{\r\n" + 
    			"		\"name\": \"张三\",\r\n" + 
    			"		\"mobile\": 16666666666\r\n" + 
    			"	}, {\r\n" + 
    			"		\"name\": \"李四\",\r\n" + 
    			"		\"mobile\": 17777777777\r\n" + 
    			"	}],\r\n" + 
    			"	\"mobilecall\": [{\r\n" + 
    			"		\"call_other_number\": 16666666666,\r\n" + 
    			"		\"call_type_name\": \"主叫\",\r\n" + 
    			"		\"call_start_time\": 1500000000,\r\n" + 
    			"		\"call_time\": 53\r\n" + 
    			"	}, {\r\n" + 
    			"		\"call_other_number\": 16666666666,\r\n" + 
    			"		\"call_type_name\": \"被叫\",\r\n" + 
    			"		\"call_start_time\": 1500000000,\r\n" + 
    			"		\"call_time\": 53\r\n" + 
    			"	}, {\r\n" + 
    			"		\"call_other_number\": 17777777777,\r\n" + 
    			"		\"call_type_name\": \"主叫\",\r\n" + 
    			"		\"call_start_time\": 1500000000,\r\n" + 
    			"		\"call_time\": 53\r\n" + 
    			"	}, {\r\n" + 
    			"		\"call_other_number\": 17777777777,\r\n" + 
    			"		\"call_type_name\": \"被叫\",\r\n" + 
    			"		\"call_start_time\": 1500000000,\r\n" + 
    			"		\"call_time\": 53\r\n" + 
    			"	},{\r\n" + 
    			"		\"call_other_number\": 16666666666,\r\n" + 
    			"		\"call_type_name\": \"被叫\",\r\n" + 
    			"		\"call_start_time\": 1500000000,\r\n" + 
    			"		\"call_time\": 53\r\n" + 
    			"	}],\r\n" + 
    			"\r\n" + 
    			"	\"get_time\": 1500000000\r\n" + 
    			"}";
        String url="http://fk.jyyjrfw.com/Interface/credit";  
        String data=HttpUtils.postBody(url,jsonParam);  
        System.out.println(data);
	}

}
