package pl.uplukaszp.grafana.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import pl.uplukaszp.grafana.domain.thingspeak.ApiKey;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelDescription;
import pl.uplukaszp.grafana.repository.ChannelRepository;
import pl.uplukaszp.grafana.repository.ReadKeyRepository;
import pl.uplukaszp.grafana.service.ReadKeyService;

public class ReadKeyServiceTests {

	private String testChannelId = "channelTestID";
	private String testReadKey = "testReadKey";

	@Test
	public void testStoringReadAPIKey() {
		// given
		ChannelRepository channelRepo = mock(ChannelRepository.class);
		ReadKeyRepository keyRepo = mock(ReadKeyRepository.class);
		ReadKeyService service = new ReadKeyService(channelRepo, keyRepo);
		when(channelRepo.getChannelDescriptions()).thenReturn(getChannelDescriptionWithReadAPIKey());
		// when
		service.init();
		// then
		verify(keyRepo).putReadKey(testChannelId, testReadKey);
	}

	@Test
	public void testStoringWriteAPIKey() {
		// given
		ChannelRepository channelRepo = mock(ChannelRepository.class);
		ReadKeyRepository keyRepo = mock(ReadKeyRepository.class);
		ReadKeyService service = new ReadKeyService(channelRepo, keyRepo);
		when(channelRepo.getChannelDescriptions()).thenReturn(getChannelDescriptionWithWriteAPIKey());
		// when
		service.init();
		// then
		verify(keyRepo, never()).putReadKey(Mockito.anyString(), Mockito.anyString());
	}

	@Test
	public void testGettingReadApiKey() {
		// given
		ChannelRepository channelRepo = mock(ChannelRepository.class);
		ReadKeyRepository keyRepo = mock(ReadKeyRepository.class);
		ReadKeyService service = new ReadKeyService(channelRepo, keyRepo);
		when(keyRepo.getReadKey(testChannelId)).thenReturn(testReadKey);
		// when
		String readKey = service.getReadKey(testChannelId);
		// then
		assertEquals(testReadKey,readKey);
	}

	private List<ChannelDescription> getChannelDescriptionWithReadAPIKey() {
		List<ChannelDescription> list = new ArrayList<>();
		ChannelDescription channelDescription = new ChannelDescription();
		channelDescription.setId(testChannelId);
		List<ApiKey> apiKeys = new ArrayList<>();
		ApiKey apiKey = new ApiKey();
		apiKey.setWriteFlag(false);
		apiKey.setApiKey(testReadKey);
		apiKeys.add(apiKey);
		channelDescription.setApiKeys(apiKeys);
		list.add(channelDescription);
		return list;
	}

	private List<ChannelDescription> getChannelDescriptionWithWriteAPIKey() {
		List<ChannelDescription> list = new ArrayList<>();
		ChannelDescription channelDescription = new ChannelDescription();
		channelDescription.setId(testChannelId);
		List<ApiKey> apiKeys = new ArrayList<>();
		ApiKey apiKey = new ApiKey();
		apiKey.setWriteFlag(true);
		apiKey.setApiKey(testReadKey);
		apiKeys.add(apiKey);
		channelDescription.setApiKeys(apiKeys);
		list.add(channelDescription);
		return list;
	}
}
