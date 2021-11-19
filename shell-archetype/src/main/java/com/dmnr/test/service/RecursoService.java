package com.dmnr.test.service;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmnr.test.bean.Config;
import com.dmnr.test.bean.Recurso;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.Uninterruptibles;

public class RecursoService {
  private static final Logger LOG = LoggerFactory.getLogger(RecursoService.class);

  private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1,
      new ThreadFactoryBuilder().setNameFormat("recurso-service-%d").build());

  private final ListeningScheduledExecutorService ls = MoreExecutors.listeningDecorator(executor);

  private final Map<String, Recurso> recursos;

  public RecursoService(Map<String, Recurso> recursos) {
    this.recursos = recursos;
  }

  public static RecursoService recursoService() {
    return new RecursoService(Map.of("RECURSO_1", new Recurso("RECURSO_1"),
                                     "RECURSO_2", new Recurso("RECURSO_2") ));
  }

  public Recurso lookupRecurso(String nombre) {
    Uninterruptibles.sleepUninterruptibly(Config.CHANNEL_DELAY, TimeUnit.MILLISECONDS);
    LOG.info("Channel lookup complete");
    return recursos.get(nombre);
  }

}
