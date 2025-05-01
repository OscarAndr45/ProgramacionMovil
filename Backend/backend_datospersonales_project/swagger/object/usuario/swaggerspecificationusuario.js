// swaggerspecificationpais.js

const swaggerJSDoc = require('swagger-jsdoc');

const options = {
    definition: {
        openapi: '3.0.0',
        info: {
            title: 'Usuario API',
            version: '1.0.0',
            description: 'BackEnd Datos Personales Project'
        }
    },
    apis: ['./application/object/usuario/*.js'], 
};

const swaggerSpec = swaggerJSDoc(options);

module.exports = swaggerSpec;