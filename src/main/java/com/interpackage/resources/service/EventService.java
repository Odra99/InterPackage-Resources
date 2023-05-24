package com.interpackage.resources.service;

import com.interpackage.basedomains.dto.RouteEvent;
import com.interpackage.resources.interfaces.EventInterface;
import com.interpackage.resources.model.Route;
import com.interpackage.resources.producers.RouteProducer;
import com.interpackage.resources.util.Constants;
import org.springframework.stereotype.Service;

@Service
public class EventService implements EventInterface {

    private final RouteProducer routeProducer;

    public EventService(RouteProducer routeProducer) {
        this.routeProducer = routeProducer;
    }

    @Override
    public void sendNotification(Route route) {
        RouteEvent routeEvent = new RouteEvent();
        routeEvent.setMessage(Constants.MESSAGE_NOTIFICATION);
        routeEvent.setStatus(Constants.PENDING_STATE);
        routeEvent.setRoute(
                new com.interpackage.basedomains.dto.Route(
                        "Origen",
                        "Destino",
                        route.getName()));
        routeProducer.sendMessage(routeEvent);
    }
}
