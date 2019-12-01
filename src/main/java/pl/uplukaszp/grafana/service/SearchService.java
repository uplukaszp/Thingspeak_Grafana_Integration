package pl.uplukaszp.grafana.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pl.uplukaszp.grafana.domain.grafana.Metric;
import pl.uplukaszp.grafana.domain.thingspeak.Channel;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelDescription;
import pl.uplukaszp.grafana.domain.thingspeak.Field;
import pl.uplukaszp.grafana.repository.ChannelRepository;

@Service
@AllArgsConstructor
public class SearchService {

	private ChannelRepository channelRepository;

	public List<Metric> getMetrics() {
		List<Metric> metrics = new ArrayList<>();
		List<ChannelDescription> channelDescriptions = channelRepository.getChannelDescriptions();
		for (ChannelDescription channelDescription : channelDescriptions) {
			List<Metric> channellMetrics = getMetricsFromChannelDescription(channelDescription);
			metrics.addAll(channellMetrics);
		}
		return metrics;
	}

	private List<Metric> getMetricsFromChannelDescription(ChannelDescription channelDescription) {
		List<Metric> metrics = new ArrayList<>();
		String id = channelDescription.getId();
		String readKey = channelDescription.getReadKey();
		Channel channelFeed = channelRepository.getChannelFeed(id, readKey);
		List<Field> fields = channelFeed.getFields();
		for (Field field : fields) {
			Metric metric = createMetric(channelDescription, channelFeed, field);
			metrics.add(metric);
		}
		return metrics;
	}

	private Metric createMetric(ChannelDescription channelDescription, Channel channelFeed, Field field) {
		Metric metric = new Metric();
		metric.setText(channelFeed.getName() + " - " + field.getName());
		metric.setValue(channelDescription.getId() + "," + field.getNumber());
		return metric;
	}

}
