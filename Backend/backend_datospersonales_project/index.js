// index.js

const express = require('express');
const bodyParser = require('body-parser');
const swaggerUi = require('swagger-ui-express');
const cors = require('cors');
const swaggerSpecificationUsuario = require('./swagger/object/usuario/swaggerspecificationusuario');
const apiUsuario = require('./application/object/usuario/apiusuario');

var app = express();

app.use(cors());

app.use(bodyParser.json());

app.use('/api-doc', swaggerUi.serve, swaggerUi.setup(swaggerSpecificationUsuario));

app.use(apiUsuario);

const PORT = process.env.PORT || 8067;

app.listen(PORT, () => {
	console.log(``);
	console.log(`----------------------------------------------------------------------------------------------------------------------`);
	console.log(``);	
    console.log(`Server running on port ${PORT}`);
	console.log(`Swagger docs available at http://localhost:${PORT}/api-doc`);
    console.log(`Get operation available at http://localhost:${PORT}/operaciongetusuario`);
	console.log(``);
	console.log(`----------------------------------------------------------------------------------------------------------------------`);
	console.log(``);
});