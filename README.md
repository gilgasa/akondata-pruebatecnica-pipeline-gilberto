![Logo de Arkon Data](https://www.arkondata.com/images/logo_arkon.svg)

# 🌐 Pipeline Spring Boot: Acceso WiFi Ciudad de México

Esta aplicación Spring Boot está diseñada para analizar y exponer datos abiertos de la Ciudad de México, específicamente los puntos de acceso WiFi disponibles en la ciudad. La aplicación está contenerizada para Docker, documentada con Swagger para facilitar su uso, y preparada para un despliegue eficiente en Kubernetes.

## 🚀 Características

- **Spring Boot**: Diseñada con este framework.
- **Base de Datos H2**: Utilizada para el almacenamiento en memoria de los datos, facilitando las pruebas y la demostración.
- **Docker 🐳**: Contenerización de la aplicación para facilitar el despliegue y la escalabilidad.
- **Swagger 📄**: Documentación de la API Rest para facilitar la interacción con el servicio.
- **Kubernetes ☸**: Configuración preparada para desplegar en Kubernetes, permitiendo un escalado y gestión eficientes de la aplicación en entornos de producción.
- 
## 🌟 Demo en Vivo

La demo de la aplicación está desplegada y dockerizada en Docker Hub con integración y despliegue continuos mediante GitHub Actions. Puedes interactuar con la demo en vivo a través de los siguientes enlaces:

- **Swagger UI**: Visualiza y prueba la API en vivo en [Dando clic aquí](https://arkondata-gilberto-prueba-bd0063b75791.herokuapp.com/swagger-ui/index.html).
- **OpenAPI Specification**: Consulta la especificación OpenAPI en formato JSON en [OpenAPI Docs](https://arkondata-gilberto-prueba-bd0063b75791.herokuapp.com/v3/api-docs).

Estos enlaces te permitirán ver la documentación interactiva de la API y realizar peticiones de prueba directamente desde tu navegador.


## 💻 Ejecución Local

Para ejecutar la aplicación localmente, asegúrate de tener Maven y Docker instalados en tu máquina. Luego, sigue estos pasos:

1. **Clonar el Repositorio**:
```bash 
git clone https://github.com/gilgasa/akondata-pruebatecnica-pipeline-gilberto.git
cd [Nombre del directorio del repositorio]
```
2. **Construir y Ejecutar con Maven**:
```bash 
mvn spring-boot:run
```

3. **Acceder a Swagger UI**:
- Navega a `http://localhost:8080/swagger-ui/index.html` para ver los endpoints disponibles y probar la API.

## 📦 Despliegue en Docker Hub

El proyecto está configurado para desplegarse automáticamente en Docker Hub mediante GitHub Actions cuando se realiza un `push` a la rama `master`.

### Pasos para el Despliegue Automático:

1. **Configurar Secretos en GitHub**:
- Ve a tu repositorio en GitHub, luego a "Settings" > "Secrets".
- Añade los secretos `DOCKERHUB_USERNAME` y `DOCKERHUB_PASSWORD` con tus credenciales de Docker Hub.

2. **Push a `master`**:
- Cada `push` a la rama `master` activará el workflow definido en `.github/workflows/docker-deploy.yml`, construyendo y desplegando automáticamente la imagen en Docker Hub.

## ☸ Preparación para Kubernetes

La aplicación incluye archivos de configuración para Kubernetes, permitiendo un despliegue sencillo en este entorno. Los archivos `Deployment` y `Service` están listos para ser aplicados a tu clúster de Kubernetes.

### Despliegue en Kubernetes:

1. **Aplicar el Deployment**:
```bash 
kubectl apply -f deployment.yaml
```

Esto creará el `Deployment` y los `Pods` asociados en tu clúster.

2. **Aplicar el Service**:
```bash 
kubectl apply -f service.yaml
```
Esto expondrá la aplicación dentro de tu clúster o, si configuraste un `LoadBalancer`, también estará accesible públicamente.

### Verificar el Despliegue:

- **Listar los Deployments**:
```bash 
kubectl get deployments
```
- **Listar los Services**:
```bash 
kubectl get services
```
- **Acceder a la Aplicación**:
- Si usaste un `LoadBalancer`, utiliza la IP externa mostrada por `kubectl get services` para acceder a la aplicación.

