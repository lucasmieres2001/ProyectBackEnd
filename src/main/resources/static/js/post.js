$(document).ready(function(){
validarToken();
nombreDeUsuario();
queryPost();
})

function nombreDeUsuario(){
document.getElementById('nombreDeUsuario').outerHTML = localStorage.email;
}

function cerrarSesion(){
localStorage.removeItem('token');
localStorage.removeItem('email');
window.location.href = 'login/html';
if (typeof respuesta !== 'undefined' && respuesta !== null) {
    URL.revokeObjectURL(respuesta);
  }
}


function validarToken(){
const token = localStorage.getItem('token');
if(!token){window.location.href = "login.html"}
}

async function queryPost(){
let email = {email: localStorage.getItem('email')}
const formData = new FormData();
formData.append('email',new Blob([JSON.stringify(email)], {type: 'application/json'}));
const query = await fetch("api/getPost",{
method: 'POST',
headers:        {
        'Authorization': localStorage.token
        },
        body: formData
    });
    const respuesta = await query.blob();
    if(respuesta != null){
    const imageUrl = URL.createObjectURL(respuesta);
      document.querySelector('#imgPost').src = imageUrl;
    }else{
    console.log("Hubo un error");
    }

}