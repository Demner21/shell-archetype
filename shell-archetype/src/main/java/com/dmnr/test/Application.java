/*-
 * =====LICENSE-START=====
 * Java 11 Application
 * ------
 * Copyright (C) 2020 - 2021 Organization Name
 * ------
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =====LICENSE-END=====
 */

package com.dmnr.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmnr.test.bean.EstadosRecursoBean;
import com.dmnr.test.bean.Recurso;
import com.dmnr.test.bean.Resultado;
import com.dmnr.test.bean.Usuario;
import com.dmnr.test.service.EstadosRecursoService;
import com.dmnr.test.service.RecursoService;
import com.dmnr.test.service.UsuarioService;

public class Application {
  private static final Logger LOG = LoggerFactory.getLogger(Application.class);
  
  EstadosRecursoService estadosRecursoService= EstadosRecursoService.estadosRecursoService();
  
  UsuarioService usuarioService= UsuarioService.usuarioService();
  
  RecursoService recursoService= RecursoService.recursoService();
  
  public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
    LOG.info("iniciando main");
    var app= new Application();
    var estadoRecursoEncontrado = app.validarEstadoRecurso(1);
    LOG.info("{}" , estadoRecursoEncontrado);
    var estadoRecursoEncontradoFast = app.validarEstadoRecursoFast(1);
    LOG.info("{}" , estadoRecursoEncontradoFast);
    var resultadoFast = app.validarResultadoFast("chbatey","RECURSO_1");
    LOG.info("{}" , resultadoFast);
  }
  
  public EstadosRecursoBean validarEstadoRecurso(int userId) {
    LOG.info("iniciando validarEstadoRecurso");
    return estadosRecursoService.estadosRecurso(userId);
  }
  
  public EstadosRecursoBean validarEstadoRecursoFast(int userId) throws InterruptedException, ExecutionException, TimeoutException {
    LOG.info("iniciando validarEstadoRecursoFast");
    Future<EstadosRecursoBean> fResultado = se.submit(()-> {
        Future<EstadosRecursoBean> fEstadoRecurso = se.submit(()-> estadosRecursoService.estadosRecurso(userId));
        return fEstadoRecurso.get();
    });
    return fResultado.get(500, TimeUnit.MILLISECONDS);
  }
  
  public Resultado validarResultadoFast(String nombreUsuario,String nombreRecurso) throws InterruptedException, ExecutionException, TimeoutException {
    LOG.info("iniciando validarResultadoFast");
    Future<Resultado> fResultado = se.submit(()-> {
      Future<Recurso> fRecurso = se.submit(()-> recursoService.lookupRecurso(nombreRecurso));
      Usuario usuario = usuarioService.lookupUser(nombreUsuario);
      EstadosRecursoBean estadosRecurso = estadosRecursoService.estadosRecurso(usuario.getIdUsuario());
      Recurso recurso = fRecurso.get();
      return new Resultado(estadosRecurso, recurso);
    });
    
    return fResultado.get(1500, TimeUnit.MILLISECONDS);
  }

  private final ScheduledExecutorService se = Executors.newScheduledThreadPool(5);
  
}
