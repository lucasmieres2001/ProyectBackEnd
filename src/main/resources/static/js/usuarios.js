//Llamamos nuestra tabla a través de su id;
verificarToken();
$(document).ready(function() {
    cargarUsuarios();
    nombreDeUsuario();
  $('#usuarios').DataTable();
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

function nombreDeUsuario(){
document.getElementById('nombreDeUsuario').outerHTML = localStorage.email;
}

//Iniciamos una función asincrónica para conectar javaScript con los datos de nuestro controlador java (AJAX);
async function cargarUsuarios(){
  const respuesta = await fetch('api/usuariodos', {
    method: 'GET',
    headers: getHeaders()
  });
  //Convertimos la solicitud adquirida con nuestro fetch a formato json;
  const usuarios = await respuesta.json();


let ListadoHtml = '';
// Acá vamos a ir asignando cada dato que llegue de nuestro servidor al HTML;
  for (elemento of usuarios){
  let botonEliminar = '<a href="#" onClick="eliminarUsuario('+elemento.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
  let telefono = elemento.tel == null ? '-' : elemento.tel;
  let tablaUsuario = '<tr><td>'+ elemento.id+'</td><td> ' + elemento.nombre+' '+ elemento.apellido+' </td> <td>'+elemento.email +'</td><td>'+ telefono +'</td> <td>'+botonEliminar+'</td></tr>'

  ListadoHtml += tablaUsuario;
  }



  console.log(usuarios);

// la selección de la ID de nuestra tabla y el tbody donde se contendrá nuestros datos, sera igual al HTML que contiene la variable 'ListadoHtml'
//PD:'ListadoHtml' es una variable vacía, solo sirve como incremento de las tablas que 'tablaUsuario' genéra;
document.querySelector('#usuarios tbody').outerHTML = ListadoHtml;
}

function getHeaders(){
    return {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': localStorage.token
            };
    }

async function eliminarUsuario(id){
if(confirm('¿Seguro quieres eliminar este usuario? (Su cuenta se borrará permanentemente).')){
const respuesta = await fetch('api/usuario/'+id, {
    method: 'DELETE',
    headers: getHeaders()
        });
        location.reload()
    }
}

