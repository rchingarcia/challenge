# Operación Fuego de Quasar
## Comenzando 🚀


### Pre-requisitos 📋

_Necesitas tener instalado maven y la jdk8_

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
      "message": [
        "este",
        "",
        "",
        "mensaje",
        ""
      ]
    },
    {
      "name": "skywalker",
      "distance": 310.00003177,
      "message": [
        "",
        "es",
        "",
        "",
        "secreto"
      ]
    },
    {
      "name": "sato",
      "distance": 744.55378218,
      "message": [
        "este",
        "",
        "un",
        "",
        ""
      ]
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


