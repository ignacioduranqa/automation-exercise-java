# Automatización de Pruebas Web | Selenium Java

Proyecto de automatización de pruebas funcionales sobre [Automation Exercise](https://automationexercise.com/), desarrollado con **Java, Selenium WebDriver, JUnit 5 y Maven**, aplicando el patrón de diseño **Page Object Model (POM)**.

El objetivo es validar distintos flujos de una aplicación de comercio electrónico mediante pruebas automatizadas, incluyendo autenticación, registro de usuarios, catálogo de productos, carrito de compras y proceso de checkout.

Este proyecto forma parte de mi portafolio personal de QA y representa la aplicación práctica de conocimientos en automatización de pruebas.

## Tecnologías utilizadas

- **Java 21:** lenguaje de programación.
- **Selenium WebDriver:** automatización de navegadores.
- **JUnit 5:** ejecución de pruebas y validaciones.
- **Maven:** gestión de dependencias y ejecución.
- **Google Chrome / ChromeDriver:** navegador de pruebas.
- **Page Object Model (POM):** organización y reutilización del código.
- **Visual Studio Code:** entorno de desarrollo.

## Funcionalidades automatizadas

| Módulo | Validaciones |
|---|---|
| Página principal | Carga y visualización de elementos |
| Registro | Flujo de registro y validación de datos |
| Inicio de sesión | Autenticación de usuarios |
| Catálogo | Visualización de productos |
| Búsqueda | Resultados positivos y negativos |
| Carrito | Productos, precios y eliminación |
| Checkout | Validaciones de acceso y proceso de compra |
| Compra E2E | Registro, carrito, checkout y confirmación de pedido |
| Cantidades | Cantidad de unidades y cálculo de totales |
| Categorías | Navegación por categorías y subcategorías |
| Contacto | Validaciones del formulario |

## Arquitectura del proyecto

El proyecto utiliza el patrón **Page Object Model (POM)**, separando la lógica de interacción con la aplicación de los casos de prueba.

```text
automation-exercise-java/
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── qa/
│                   └── automation/
│                       ├── base/
│                       │   └── BaseTest.java
│                       ├── models/
│                       │   └── ProductData.java
│                       ├── pages/
│                       │   ├── CartPage.java
│                       │   ├── CategoriesPage.java
│                       │   ├── CheckoutPage.java
│                       │   ├── ContactPage.java
│                       │   ├── HomePage.java
│                       │   ├── LoginPage.java
│                       │   ├── PaymentPage.java
│                       │   ├── ProductDetailPage.java
│                       │   ├── ProductsPage.java
│                       │   └── SignupPage.java
│                       ├── utils/
│                       └── [Clases de pruebas JUnit]
├── pom.xml
└── README.md
```

## Características de la automatización

- Separación de responsabilidades mediante Page Object Model.
- Uso de esperas explícitas con WebDriverWait.
- Validaciones mediante assertions de JUnit.
- Selección dinámica de productos del catálogo.
- Identificación de productos mediante sus IDs obtenidos de la aplicación.
- Verificación de precios, cantidades y cálculos de totales.
- Generación de correos únicos para pruebas de registro.
- Ejecución de escenarios end-to-end.
- Configuración centralizada del navegador en BaseTest.

## Requisitos previos

Para ejecutar las pruebas se necesita:

- Java JDK 21.
- Apache Maven.
- Google Chrome.
- Git para clonar el repositorio.

## Instalación y ejecución

**1. Clonar el repositorio**

```bash
git clone URL_DEL_REPOSITORIO
```

**2. Acceder al proyecto**

```bash
cd automation-exercise-java
```

**3. Ejecutar todas las pruebas**

```bash
mvn clean test
```

Para ejecutar la suite secuencialmente:

```bash
mvn clean test "-Djunit.jupiter.execution.parallel.enabled=false" "-DforkCount=1" "-DreuseForks=true"
```

**4. Ejecutar una prueba específica**

Por ejemplo, para validar las cantidades y totales del carrito:

```bash
mvn clean test "-Dtest=ProductQuantityTest"
```

## Resultados de ejecución

Última ejecución completa: **9 de noviembre de 2026**.

| Métrica | Resultado |
|---|---|
| Pruebas ejecutadas | 15 |
| Pruebas exitosas | 15 |
| Fallos | 0 |
| Errores | 0 |
| Pruebas omitidas | 0 |
| Tiempo total | 2 minutos y 22 segundos |
| Estado | BUILD SUCCESS |

Estos resultados corresponden a una ejecución local de la suite y no garantizan que futuras ejecuciones estén libres de fallos.

## Próximas mejoras

- Ampliar la cobertura del formulario de contacto, incluyendo archivos adjuntos.
- Incorporar reportes de ejecución automatizados.
- Evaluar la integración con GitHub Actions para ejecutar las pruebas mediante CI.

## Aplicación utilizada

[Automation Exercise](https://automationexercise.com/)

Sitio de práctica para automatización de pruebas web.

---

**Proyecto personal de QA Automation desarrollado con fines de aprendizaje y portafolio profesional.**