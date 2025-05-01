// apiusuario.js

const express = require('express');
const { ModelUsuario } = require('../../../domain/object/usuario/modelusuario'); 

var router = express.Router();

// GET
/**
 * @swagger
 * /operaciongetusuario:
 *   get:
 *     summary: Get all ModelUsuario
 *     responses:
 *       200:
 *         description: List of ModelUsuario
 */
router.get('/operaciongetusuario', async (req, res) => {
    try {
        // Simulación de datos de usuario
        const identificacion = "1";
        const documento = "1078456414";
        const nombres = "Oscar Andres";
        const apellidos = "Mena Valencia";
        const fechaNacimiento = "2004-03-10"; // formato YYYY-MM-DD
        const email = "oscarandresmena@gmail.com";
        const direccion = "Calle 57dd#23a16";
        const imagen = "https://www.dondevive.org/wp-content/uploads/2015/07/leon.jpg";

        const modelusuario = new ModelUsuario(
            identificacion,
            documento,
            nombres,
            apellidos,
            fechaNacimiento,
            email,
            direccion,
            imagen
        );

        res.status(200).json(modelusuario);

    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

module.exports = router;