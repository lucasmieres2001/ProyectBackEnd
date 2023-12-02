$(document).ready(function(){
verificarToken();
})

function verificarToken(){
const token = localStorage.getItem('token');
if(!token){
 window.location.href = "login.html";
}
}

async function subirImg(){
const comprobar = document.querySelector('#txtImg');
if(!(comprobar.files.length > 0)){
    alert("Debe subir una imagen para continuar");
    return
}
let tipoImagen = comprobar.files[0].type;
let resultado = comprobar.files[0].type;
//let resultado = "." + tipoImagen2.toUpperCase().replace(/\//g, '_');
let SoloPng = tipoImagen.substring(tipoImagen.lastIndexOf("/") + 1);
const formData = new FormData();
let email = {email: localStorage.getItem('email')}

formData.append('email', new Blob([JSON.stringify(email)], { type: 'application/json' }));
formData.append('typeImg',SoloPng);
formData.append('img',comprobar.files[0]);
formData.append('typeReal', resultado);

try{
const queryImg = await fetch('api/img',{
method: 'POST',
headers: {'Authorization': localStorage.token},
body: formData
});
const respuesta = await queryImg.json();
if(respuesta != null){
        window.location.href = "index.html";
    }
}catch(error){console.error("Hubo un error: " + error)}


}