# UVG_DLP_Regex-NFA-DFA

![image](https://user-images.githubusercontent.com/60373842/221439305-0e3391c1-9e72-4a1c-921d-1bbbe91b4f29.png) <br>
Facultad de Ingeniería <br>
Departamento de Ciencias de la Computación <br>
CC3071 - Diseño de Lenguajes de Programación <br>
Ma. Isabel Solano 20504 <br>

## Índice
* [Sobre el Laboratorio](https://github.com/MaIsabelSolano/UVG_DLP_Regex-NFA/edit/main/README.md#sobre-el-laboratorio)
* [Objetivos](https://github.com/MaIsabelSolano/UVG_DLP_Regex-NFA-DFA/edit/main/README.md#objetivos)
* [Dependencias](https://github.com/MaIsabelSolano/UVG_DLP_Regex-NFA-DFA/edit/main/README.md#dependencias)
* [Funciones](https://github.com/MaIsabelSolano/UVG_DLP_Regex-NFA-DFA/edit/main/README.md#funciones)
* [Modo de uso](https://github.com/MaIsabelSolano/UVG_DLP_Regex-NFA-DFA/edit/main/README.md#modo-de-uso)
* [Tecnologías](https://github.com/MaIsabelSolano/UVG_DLP_Regex-NFA-DFA/blob/main/README.md#tecnologías)

## Sobre el Laboratorio
Se buscba desarrollar una implementación de algoritmos para la construcción de autómatas finitos no deterministas a partir de una expresión regular dada. Para ello se utilizan distintos métodos de construcción, entre estos Thompson, subconjuntos, construcción directa, etc. 

## Objetivos
* Generales
  * Implementación de algoritmos básicos de autómatas finitos no deterministas y expresiones regulares.
  * Desarrollar una sección para la base de la implementación del generador de analizadores léxicos.
* Específicos
  * Implementación del algoritmo de Construcción de Subconjuntos.
  * Implementación del algoritmo de Construcción directa de AFD (DFA).
  * Implementación del algoritmo de Minimización de AFD (DFA).
  * Generación visual de los AF.
  * Implementación de la simulación de un AFN.
  * Implementación de la simulación de un AFD

## Dependencias
Para el correcto uso del programa, es necesario que se tenga instalado [GraphViz](https://github.com/nidi3/graphviz-java) ([Descarga](https://graphviz.org/download/) graphviz-7.1.0 (64-bit) EXE installer) y que dot sea una variable del sistema. 

## Funciones
* Identificación de ingreso de input incorrecto
* Conversión de una expresión infix a postfix
* Determinación del alfabeto del lenguaje a partir de la expresión ingresada
* Construcción de árbol sintáctico a partir de expresión postfix
* Construcción de AFN por Thompson y por subconjuntos basados en el árbol sintáctico
* Graficación del AFN con ayuda de GraphViz
* Construcción de AFD a partir de un AFN
* Construcción de AFD a partir de un árbol sintáctico
* Minimización de AFD
* Simulación de AFN
* Simulación de AFD

## Modo de uso 
<b>Input</b>: <br>
Expresión regular con los operadores '|', '.', '+', '-', '?' ' '(para representar épsilon) <br>
<b>Durante ejecución</b>: <br>
String a revisar si pertenece o no al autómata generado por la expresión regular

<b>Correr</b>:
* `javac src/Controller.java`
* `java src/Controller`

<b>Output<b>: 
<i>regex: 0(0|1)*0</i><br>
![AFN](https://user-images.githubusercontent.com/60373842/226206781-85cb6750-ab6a-4f1f-91ae-d01c9a7c29ee.jpg) <br>
AFN por Thompson<br>
![AFD_trans](https://user-images.githubusercontent.com/60373842/226206862-d5fa6676-f7d9-4cfc-a32d-969ac8f88b7a.jpg) <br>
AFD a partir de AFN<br>
![AFD_direct](https://user-images.githubusercontent.com/60373842/226208365-5a9060f6-9aa8-4f62-9b0e-f62b6f51e18e.jpg)<br>
AFD construcción directa<br>
![AFD_min](https://user-images.githubusercontent.com/60373842/226208387-ec44c760-9693-4a7f-bb4d-b8b599995a04.jpg)<br>
Minimización de AFD<br>

## Tecnologías
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white) ![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)
