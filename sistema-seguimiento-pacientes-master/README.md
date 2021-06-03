# sistema-seguimiento-pacientes
Es un sistema creado para el curso de Taller de construccion de software del octavo ciclo de la UNMSM,
El sistema es una aplicacion web la cual realiza seguimiento a pacientes en un centro de salud, tales como sus sintomas, analisis realizados, recetas, etc, ademas permite la realización de videollamadas para consultas en linea previa cita tambien agendada mediante el sistema

El sistema consiste en una api REST con un back-end java y usando la libreria javalin, y un front-end creado con vueJs y bootstrap 4

Se puede probar el sistema en la siguiente direccion web:
https://sistema-seguimiento-pacientes-self.vercel.app/

Credemciales:
Paciente: user 123456
Medico: magico magico
Administrador: admin admin


## Pasos para instalar correr el backend
```
 abrirlo con netbeans
 clic der
```
## Pasos para instalar el front por primera vez
```
 cd front-end
 npm install (solo la primera vez)
 npm install --save @opentok/client vuex (en caso no compile)
```
### Correr el front
```
  npm run serve (estando en la carpeta front-end)
```

### Compilar el front (no lo necesitan a menos que ya este terminado y quieran subirlo a un server real como heroku o idk)
```
  npm run build
```
### Activar la seguridad del server
  Para activar la seguridad necesitamos realizar 2 pasos
1) instalar un certificado https en la pc
2) habilitar en el front y back
#### Configurar modo remoto o local
esta opcion cambia la bd remota por una local y configura las cookies para que funcionen en localhost
```
  Ir a la clase SecurityConfig y cambiar la variable pruebaLocal a true
```

#### Instalar certificado https
  Primero debemos crear el certificado, para eso usaremos la herramienta [mkcert](https://github.com/FiloSottile/mkcert)
  El exe ya lo inclui en la carpeta herramientas
  Lo unico que hay que hacer es:
```
  1) abrir el cmd y ubicarse en la carpeta herramientas
  2) ejecutar los comandos:  
     mkcert -install
     mkcert -pkcs12 "localhost"
```
Esto nos creara un archivo .p12 en la carpeta herramientas, el cual debemos mover a la carperta /back-end/certificado
pueden moverlo a mano o usar el comando
```
  move localhost.p12 "..\back-end\certificado"
```

#### Configurar el server (en caso se desee correr como local )
esto solo se debe hacer si ya se instalo los certificados https o la aplicacion no funcionara
para configurar el backend, ya cree una variable magica que deshabilita toda la seguridad en 1 paso:
```
  Ir a la clase SecurityConfig y cambiar la variable habilitarSeguridad a true
```
 Para configurar el front tambien solo hay que hacer 1 paso:
```
  Ir al archivo constantes.js y cambiar 
  BASE_URL = "http://localhost:4567" por BASE_URL = "https://localhost:4567"
  (notar la s en https)
```
 Y ya al activar esos 2 ya se tendra el login y los permisos habilitados
 
 
#### Subir el back-end a heroku
Se debe subir solo el backend al git de Heroku:
hay 2 opciones:
Opcion 1:
```
  git subtree push --prefix back-end heroku master
  heroku ps:scale web=1
```
opcion 2:
```
  cd back.end
  mvn heroku:deploy
  heroku ps:scale web=1
```
#### Subir el front-end a vercel
Solo se necesita subir el proyecto a git y ya
Esta sincronizado con mi git personal ya que la opcion para usar grupos es de paga

```
 git push git@gitlab.com:Nagamine_/sistema-seguimiento-pacientes.git heroku
```
