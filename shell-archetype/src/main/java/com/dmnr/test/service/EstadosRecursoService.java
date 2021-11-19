package com.dmnr.test.service;

import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmnr.test.bean.Config;
import com.dmnr.test.bean.EstadosRecursoBean;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.Uninterruptibles;

public class EstadosRecursoService {
  private static final Logger LOG = LoggerFactory.getLogger(EstadosRecursoService.class);

  private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1,
      new ThreadFactoryBuilder().setNameFormat("estadosRecurso-service-%d").build());

  private final ListeningScheduledExecutorService ls = MoreExecutors.listeningDecorator(executor);

  private Map<Integer, EstadosRecursoBean> estados;

  private EstadosRecursoService(Map<Integer, EstadosRecursoBean> estadosRecursos) {
    this.estados = estadosRecursos;
  }

  public static EstadosRecursoService estadosRecursoService() {
    Map<Integer, EstadosRecursoBean> of = Map.of(
                                      1, EstadosRecursoBean.estadosRecurso("INICIADO", "PROCESADO"), 
                                      2, EstadosRecursoBean.estadosRecurso("INICIADO")
                                      );
    return new EstadosRecursoService(of);
  }
  
  public EstadosRecursoBean estadosRecurso(int userId) {
    Uninterruptibles.sleepUninterruptibly(Config.PERMISSION_DELAY, TimeUnit.MILLISECONDS);
    LOG.info("Permission lookup complete");
    return estados.get(userId);
}

  public static void main(String[] args) {
    var beep = new BeeperControl();
    beep.beepForAnHour();
  }
}

class BeeperControl {
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  public void beepForAnHour() {
    Runnable beeper = () -> System.out
        .println("beep ::" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a")));
    ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 10, SECONDS);
    Runnable canceller = () -> beeperHandle.cancel(false);
    scheduler.schedule(canceller, 1, HOURS);
  }
}