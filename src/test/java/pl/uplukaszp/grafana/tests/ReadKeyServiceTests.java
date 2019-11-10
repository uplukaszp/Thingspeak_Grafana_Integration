package pl.uplukaszp.grafana.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import pl.uplukaszp.grafana.domain.thingspeak.ApiKey;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelDescription;
import pl.uplukaszp.grafana.repository.ChannelRepository;
import pl.uplukaszp.grafana.repository.ReadKeyRepository;
import pl.uplukaszp.grafana.service.ReadKeyService;

public class ReadKeyServiceTests<E> {

	@Test
	public void getReadKeyFromChannelWithAssignedReadKey() {
		// given
		ReadKeyRepository keyRepo = new ReadKeyRepository();
		ChannelRepository channelRepo = mock(ChannelRepository.class);
		ReadKeyService readKeyService = new ReadKeyService(channelRepo, keyRepo);
		when(channelRepo.getChannelDescriptions()).thenReturn(getChannelWithAssignedReadKey());
		// when
		readKeyService.init();
		// then
		String readKey = readKeyService.getReadKey("0");
		assertNotNull("ReadKey is null",readKey);
		assertEquals("ReadKey value is wrong", "0",readKey);
	}
	@Test
	public void getReadKeyFromChannelWithAssignedWriteKey() {
		// given
		ReadKeyRepository keyRepo = new ReadKeyRepository();
		ChannelRepository channelRepo = mock(ChannelRepository.class);
		ReadKeyService readKeyService = new ReadKeyService(channelRepo, keyRepo);
		when(channelRepo.getChannelDescriptions()).thenReturn(getChannelWithAssignedWriteKey());
		// when
		readKeyService.init();
		// then
		String readKey = readKeyService.getReadKey("0");
		assertNull("ReadKey isnt null",readKey);
	}
	
	private List<ChannelDescription> getChannelWithAssignedReadKey() {
		List<ChannelDescription> channels = new ArrayList<ChannelDescription>();
		ChannelDescription channel=new ChannelDescription();
		channel.setId("0");
		List<ApiKey> apiKeys=new ArrayList<ApiKey>();
		ApiKey readKey = new ApiKey();
		readKey.setApiKey("0");
		readKey.setWriteFlag(false);
		apiKeys.add(readKey);
		channel.setApiKeys(apiKeys);
		channels.add(channel);
		return channels;
	}
	private List<ChannelDescription> getChannelWithAssignedWriteKey() {
		List<ChannelDescription> channels = new ArrayList<ChannelDescription>();
		ChannelDescription channel=new ChannelDescription();
		channel.setId("0");
		List<ApiKey> apiKeys=new ArrayList<ApiKey>();
		ApiKey readKey = new ApiKey();
		readKey.setApiKey("0");
		readKey.setWriteFlag(true);
		apiKeys.add(readKey);
		channel.setApiKeys(apiKeys);
		channels.add(channel);
		return channels;
	}
}
