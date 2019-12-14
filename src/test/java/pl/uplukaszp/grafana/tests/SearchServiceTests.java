package pl.uplukaszp.grafana.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import pl.uplukaszp.grafana.domain.grafana.Metric;
import pl.uplukaszp.grafana.domain.thingspeak.ApiKey;
import pl.uplukaszp.grafana.domain.thingspeak.Channel;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelDescription;
import pl.uplukaszp.grafana.repository.ChannelRepository;
import pl.uplukaszp.grafana.service.SearchService;

public class SearchServiceTests {

	private static final String TEST_FIELD_NAME = "TestFieldName";
	private static final String TEST_READ_KEY = "TestReadKey";
	private static final String TEST_DESCRIPTION = "testDescription";
	private static final Integer TEST_ID = 0;
	private static final String TEST_CHANNEL_NAME = "TestChannelName";

	@Test
	public void testEmptyChannelRepository() {
		// given
		ChannelRepository channelRepository = mock(ChannelRepository.class);
		SearchService searchService = new SearchService(channelRepository);
		when(channelRepository.getChannelDescriptions()).thenReturn(Collections.emptyList());
		// when
		List<Metric> metrics = searchService.getMetrics();
		// then
		assertEquals(0, metrics.size());
	}

	@Test
	public void testGetChannelFeedArguments() {
		// given
		ChannelRepository channelRepository = mock(ChannelRepository.class);
		SearchService searchService = new SearchService(channelRepository);
		when(channelRepository.getChannelDescriptions()).thenReturn(getChannelDescription());
		when(channelRepository.getChannelFeed(Mockito.anyString(), Mockito.anyString())).thenReturn(new Channel());
		// when
		searchService.getMetrics();
		// then
		verify(channelRepository).getChannelFeed(TEST_ID.toString(), TEST_READ_KEY);
	}

	@Test
	public void testMetricsSizeWhenChannelHasNoFields() {
		// given
		ChannelRepository channelRepository = mock(ChannelRepository.class);
		SearchService searchService = new SearchService(channelRepository);
		when(channelRepository.getChannelDescriptions()).thenReturn(getChannelDescription());
		when(channelRepository.getChannelFeed(Mockito.anyString(), Mockito.anyString())).thenReturn(new Channel());
		// when
		List<Metric> metrics = searchService.getMetrics();
		// then
		assertEquals(0, metrics.size());
	}

	@Test
	public void testMetricsSizeWhenChannelHasField() {
		// given
		ChannelRepository channelRepository = mock(ChannelRepository.class);
		SearchService searchService = new SearchService(channelRepository);
		when(channelRepository.getChannelDescriptions()).thenReturn(getChannelDescription());
		when(channelRepository.getChannelFeed(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(getChannelWithField());
		// when
		List<Metric> metrics = searchService.getMetrics();
		// then
		assertEquals(1, metrics.size());
	}

	@Test
	public void testMetricsContent() {
		// given
		ChannelRepository channelRepository = mock(ChannelRepository.class);
		SearchService searchService = new SearchService(channelRepository);
		when(channelRepository.getChannelDescriptions()).thenReturn(getChannelDescription());
		when(channelRepository.getChannelFeed(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(getChannelWithField());
		// when
		List<Metric> metrics = searchService.getMetrics();
		// then
		assertEquals(TEST_CHANNEL_NAME+" - "+TEST_FIELD_NAME, metrics.get(0).getText());
		assertEquals(TEST_ID+","+1, metrics.get(0).getValue());

	}
	private List<ChannelDescription> getChannelDescription() {
		List<ChannelDescription> list = new ArrayList<>();
		ChannelDescription emptyChannelDescription = new ChannelDescription();
		emptyChannelDescription.setId(TEST_ID.toString());
		emptyChannelDescription.setDescription(TEST_DESCRIPTION);
		List<ApiKey> apiKeys = new ArrayList<>();
		ApiKey readKey = new ApiKey();
		readKey.setApiKey(TEST_READ_KEY);
		readKey.setWriteFlag(false);
		apiKeys.add(readKey);
		emptyChannelDescription.setApiKeys(apiKeys);
		list.add(emptyChannelDescription);
		return list;
	}

	private Channel getChannelWithField() {
		Channel channel = new Channel();
		channel.setField1(TEST_FIELD_NAME);
		channel.setId(TEST_ID);
		channel.setName(TEST_CHANNEL_NAME);
		return channel;
	}
}
