package pl.uplukaszp.grafana.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Setter;
import pl.uplukaszp.grafana.domain.grafana.Metric;
import pl.uplukaszp.grafana.domain.thingspeak.Channel;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelDescription;
import pl.uplukaszp.grafana.domain.thingspeak.Field;
import pl.uplukaszp.grafana.dto.SearchTargetDTO;
import pl.uplukaszp.grafana.repository.ChannelRepository;
import pl.uplukaszp.grafana.repository.ReadKeyRepository;

@Service
@AllArgsConstructor
public class SearchService {

	private ChannelRepository channelRepository;
	private ReadKeyService readKeyService;

	public List<Metric> getMetrics(SearchTargetDTO target) {
		List<Metric> metrics = new ArrayList<>();
		List<ChannelDescription> channelDescriptions = channelRepository.getChannelDescriptions();
		for (ChannelDescription channelDescription : channelDescriptions) {
			String id = channelDescription.getId();
			String readKey = readKeyService.getReadKey(id);
			Channel channelFeed = channelRepository.getChannelFeed(id, readKey);
			List<Field> fields = channelFeed.getFields();
			for (Field field : fields) {
				Metric metric = getMetric(channelDescription, channelFeed, field);
				metrics.add(metric);
			}
		}
		return metrics;
	}

	private Metric getMetric(ChannelDescription channelDescription, Channel channelFeed, Field field) {
		Metric metric = new Metric();
		metric.setText(channelFeed.getName() + " - " + field.getName());
		metric.setValue(channelDescription.getId() + "," + field.getNumber());
		return metric;
	}

}
