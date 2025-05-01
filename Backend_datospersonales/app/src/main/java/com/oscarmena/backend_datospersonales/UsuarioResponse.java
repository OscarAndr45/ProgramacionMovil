package com.oscarmena.backend_datospersonales;

public class UsuarioResponse {
    private String identificacion;
    private String documento;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String email;
    private String direccion;
    private String imagen;

    // Getters
    public String getIdentificacion() { return identificacion; }
    public String getDocumento() {
        return documento; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getEmail() { return email; }
    public String getDireccion() { return direccion; }
    public String getImagen() { return imagen; }
}
