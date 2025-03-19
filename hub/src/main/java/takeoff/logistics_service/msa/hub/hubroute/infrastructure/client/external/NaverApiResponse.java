package takeoff.logistics_service.msa.hub.hubroute.infrastructure.client.external;

import java.util.List;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
public record NaverApiResponse(int code, String message, Route route) {
}

record Route(List<Traoptimal> traoptimal) {}

record Traoptimal(Summary summary, List<Guide> guides) {}

record Summary(Start start, Goal goal, int distance, int duration) {}

record Guide(int pointIndex, int distance, int duration) {}

record Start(List<Double> location) {}

record Goal(List<Double> location, int dir) {}
