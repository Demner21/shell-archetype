package com.dmnr.test.bean;

public class Resultado {
  EstadosRecursoBean estadosRecurso;
  Recurso recurso;

  public Resultado(EstadosRecursoBean estadosRecurso, Recurso recurso) {
    super();
    this.estadosRecurso = estadosRecurso;
    this.recurso = recurso;
  }

  public EstadosRecursoBean getEstadosRecurso() {
    return estadosRecurso;
  }

  public Recurso getRecurso() {
    return recurso;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((estadosRecurso == null) ? 0 : estadosRecurso.hashCode());
    result = prime * result + ((recurso == null) ? 0 : recurso.hashCode());
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
    Resultado other = (Resultado) obj;
    if (estadosRecurso == null) {
      if (other.estadosRecurso != null)
        return false;
    } else if (!estadosRecurso.equals(other.estadosRecurso))
      return false;
    if (recurso == null) {
      if (other.recurso != null)
        return false;
    } else if (!recurso.equals(other.recurso))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Resultado [estadosRecurso=" + estadosRecurso + ", recurso=" + recurso + "]";
  }

}
