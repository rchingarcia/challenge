# Operación Fuego de Quasar

_Esta api utiliza la ubicación de tres satélites (coordenadas x,y) que te permitirán triangular la posición de la fuente del mensaje, e intentar decifrar el mensaje recibido, en caso contrario obtendra un código 404 de que no se pudo calcular la ubicación del emisor ni el mensaje_.

## Comenzando 🚀


### Pre-requisitos 📋

_Necesitas tener instalado maven y la jdk8_

* Instalar [maven y jdk8](https://dev.to/vanessa_corredor/instalar-manualmente-maven-en-windows-10-50pb)

## Despliegue 📦

_Ejecutar el siguiente comando desde la raíz del proyecto_

```
mvn package
```
_En la ruta /target encontrará el .jar_

```
fire-quasar-operation-0.0.1-SNAPSHOT.jar
```
_Ahora puede ejecutar el .jar generado desde una consola de comandos de la siguiente forma_

```
java -jar fire-quasar-operation-0.0.1-SNAPSHOT.jar
```
## Ejecutando las pruebas ⚙️

_Ejecutar una peticion POST => /topsecret conn el siguiente body_

```
{
  "satellites": [
    {
      "name": "Kenobi",
      "distance": 300.0,
      "message": [ "este", "", "", "mensaje", "" ]
    },
    {
      "name": "skywalker",
      "distance": 310.00003177,
      "message": [ "", "es", "", "", "secreto" ]
    },
    {
      "name": "sato",
      "distance": 744.55378218,
      "message": [ "este", "", "un", "", "" ]
    }
  ]
}
```

_Obtendras como respuesta_

```
{
    "position": {
        "x": -208.71236,
        "y": -128.22597
    },
    "message": "este es un mensaje secreto "
}
```

_Ejecutar una peticion POST => /topsecret_split/kenobi con el siguiente body_

```
{
    "distance": 300.0,
     "message": [ "este", "", "", "mensaje", "" ]
}
```

_Ejecutar una peticion POST => /topsecret_split/skywaler con el siguiente body_

```
{
     "distance": 310.00003177,
     "message": [ "", "es", "", "", "secreto" ]
     
}
```

_Ejecutar una peticion POST => /topsecret_split/sato con el siguiente body_

```
{
     "distance": 744.55378218,
     "message": [ "", "es", "", "", "secreto" ]
     
}
```

_Por último ejecutar una peticion GET => /topsecret_split y obtendrá la respuesta_

```
{
    "position": {
        "x": -208.71236,
        "y": -128.22597
    },
    "message": "este es un mensaje secreto "
}
```

