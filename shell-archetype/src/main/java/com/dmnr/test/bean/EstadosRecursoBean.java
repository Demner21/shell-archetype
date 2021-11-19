package com.dmnr.test.bean;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

public class EstadosRecursoBean {
  private Set<String> estados;

  EstadosRecursoBean(HashSet<String> estadosRecurso) {
    this.estados = estadosRecurso;
  }

  public static EstadosRecursoBean estadosRecurso(String... estadosRecurso) {
    return new EstadosRecursoBean(Sets.newHashSet(estadosRecurso));
  }

  public boolean poseeEstadoRecurso(String estado) {
    return estados.contains(estado);
  }

  @Override
  public String toString() {
    return "EstadosRecurso [estadosRecurso=" + estados + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((estados == null) ? 0 : estados.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    EstadosRecursoBean other = (EstadosRecursoBean) obj;
    if (estados == null) {
      if (other.estados != null)
        return false;
    } else if (!estados.equals(other.estados))
      return false;
    return true;
  }
}
