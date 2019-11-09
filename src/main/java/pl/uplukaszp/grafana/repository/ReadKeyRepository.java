package pl.uplukaszp.grafana.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ReadKeyRepository {
	Map<String, String> channelReadKeys = new HashMap<>();

	public String getReadKey(String channelId) {
		return channelReadKeys.get(channelId);
	}

	public String putReadKey(String channelId, String readKey) {
		return channelReadKeys.put(channelId, readKey);
	}

}
