package org.apache.flume.sink;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.log4j.Logger;


/**
 * @author jose alvarez muguerza
 */
public class HttpSink extends AbstractSink implements Configurable {
	private String wsURL;

	private static final Logger LOG = Logger.getLogger(HttpSink.class);

	public void configure(Context context) {
		String wsEndpoint = context.getString("endpoint", "127.0.0.1");
		String wsPort = context.getString("port", "8080");
		validateFullEndpoint(wsEndpoint, wsPort);

		this.wsURL = wsEndpoint + ":" + wsPort;
	}

	@Override
	public void start() {
		// Initialize the connection to the external repository (e.g. HDFS) that
		// this Sink will forward Events to ..
		LOG.debug("Start method. Open new connection");
		return;
	}

	@Override
	public void stop() {
		// Disconnect from the external respository and do any
		// additional cleanup (e.g. releasing resources or nulling-out
		// field values) ..
		LOG.debug("Stop method. Closed connection");

		return;
	}

	public Status process() throws EventDeliveryException {
		Status status = null;
		
		// Start transaction
		Channel ch = getChannel();
		Transaction txn = ch.getTransaction();
		txn.begin();
		try {
			Event event = ch.take();
			String message = URLEncoder.encode(new String(event.getBody()),
					"UTF-8");
			URL serverURL = new URL(this.wsURL);
			LOG.debug("New event ready: " + message + " --> " + wsURL);
			
			HttpURLConnection connection = (HttpURLConnection) serverURL
					.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			OutputStreamWriter wr = new OutputStreamWriter(
					connection.getOutputStream());
			
			wr.write(message);
			wr.flush();
			connection.getInputStream();
			connection.disconnect();
			LOG.debug("message sucessfuly sent");
			txn.commit();
			status = Status.READY;
		} catch (Throwable t) {
			txn.rollback();

			LOG.error(t.getMessage());

			status = Status.BACKOFF;

			// re-throw all Errors
			if (t instanceof Error) {
				throw (Error) t;
			}
		} finally {
			txn.close();
		}
		return status;
	}

	//private char[] convertToJSON(final String message) {
	//	JSONObject jsonObj = new JSONObject();
		
	//	StringTokenizer tokens = new StringTokenizer(message, " - ");
	//	while (tokens.hasMoreElements()){
	//		dataSet.put("id", ) ;
	//		dataSet.put("client_id", "SomeValueB") ;
	//		dataSet.put("keyC", "SomeValueC") ;	
	//	}
		
		
	//	return jsonObj.append("news", news);
		
	//	JSONObject dataSet = new JSONObject();
		
		
	//}

	private void validateFullEndpoint(final String endpoint, final String port) {
		try {
			URL url = new URL(endpoint + ":" + port);
			URLConnection conn = url.openConnection();
			conn.connect();
		} catch (MalformedURLException e) {
			String errMsg = "Web Service endpoint is malformed: (" + endpoint + ":" + port + ")";
			LOG.error(errMsg);
			new EventDeliveryException(errMsg );
		} catch (IOException e) {
			String errMsg = "Web Service endpoint is not valid: (" + endpoint + ":" + port + ")";
			LOG.error(errMsg);
			new IOException(errMsg);
		}
	}
}
