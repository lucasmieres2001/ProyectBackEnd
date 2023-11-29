validarToken();
$().ready(function(){

})

function validarToken(){
const token = localStorage.getItem('token');
    if(!token){
    window.location.href= "login.html";
    }
}

async function enviarNumero(){
let dato = {}
dato.tel = document.querySelector('#TelId').value
dato.email = localStorage.getItem('email');
const query = await fetch('api/tel',{

method: 'POST',
headers: {
'Accept': 'application/json',
'Content-Type':'application/json',
'Authorization': localStorage.token
},
body: JSON.stringify(dato)
});
const respuesta = await query.text();

if(respuesta){
alert("Número de teléfono registrado");
window.location.href = "usuarios.html";
}else{
alert("Error al registrar su número de telefono");
return
}
}