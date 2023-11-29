$(document).ready(function() {
//Funcion JQuery
})

async function iniciarSesion(){
let datosLogin = {}
datosLogin.email = document.querySelector('#email').value;
datosLogin.password = document.querySelector('#pass').value;

if(datosLogin.email.length < 10 || !datosLogin.email.match(/@/)){
alert("Por favor, escriba un correo electronico válido");
return
}

const Query = await fetch ('api/login',{
method: 'POST',
headers:{
'Accept': 'application/json',
'Content-Type': 'application/json'
},
body: JSON.stringify(datosLogin)
});
try{
const infoUsuario = await Query.text();

if(infoUsuario != "error"){
localStorage.token = infoUsuario;
localStorage.email = datosLogin.email;
window.location.href="usuarios.html";
}else{
alert("Sus datos no coinciden, por favor vuelva a intentarlo");
document.querySelector('.text-center h1').innerText = "Por favor intentelo de nuevo."

    }
}catch(error){console.error("Error al analizar la respuesta JSON",error);}

}

