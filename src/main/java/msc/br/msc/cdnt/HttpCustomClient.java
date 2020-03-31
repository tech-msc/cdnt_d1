package msc.br.msc.cdnt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpCustomClient {
	
	private String url_get =  "https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=******";
	private String url_post = "https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=******";

	//
    private final CloseableHttpClient httpClient = HttpClients.createDefault();


    private void close() throws IOException {
        httpClient.close();
    }

    public String getResultBody() throws Exception {
    	HttpGet request = new HttpGet(url_get);
        
        try (CloseableHttpResponse response = httpClient.execute(request)) {
 
            HttpEntity entity = response.getEntity();            

            if (entity != null) {
                // return it as a String
                String result = EntityUtils.toString(entity);
                return result;
            }

        }
        
        this.close();        
        return null;
    }
    

    public void sendPost() throws Exception {

        HttpPost post = new HttpPost(url_post);        
         
        File fileJson = new FileWork().getFileToSendPost();
       
        HttpEntity entity = (HttpEntity) MultipartEntityBuilder.create()
        		.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addPart("answer", new FileBody(fileJson))
//                .addPart("answer", new StringBody())
                .build();        
        
        
        post.setEntity(entity);
         
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        
    	this.close();

    }
}
