package pl.uplukaszp.grafana.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.uplukaszp.grafana.domain.grafana.GraphDataResponse;
import pl.uplukaszp.grafana.domain.grafana.Metric;
import pl.uplukaszp.grafana.dto.QueryDTO;
import pl.uplukaszp.grafana.dto.SearchTargetDTO;

@RestController
public class GrafanaRequestController {
	Logger logger = LoggerFactory.getLogger(GrafanaRequestController.class);

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ResponseEntity<Object> index(@RequestBody(required = false) Map<String, String> request) {
		return ResponseEntity.ok(null);
	}

	@RequestMapping(path = "/search", method = RequestMethod.POST)
	public ResponseEntity<Metric> search(@RequestBody(required = false) SearchTargetDTO request) {
		return ResponseEntity.ok(null);
	}

	@RequestMapping(path = "/query", method = RequestMethod.POST)
	public ResponseEntity<GraphDataResponse> query(@RequestBody(required = false) QueryDTO request) {

		return ResponseEntity.ok(null);
	}
}
