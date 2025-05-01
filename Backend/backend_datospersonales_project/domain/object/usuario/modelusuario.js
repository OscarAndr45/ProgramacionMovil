
// domain/object/usuario/modelusuario.js

class ModelUsuario {
    constructor(
        identificacion,
        documento,
        nombres,
        apellidos,
        fechaNacimiento,
        email,
        direccion,
        imagen
    ) {
        this.identificacion = identificacion;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.direccion = direccion;
        this.imagen = imagen;
    }
}

module.exports = { ModelUsuario };