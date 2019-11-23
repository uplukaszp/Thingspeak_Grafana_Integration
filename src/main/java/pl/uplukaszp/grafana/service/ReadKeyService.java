package pl.uplukaszp.grafana.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pl.uplukaszp.grafana.domain.thingspeak.ApiKey;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelDescription;
import pl.uplukaszp.grafana.repository.ChannelRepository;
import pl.uplukaszp.grafana.repository.ReadKeyRepository;

@Service
@AllArgsConstructor
public class ReadKeyService {

	private ChannelRepository channelRepo;
	private ReadKeyRepository keyRepo;

	@PostConstruct
	public void init() {
		List<ChannelDescription> channelDescriptions = channelRepo.getChannelDescriptions();
		saveReadKeys(channelDescriptions);
	}

	public String getReadKey(String channelId) {
		return keyRepo.getReadKey(channelId);
	}

	private void saveReadKeys(List<ChannelDescription> chanells) {
		for (ChannelDescription channelDescription : chanells) {
			String id = channelDescription.getId();
			String key = getKey(channelDescription.getApiKeys());
			if (key != null)
				keyRepo.putReadKey(id, key);
		}
	}

	private String getKey(List<ApiKey> apiKeys) {
		for (ApiKey apiKey : apiKeys) {
			if (apiKey.getWriteFlag() == false)
				return apiKey.getApiKey();
		}
		return null;
	}
}
