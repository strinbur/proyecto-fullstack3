# Frontend React

## Descripción
Aplicación frontend en React que consume el BFF (`ms-bff`) para manejar autenticación, usuarios y productos. La UI mantiene sesión con JWT y envía automáticamente el token a todas las peticiones al BFF.

## Stack Tecnológico
- **Framework**: React 19
- **Lenguaje**: TypeScript
- **Bundler**: Vite
- **Ruteo**: React Router DOM 7
- **HTTP**: Axios
- **Notificaciones**: React Hot Toast
- **Linting**: ESLint

## Dependencias
- `react`, `react-dom` — UI principal
- `react-router-dom` — navegación entre páginas
- `axios` — consumo de API y configuración de interceptors
- `react-hot-toast` — notificaciones de éxito/error
- `@vitejs/plugin-react` — plugin React para Vite
- `typescript` — tipado estático
- `eslint` — análisis y reglas de calidad de código

## Patrones de Diseño
- **Context API**: `AuthContext` y `AuthProvider` para estado de autenticación.
- **Protected Routes**: `ProtectedRoute.tsx` protege rutas que requieren login.
- **Interceptor HTTP**: Axios intercepta requests/responses para adjuntar el token y manejar errores globales.
- **Separation of Concerns**: llamadas API separadas en `authApi.ts` e `inventoryApi.ts`.

## Integración con el BFF
- El frontend se conecta a `http://localhost:8080/bff` desde `src/api/api.ts`.
- No llama directamente a `ms-login` ni a `ms-inventory`.
- Rutas usadas por el frontend:
  - `POST /bff/login`
  - `POST /bff/login/register`
  - `POST /bff/login/admin`
  - `PUT /bff/login/{id}`
  - `GET /bff/login`
  - `DELETE /bff/login/{id}`
  - `GET /bff/inventory`
  - `GET /bff/inventory/codigo/{codigo}`
  - `POST /bff/inventory`
  - `PUT /bff/inventory/codigo/{codigo}`
  - `DELETE /bff/inventory/codigo/{codigo}`

## Cómo maneja la autenticación y los tokens
- El login se realiza en `POST /bff/login`.
- El BFF devuelve `{ token, usuario }`.
- `AuthProvider` guarda el `token` y el `usuario` en `localStorage`.
- En cada petición, `src/api/api.ts` agrega `Authorization: Bearer <token>`.
- Al recibir `401`, el interceptor limpia `localStorage` y redirige a `/login`.

## Estructura del Proyecto
```
src/
+-- api/
¦   +-- api.ts                 # Configura Axios, baseURL e interceptores
+-- components/
¦   +-- Footer.tsx             # Pie de página
¦   +-- Navbar.tsx             # Barra de navegación con menú de usuario
¦   +-- ProtectedRoute.tsx     # Protege rutas que requieren login
+-- features/
¦   +-- auth/
¦   ¦   +-- authApi.ts         # Llamadas API de autenticación
¦   ¦   +-- AuthContext.tsx    # Contexto y tipos de autenticación
¦   ¦   +-- AuthProvider.tsx   # Proveedor de contexto de usuario
¦   +-- inventory/
¦       +-- inventoryApi.ts    # Llamadas API de inventario
+-- pages/
¦   +-- Dashboard/
¦   ¦   +-- Dashboard.css
¦   ¦   +-- Dashboard.tsx      # Panel de administración
¦   +-- Home/
¦   ¦   +-- Home.css
¦   ¦   +-- Home.tsx           # Página de bienvenida
¦   +-- Login/
¦   ¦   +-- Login.css
¦   ¦   +-- Login.tsx          # Formulario de inicio de sesión
¦   +-- Products/
¦   ¦   +-- Products.css
¦   ¦   +-- Products.tsx       # Lista y búsqueda de productos
¦   +-- Profile/
¦   ¦   +-- Profile.css
¦   ¦   +-- Profile.tsx        # Perfil de usuario y edición
¦   +-- Register/
¦       +-- Register.css
¦       +-- Register.tsx       # Formulario de registro
+-- App.tsx                    # Enrutado principal de la app
+-- main.tsx                   # Punto de entrada de React
+-- index.css                  # Estilos globales
+-- assets/                    # Recursos estáticos
```

## Funcionalidades
- **Autenticación**: login, registro, logout y persistencia de sesión.
- **Gestión de usuario**: edición de perfil y visualización de datos.
- **Inventario**: visualización, búsqueda por categoría y consumo de productos.
- **Administración**: acceso a dashboard según rol y manejo de usuarios.
- **Protección de rutas**: páginas seguras solo accesibles con sesión activa.

## Ejecución local
### Prerrequisitos
- Node.js instalado
- `ms-bff` levantado en `http://localhost:8080`

### Instalar dependencias
```bash
npm install
```

### Ejecutar en desarrollo
```bash
npm run dev
```

### Construir la aplicación
```bash
npm run build
```

### Vista previa de producción
```bash
npm run preview
```

### Linting
```bash
npm run lint
```

## Notas
- El frontend está configurado para usar el BFF en `http://localhost:8080/bff`.
- No se documentan variables de entorno en este README.
- Si cambias el puerto del BFF, actualiza `src/api/api.ts`.
- El token JWT se maneja en el frontend solo para enviar la cabecera `Authorization`.
