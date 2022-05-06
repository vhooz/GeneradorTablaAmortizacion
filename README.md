# Generador de Tablas de Amortización de Prestamos.

_Programa que calcular y genera la tabla de amortización de un prestamo._


### Pre-requisitos 📋


```
* Java IDE
* Java JDK >= 1.8
* Maven
```

### Correr el programa desde el archivo .jar 🔧

_Abrir la terminal o CMD desde el directorio **target** que contiene el el archivo .jar y correr el siguiente comando:_

```
java -jar Prueba_Practica_Victor-1.0.jar  
```
### Correr el programa desde el IDE 🔧

_El siguiente proceso utiliza maven para instalar las dependencias del projecto, limpiar y compilar el codigo fuente._


* Abrir el IDE de preferencia.
* Import carpeta del projecto como projecto **Maven**.
* Asegurar que la version de java en el pom.xml sea la compatible con la maquina.
* Correr el comando mvn clean para limpiar la carpeta target con el codigo fuente compilado.
* Correr mvn install para installar las dependencias del projecto. Esto también se asegura de compilar el projecto.
* Ir a src/main/java/com.bgeneral/app/App.java y correr el main de la clase **_App.java_**
![](/Users/victor/Documents/GitHub/GeneradorTablaAmortizacion/Screen Shot 2022-05-05 at 11.10.18 PM.png)* 

## Build Package .jar con Maven 📦

* Correr el comando mvn clean para limpiar la carpeta target con el codigo fuente compilado.
* Correr mvn package para generar el archivo ejecutable .jar dentro de la carpeta de target
* Ejectuar el archivo .jar desde la terminal con el comando 
```
  java -jar Prueba_Practica_Victor-1.0.jar
```

## Construido con 🛠️

* [Maven](https://maven.apache.org/) - Gestionador de dependencias y construcción de projectos Java

## Actualizaciones pendientes

- [ ] Unit testing con JUnit
