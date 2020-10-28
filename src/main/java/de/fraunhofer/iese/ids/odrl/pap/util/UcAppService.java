/**
 * 
 */
package de.fraunhofer.iese.ids.odrl.pap.util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.concurrent.TimeUnit;

import de.fraunhofer.iese.ids.odrl.pap.model.JsonOdrlPolicy;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Robin Brandstaedter <Robin.Brandstaedter@iese.fraunhofer.de>
 *
 */
public class UcAppService {
	
	
	
	private static UcAppRestCallService ucAppRestCallService(String baseUrl) {
		final OkHttpClient okHttpClient = new OkHttpClient.Builder()
		        .readTimeout(60, TimeUnit.SECONDS)
		        .connectTimeout(60, TimeUnit.SECONDS)
		        .build();
		
		return new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build().create(UcAppRestCallService.class);
	}

	
	public static String sendPolicy(JsonOdrlPolicy jsonOdrlPolicy) {
		RequestBody body = RequestBody.create(MediaType.parse("application/ld+json;charset=UTF-8"), jsonOdrlPolicy.getJsonString());
		
		Call<ResponseBody> callSync = ucAppRestCallService(jsonOdrlPolicy.getUcAppUrl()).addPolicy(body);
		try {
			Response<ResponseBody> response = callSync.execute();
			if (!response.isSuccessful()) {
				throw new InvalidObjectException("could not apply the policy");
			}
			return response.body().string(); 
		}catch (IOException ioe) {
			return "";
		}
	}
}
