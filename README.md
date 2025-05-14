# 🌤️ Tele-Weather (App Android)

**Tele-Weather** es una aplicación móvil desarrollada en Android Studio como parte del laboratorio del curso *Servicios y Aplicaciones para IoT* (2025-1). Su objetivo es permitir a los usuarios consultar pronósticos del clima en distintas fechas, usando datos obtenidos desde la API de [WeatherAPI](https://www.weatherapi.com/).

---

## 🧩 Funcionalidades principales

- 🔍 Búsqueda de locaciones por nombre o ID
- 📅 Pronóstico para los próximos 14 días (modo estándar)
- 📆 Pronóstico para fechas futuras (más de 14 días hasta 300 días)
- ⛅ Visualización por hora, condición, temperatura, humedad, probabilidad de lluvia, etc.
- 📱 Integración con acelerómetro: agita el teléfono para borrar los pronósticos actuales
- 🔄 Navegación entre fragmentos (`Location`, `Forecaster`, `Future`)
- 🌙 Interfaz intuitiva y visualmente agradable

---

## 🤖 Uso de Inteligencia Artificial (IA)

Durante el desarrollo del presente proyecto se hizo uso responsable de **ChatGPT (OpenAI)** como **herramienta de apoyo**, especialmente para:

- Optimizar estructuras XML de diseño
- Estructurar código Java en fragmentos
- Corregir errores de navegación o sensores
- Sugerir mejoras visuales y funcionales

### 📌 Ejemplos de prompts utilizados

```plaintext
→ ¿Cómo puedo limpiar el backstack al navegar entre fragmentos con BottomNavigationView?
→ Haz que el botón de "Atrás" del dispositivo salga de AppActivity y no regrese al fragmento anterior
→ Detectar agitación con acelerómetro y mostrar diálogo de confirmación en Android
→ Mejora visualmente esta pantalla inicial usando ConstraintLayout y centrado
→ Corrige el error 400 en Retrofit al usar id:1802603 como parámetro para WeatherAPI
