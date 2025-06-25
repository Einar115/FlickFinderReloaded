package mx.primis.interaction_service.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 401) {
            return new UnauthorizedException("Token inválido o expirado");
        }
        if (response.status() == 403) {
            return new ForbiddenException("Acceso denegado");
        }
        return new RuntimeException("Error en la comunicación con el servicio de autenticación");
    }
}
