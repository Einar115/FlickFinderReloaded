package mx.primis.interaction_service.client;

import mx.primis.interaction_service.model.dto.requests.TokenRequest;
import mx.primis.interaction_service.model.dto.responses.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${auth.service.url}")
public interface AuthServiceClient {
    @PostMapping("/auth/validate")
    TokenValidationResponse validateToken(@RequestBody TokenRequest request);
}
