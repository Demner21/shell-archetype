package com.dmnr.test.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import static java.util.concurrent.TimeUnit.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EstadosRecursoService {
  private static final Logger LOG = LoggerFactory.getLogger(EstadosRecursoService.class);
  
  private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1,
      new ThreadFactoryBuilder().setNameFormat("permissions-service-%d").build());
  
  private final ListeningScheduledExecutorService ls = MoreExecutors.listeningDecorator(executor);
  
  public static void main(String[] args) {
    var beep = new BeeperControl();
    beep.beepForAnHour();
  }
}

class BeeperControl {
  private final ScheduledExecutorService scheduler =
    Executors.newScheduledThreadPool(1);

  public void beepForAnHour() {
    Runnable beeper = () -> System.out.println("beep ::" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a")));
    ScheduledFuture<?> beeperHandle =
      scheduler.scheduleAtFixedRate(beeper, 0, 10, SECONDS);
    Runnable canceller = () -> beeperHandle.cancel(false);
    scheduler.schedule(canceller, 1, HOURS);
  }
}