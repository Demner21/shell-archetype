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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmnr.test.bean.EstadosRecurso;
import com.dmnr.test.service.EstadosRecursoService;

public class Application {
  private static final Logger LOG = LoggerFactory.getLogger(Application.class);
  
  EstadosRecursoService estadosRecursoService= EstadosRecursoService.estadosRecursoService();
  
  public static void main(String[] args) {
    LOG.info("iniciando main");
    var app= new Application();
    var estadoRecursoEncontrado = app.validarEstadoRecurso(1);
    LOG.info("{}" , estadoRecursoEncontrado);
  }
  
  EstadosRecurso validarEstadoRecurso(int userId) {
    return estadosRecursoService.estadosRecurso(userId);
  }
  
}
