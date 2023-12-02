$(document).ready(function(){
nombreDeUsuario();
});

function verificarToken(){
const token = localStorage.getItem('token');
if(!token){
window.location.href = 'login.html';
    }
}

function cerrarSesion(){
localStorage.removeItem('token');
localStorage.removeItem('email');
window.location.href = 'login/html';
}

