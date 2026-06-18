# Frontend React

## Descripción
Aplicación frontend en React que consume el BFF (`ms-bff`) para manejar autenticación, usuarios y productos. La UI usa `AuthContext` para mantener el usuario logueado y agrega automáticamente el token JWT a las peticiones.

---

## Diagrama C3 - Componentes del Frontend

### C3 - Componentes
```
┌──────────────────────────────────────────────────────────────────────┐
│              Frontend (React + TypeScript - Port 5173)               │
│                                                                      │
│  ┌────────────────────────────────────────────────────────────────┐ │
│  │                  React Router (App.tsx)                      │ │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐   │ │
│  │  │ Home Page    │  │ Auth Pages   │  │ Dashboard Pages  │   │ │
│  │  │              │  │ - Login      │  │ - Products       │   │ │
│  │  │              │  │ - Register   │  │ - Cart           │   │ │
│  │  │              │  │ - Profile    │  │ - Profile        │   │ │
│  │  └──────────────┘  └──────────────┘  └──────────────────┘   │ │
│  └─────────────────┬────────────────────────────────────────────┘ │
│                    │                                              │
│  ┌─────────────────▼────────────────────────────────────────────┐ │
│  │               Features Layer (Context + API)                 │ │
│  │  ┌──────────────────┐  ┌──────────────────────────────────┐ │ │
│  │  │  auth/                                                 │ │ │
│  │  │  - AuthProvider.tsx (manages login state)             │ │ │
│  │  │  - AuthContext.tsx (exposes auth methods)             │ │ │
│  │  │  - authApi.ts (calls /bff/login endpoints)            │ │ │
│  │  └──────────────────┘  ├─ inventory/                     │ │ │
│  │                         │ - inventoryApi.ts              │ │ │
│  │                         │ (calls /bff/inventory)         │ │ │
│  │                         ├─ cart/                         │ │ │
│  │                         │ - CartContext.tsx              │ │ │
│  │                         │ - cartApi.ts                   │ │ │
│  │                         │ (calls /bff/cart)              │ │ │
│  │                         └─ order/                        │ │ │
│  │                           - orderApi.ts                  │ │ │
│  │                           (calls /bff/order)             │ │ │
│  │  ┌──────────────────────────────────────────────────────┐ │ │
│  │  │  api/                                               │ │ │
│  │  │  - api.ts (Axios instance + interceptors)           │ │ │
│  │  │    * Adds Authorization: Bearer {token} header      │ │ │
│  │  │    * Reads token from localStorage                 │ │ │
│  │  │    * Handles errors globally                       │ │ │
│  │  └──────────────────────────────────────────────────────┘ │ │
│  └─────────────────┬────────────────────────────────────────────┘ │
│                    │                                              │
│  ┌─────────────────▼────────────────────────────────────────────┐ │
│  │                   Components (UI Layer)                      │ │
│  │  ┌──────────────────┐  ┌──────────────────────────────────┐ │ │
│  │  │ Navbar.tsx       │  │ ProtectedRoute.tsx              │ │ │
│  │  │ - Display user    │  │ - Checks if user is logged in │ │ │
│  │  │ - Logout button   │  │ - Redirects to login if needed  │ │ │
│  │  │ - Navigation menu │  │ - Renders protected page      │ │ │
│  │  └──────────────────┘  └──────────────────────────────────┘ │ │
│  │  ┌──────────────────────────────────────────────────────────┐ │ │
│  │  │ Header.tsx, Footer.tsx                                 │ │ │
│  │  │ - Layout components                                    │ │ │
│  │  │ - Page structure                                       │ │ │
│  │  └──────────────────────────────────────────────────────────┘ │ │
│  └─────────────────┬────────────────────────────────────────────┘ │
│                    │                                              │
│  ┌─────────────────▼────────────────────────────────────────────┐ │
│  │              Utils & Helpers                                 │
│  │  - format.ts (Date, currency formatting)                   │ │
│  │  - validators.ts (Form validation)                          │ │
│  │  - constants.ts (App constants)                             │ │
│  └────────────────────────────────────────────────────────────┘ │
│                    │                                              │
│  ┌─────────────────▼────────────────────────────────────────────┐ │
│  │           LocalStorage Management                            │ │
│  │  - Persist user token                                        │ │
│  │  - Persist user info (name, email, role)                    │ │
│  │  - Persist UI preferences (theme, language)                 │ │
│  └────────────────────────────────────────────────────────────┘ │
└──────────────────────────────────────────────────────────────────────┘
                            │
                   HTTP/JSON │ (Axios)
                 Authorization│ Bearer {token}
                            │
          ┌─────────────────▼──────────────┐
          │   BFF (Port 8080)              │
          │   /bff/login                   │
          │   /bff/inventory               │
          │   /bff/cart                    │
          │   /bff/order                   │
          └────────────────────────────────┘
```

---
- **Framework**: React 19
- **Lenguaje**: TypeScript
- **Bundler**: Vite
- **Ruteo**: React Router DOM 7
- **HTTP**: Axios
- **Notificaciones**: React Hot Toast
- **Linting**: ESLint

## Dependencias Principales
- `react`, `react-dom`: UI principal
- `react-router-dom`: Navegación de rutas
- `axios`: Consumo de APIs y manejo de interceptors
- `react-hot-toast`: Mensajes de éxito/error
- `@vitejs/plugin-react`: Plugin React para Vite
- `typescript`: Tipado estático
- `eslint`: Análisis de código

## Estructura Principal
- `src/api/api.ts`: Configuración global de Axios con `baseURL` al BFF y manejo de token JWT.
- `src/features/auth/`: Autenticación del frontend.
  - `AuthProvider.tsx`: Provee el contexto de usuario y las funciones `loginUser` / `logout`.
  - `AuthContext.tsx`: Tipos y definición del contexto.
  - `authApi.ts`: Llamadas a los endpoints de login, registro y administración de usuarios.
- `src/features/inventory/inventoryApi.ts`: Llamadas a los endpoints del inventario.
- `src/components/ProtectedRoute.tsx`: Componente de protección de rutas basado en usuario logueado.
- `src/pages/`: Páginas de la aplicación (`Home`, `Login`, `Register`, `Dashboard`, `Profile`, `Products`).

## Arquitectura del Proyecto
```
src/
├── api/
│   └── api.ts                 # Configuración global de Axios y interceptores
├── components/
│   ├── Footer.tsx             # Pie de página
│   ├── Navbar.tsx             # Barra de navegación con menú de usuario
│   └── ProtectedRoute.tsx     # Componente para proteger rutas autenticadas
├── features/
│   ├── auth/
│   │   ├── authApi.ts         # Llamadas API para autenticación
│   │   ├── AuthContext.tsx    # Contexto y tipos de autenticación
│   │   └── AuthProvider.tsx   # Proveedor de contexto de usuario
│   └── inventory/
│       └── inventoryApi.ts    # Llamadas API para inventario
├── pages/
│   ├── Dashboard/
│   │   ├── Dashboard.css
│   │   └── Dashboard.tsx      # Panel de administración
│   ├── Home/
│   │   ├── Home.css
│   │   └── Home.tsx           # Página de inicio
│   ├── Login/
│   │   ├── Login.css
│   │   └── Login.tsx          # Página de login
│   ├── Products/
│   │   ├── Products.css
│   │   └── Products.tsx       # Lista de productos
│   ├── Profile/
│   │   ├── Profile.css
│   │   └── Profile.tsx        # Perfil de usuario
│   └── Register/
│       ├── Register.css
│       └── Register.tsx       # Página de registro
├── App.tsx                    # Componente principal con rutas
├── main.tsx                   # Punto de entrada de React
├── index.css                  # Estilos globales
└── assets/                    # Recursos estáticos (imágenes, etc.)
```

## Cómo maneja la autenticación y los tokens
- El frontend hace login en el endpoint `POST /bff/login`.
- El BFF devuelve un objeto con `{ token, usuario }`.
- `AuthProvider` guarda:
  - `token` en `localStorage` bajo la clave `token`
  - `usuario` en `localStorage` bajo la clave `usuario`
- El estado de usuario se inicializa desde `localStorage` cuando se monta el proveedor.
- `src/api/api.ts` añade un interceptor que coloca el header `Authorization: Bearer <token>` en todas las peticiones hacia el BFF.
- Si el backend responde con `401`, el interceptor limpia el token y el usuario y redirige a `/login`.

## Integración con el BFF
- `src/api/api.ts` usa `baseURL: http://localhost:8080/bff` para conectar con el BFF.
- El frontend consume el BFF y no llama directamente a `ms-login` ni `ms-inventory`.
- El frontend en desarrollo se sirve típicamente en `http://localhost:5173`.
- Rutas principales usadas por el frontend:
  - `POST /bff/login`
  - `POST /bff/login/register`
  - `POST /bff/login/admin`
  - `PUT /bff/login/{id}`
  - `GET /bff/login/{id}`
  - `GET /bff/login`
  - `DELETE /bff/login/{id}`
  - `GET /bff/inventory`
  - `GET /bff/inventory/code/{code}`
  - `POST /bff/inventory`
  - `PUT /bff/inventory/code/{code}`
  - `DELETE /bff/inventory/code/{code}`

## Funcionalidades del frontend
- **Login**: al iniciar sesión, guarda token y usuario y redirige al home.
- **Registro**: validaciones básicas en el frontend antes de enviar datos.
- **Perfil**: muestra datos del usuario, permite editar nombre/apellido/correo y actualiza `localStorage`.
- **Productos**: lista productos usando el endpoint de inventario del BFF.
- **Dashboard**: panel de administración con carga de usuarios y productos, y creación/eliminación de usuarios.
- **Navbar**: muestra enlaces distintos según el usuario logueado y su rol.

## Almacenamiento y persistencia
- El usuario autenticado se mantiene con `localStorage`.
- En recargas, `AuthProvider` lee `usuario` desde `localStorage`.
- El logout elimina `token` y `usuario` de `localStorage`.

## Reglas de UI y seguridad
- `ProtectedRoute.tsx` existe para proteger rutas basadas en autenticación.
- La UI también oculta enlaces de perfil y admin cuando no hay usuario autenticado o el rol no es ADMIN.
- Las llamadas a administración y actualización usan el token del usuario para autorización.

## Ejecución local
### Prerrequisitos
- Node.js instalado
- `ms-bff` levantado en `http://localhost:8080`

### Instalar dependencias
```bash
npm install
```

### Ejecutar en modo desarrollo
```bash
npm run dev
```

- En desarrollo Vite sirve el frontend en `http://localhost:5173`.
- Con `docker-compose up -d --build`, el frontend también queda expuesto en `http://localhost:5173`.

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
- El frontend se sirve localmente en `http://localhost:5173` cuando ejecutas `npm run dev`.
- Con Docker Compose, `front` se mapea a `5173:5173` y `ms-bff` a `8080:8080`.
- El frontend está configurado para usar el BFF en `http://localhost:8080/bff`; si cambias el puerto del BFF, actualiza `src/api/api.ts`.
- El BFF realiza la reexpedición de tokens hacia `ms-login` y `ms-inventory`, por lo que el frontend solo necesita enviar el token en la cabecera.
- Si el token expira o es inválido, el frontend forzará logout y volverá a `/login`.
