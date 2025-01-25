package dev.eislyn.chronos_calendar.config;

import dev.eislyn.chronos_calendar.utils.UserReadDomainServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Long> {
    private final UserReadDomainServiceImpl userReadDomainServiceImpl;

    @Override
    public Optional<Long> getCurrentAuditor() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();

        return Optional.ofNullable(userReadDomainServiceImpl.me(request).getId());
    }
}
