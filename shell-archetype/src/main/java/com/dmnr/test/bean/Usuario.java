package com.dmnr.test.bean;

public class Usuario {
  private String nombre;
  private String nombreUsuario;
  private int idUsuario;

  public Usuario(String nombre, String nombreUsuario, int idUsuario) {
    super();
    this.nombre = nombre;
    this.nombreUsuario = nombreUsuario;
    this.idUsuario = idUsuario;
  }

  public String getNombre() {
    return nombre;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public int getIdUsuario() {
    return idUsuario;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + idUsuario;
    result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
    result = prime * result + ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
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
    Usuario other = (Usuario) obj;
    if (idUsuario != other.idUsuario)
      return false;
    if (nombre == null) {
      if (other.nombre != null)
        return false;
    } else if (!nombre.equals(other.nombre))
      return false;
    if (nombreUsuario == null) {
      if (other.nombreUsuario != null)
        return false;
    } else if (!nombreUsuario.equals(other.nombreUsuario))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Usuario [nombre=" + nombre + ", nombreUsuario=" + nombreUsuario + ", idUsuario=" + idUsuario + "]";
  }

}
