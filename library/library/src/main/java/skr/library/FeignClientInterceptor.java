package skr.library;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import skr.library.provider.JwtProvider;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void apply(RequestTemplate template) {
        String token = jwtProvider.getToken();
        if (token != null) {
            template.header("Authorization", "Bearer " + token);
        }
    }
}