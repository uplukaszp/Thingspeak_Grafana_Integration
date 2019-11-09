package pl.uplukaszp.grafana.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.uplukaszp.grafana.domain.thingspeak.ApiKey;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelDescription;
import pl.uplukaszp.grafana.repository.ChannelRepository;
import pl.uplukaszp.grafana.repository.ReadKeyRepository;

@Service
public class ReadKeyService {

	@Autowired
	ChannelRepository channelRepo;

	@Autowired
	ReadKeyRepository keyRepo;

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
			keyRepo.putReadKey(channelDescription.getId(), getKey(channelDescription.getApiKeys()));
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
