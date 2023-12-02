$(document).ready(function(){
verificarToken();
});

function verificarToken(){
const token = localStorage.getItem('token');
if(token){
window.location.href = "index.html";
}
}

async function registrarUsuario() {
let datos = {};

datos.nombre = document.getElementById('txtNombre').value;
datos.apellido = document.getElementById('txtApellido').value;
datos.email = document.getElementById('txtEmail').value;
datos.password = document.getElementById('txtPass').value;
const confirmacion = document.getElementById('txtDoublePass').value;

if (datos.nombre.trim().length === 0 || datos.apellido.trim().length === 0) {
    alert("Debe llenar todos los campos para registrarse");
    return
}

if(datos.email.length < 10 || !datos.email.match(/@/)){
alert("Por favor, escriba un correo electronico válido");
return
}

if(datos.password != confirmacion){
alert('Por favor, verifíque que las contraseñas coincidan.')
return
}

if(datos.password.length < 8 || !datos.password || datos.password.match(/\d/g).length < 3){
alert("La contraseña debe tener un minimo de 8 caracteres y 3 números");
document.getElementById('txtPass').value = "";
datos.password = document.getElementById('txtPass').value;
document.getElementById('txtDoublePass').value = "";
datos.confirmacion = document.getElementById('txtDoublePass').value;

return;
}

const registrar = await fetch('api/usuariodos',{
method: 'POST',
headers: {
'Accept': 'application/json',
 'Content-Type': 'application/json'
},
body: JSON.stringify(datos)
});
alert("La cuenta se ah creado exitosamente");
window.location.href="login.html";
}