/**
 * 
 */
package com.appdynamics.cloud.modern.analytics;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.appdynamics.cloud.modern.Logger;
import com.appdynamics.cloud.modern.config.EventsServiceConfig;

/**
 * @author James Schneider
 *
 */
public class AnalyticsEventsDriver implements Runnable {

	private EventsServiceConfig eventsServiceConfig;
	private AnalyticsEventsSource analyticsEventsSource;
	private static Logger logr = new Logger(AnalyticsEventsDriver.class.getSimpleName());
	
	/**
	 * 
	 */
	public AnalyticsEventsDriver(EventsServiceConfig eventsServiceConfig, AnalyticsEventsSource analyticsEventsSource) {
		this.eventsServiceConfig = eventsServiceConfig;
		this.analyticsEventsSource = analyticsEventsSource;
		
	}

	@Override
	public void run() {
		
		while (true) {

			try {
				
				logr.info("##############################  Processing Batch Events for '" + this.analyticsEventsSource.getSchemaName() + "' schema ##############################");
				String payload = this.analyticsEventsSource.getPublishEventsJson();
				
				String publishUrl = this.eventsServiceConfig.getEventsServiceEndpoint() + "/events/publish/" + this.analyticsEventsSource.getSchemaName();
				
				this.publishEvents(this.eventsServiceConfig.getControllerGlobalAccount(), 
						this.eventsServiceConfig.getEventsServiceApikey(), publishUrl, payload, this.analyticsEventsSource.getSchemaName());
				
				Thread.currentThread().sleep(this.analyticsEventsSource.getPublishEventsInterval() * 60000);
				
			} catch (Throwable ex) {
				ex.printStackTrace();
			}
			
		}
		
	}

	private void publishEvents(String accountName, String apiKey, String restEndpoint, String payload, String schemaName) throws Throwable {
		
		CloseableHttpClient client = HttpClients.createDefault();
		
		HttpPost request = new HttpPost(restEndpoint);
		request.addHeader("X-Events-API-AccountName", accountName);
		request.addHeader("X-Events-API-Key", apiKey);

		request.addHeader("Content-Type", "application/vnd.appd.events+json;v=2");
		request.addHeader("Accept", "application/vnd.appd.events+json;v=2");

	    StringEntity entity = new StringEntity(payload);
	    request.setEntity(entity);
	    
	    CloseableHttpResponse response = client.execute(request);
		
	    logr.info(" - Schema: " + schemaName + " : HTTP Status: " + response.getStatusLine().getStatusCode());
	    
		String resp = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }			
		
        resp = out.toString();
		reader.close();
		
		//logr.info("Publish Events Response");
		//logr.info(resp);

		HttpClientUtils.closeQuietly(response);
		HttpClientUtils.closeQuietly(client);
		
		
	}
	
	
}
