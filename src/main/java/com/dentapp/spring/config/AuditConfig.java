package com.dentapp.spring.config;

import com.dentapp.spring.security.services.UserDetailsImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
public class AuditConfig implements AuditorAware<String> {

    /**
     * Gets the current auditor.
     *
     * @return the current auditor
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        String currentUser = ((UserDetailsImpl)(SecurityContextHolder.getContext().getAuthentication()).getPrincipal()).getFullname();
        return Optional.ofNullable(currentUser);
    }
}