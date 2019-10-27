package pl.uplukaszp.grafana.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.uplukaszp.grafana.domain.grafana.Metric;
import pl.uplukaszp.grafana.domain.thingspeak.ApiKey;
import pl.uplukaszp.grafana.domain.thingspeak.Channel;
import pl.uplukaszp.grafana.domain.thingspeak.ChannelDescription;
import pl.uplukaszp.grafana.domain.thingspeak.Field;
import pl.uplukaszp.grafana.dto.SearchTargetDTO;
import pl.uplukaszp.grafana.repository.ChannelRepository;

@Service
public class SearchService {

	@Autowired
	ChannelRepository channelRepository;

	public List<Metric> getMetrics(SearchTargetDTO target) {
		List<Metric> metrics = new ArrayList<>();
		List<ChannelDescription> channelDescriptions = channelRepository.getChannelDescriptions();
		for (ChannelDescription channelDescription : channelDescriptions) {
			String id = channelDescription.getId();
			String readKey = channelRepository.getReadKey(id);
			Channel channelFeed = channelRepository.getChannelFeed(id, readKey);
			List<Field> fields = channelFeed.getFields();
			for (Field field : fields) {
				Metric metric = new Metric();
				metric.setText(channelFeed.getName() + " - " + field.getName());
				metric.setValue(channelDescription.getId() + "," + field.getNumber());
				metrics.add(metric);
			}
		}
		return metrics;
	}

}
