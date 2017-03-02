/**
 * 
 */
package com.bio.cip.database;

import java.io.Serializable;
import java.net.UnknownHostException;

import com.bio.cip.constants.ApplicationConstants;
import com.bio.cip.indexer.util.AppConfig;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

/**
 * <p>
 * </p>
 * 
 * @version 1.0
 * 
 */
public class MongoDBUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5105030054471942567L;
	private Mongo mongo = null;
	private static MongoOptions options;
	static {
		options = new MongoOptions();
		options.connectionsPerHost = 200;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Mongo getMongo() {
		if (mongo == null) {
			try {
				int port = Integer.valueOf(AppConfig
						.get(ApplicationConstants.DB_PORT));
				System.out.println("port: " + port);
				mongo = new Mongo(new ServerAddress(
						AppConfig.get(ApplicationConstants.DB_ADDRESS), port),
						options);

			} catch (MongoException | NumberFormatException
					| UnknownHostException e) {
				throw new RuntimeException(e);
			}
		}
		return mongo;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public DB getStaging() {
		Mongo mongo = getMongo();
		return mongo.getDB(AppConfig.get(ApplicationConstants.STAGING_DATA_BASE_NAME));
	}

	/**
	 * <p>
	 * </p>
	 * 
	 */
	public void disconnect() {
		if (null != mongo) {
			mongo.close();
		}
		mongo = null;
	}

}
