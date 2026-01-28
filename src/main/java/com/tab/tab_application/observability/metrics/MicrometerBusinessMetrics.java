package com.tab.tab_application.observability.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MicrometerBusinessMetrics implements BusinessMetrics{
    private final Counter tabsCreated;
    private final Counter receiptsCreated;
    private final Counter invitesSent;

    public MicrometerBusinessMetrics(MeterRegistry registry) {
        this.tabsCreated = Counter.builder("tabs_created_total")
                .description("Total number of tabs created")
                .register(registry);

        this.receiptsCreated = Counter.builder("receipts_created_total")
                .description("Total number of receipts created")
                .register(registry);

        this.invitesSent = Counter.builder("tab_invites_sent_total")
                .description("Total number of tab invites sent")
                .register(registry);
    }

    @Override
    public void tabCreated() {
        tabsCreated.increment();
    }

    @Override
    public void receiptCreated() {
        receiptsCreated.increment();
    }

    @Override
    public void inviteSent() {
        invitesSent.increment();
    }
}
