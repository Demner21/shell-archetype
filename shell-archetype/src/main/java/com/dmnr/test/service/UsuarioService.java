package com.dmnr.test.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmnr.test.bean.Config;
import com.dmnr.test.bean.Usuario;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.Uninterruptibles;

public class UsuarioService {
  private static final Logger LOG = LoggerFactory.getLogger(UsuarioService.class);

  private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1,
      new ThreadFactoryBuilder().setNameFormat("usuario-service-%d").build());
  private final ListeningScheduledExecutorService ls = MoreExecutors.listeningDecorator(executor);
  private final Map<String, Usuario> usuarios;

  public UsuarioService(Map<String, Usuario> usuarios) {
    this.usuarios = usuarios;
  }

  public static UsuarioService usuarioService() {
    return new UsuarioService(Map.of(
        "chbatey",new Usuario("Christopher Batey", "chbatey", 1), 
        "trevor",new Usuario("Trevor Sinclair", "trevor", 2)));
  }

  public Usuario lookupUser(String nombreUsuario) {
    Uninterruptibles.sleepUninterruptibly(Config.USER_DELAY, TimeUnit.MILLISECONDS);
    LOG.info("User look up complete");
    return usuarios.get(nombreUsuario);
  }
  
  public CompletableFuture<Usuario> lookupUserCompletable(String userName) {
    CompletableFuture<Usuario> cUser = new CompletableFuture<>();
    // How you can very easily wrap existing APIs with an API that returns
    // completable futures.
    executor.schedule(() -> {
                LOG.info("User lookup complete");
                cUser.complete(usuarios.get(userName));
            },
            Config.USER_DELAY, TimeUnit.MILLISECONDS);
    return cUser;
}
}
