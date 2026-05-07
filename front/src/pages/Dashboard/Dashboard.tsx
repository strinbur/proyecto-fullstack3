import "./Dashboard.css";

export default function Dashboard() {
  return (
    <div className="dashboard-page">
      <div className="dashboard-container">
        
        {/* CABECERA DEL DASHBOARD */}
        <header className="dashboard-header-centered">
          <h1>Panel de Administración</h1>
          <p>Bienvenido al centro de control de Grupo Cordillera</p>
        </header>

        {/* TARJETAS DE ESTADÍSTICAS */}
        <div className="stats-grid-centered">
          <div className="stat-card">
            <div className="stat-icon blue">📦</div>
            <div className="stat-info">
              <span className="stat-label">Productos</span>
              <span className="stat-number">452</span>
            </div>
          </div>
          
          <div className="stat-card">
            <div className="stat-icon green">💰</div>
            <div className="stat-info">
              <span className="stat-label">Ventas Hoy</span>
              <span className="stat-number">$125.400</span>
            </div>
          </div>

          <div className="stat-card">
            <div className="stat-icon orange">👥</div>
            <div className="stat-info">
              <span className="stat-label">Usuarios</span>
              <span className="stat-number">89</span>
            </div>
          </div>
        </div>

        {/* TABLA CENTRAL */}
        <div className="dashboard-main-card">
          <div className="card-header">
            <h3>Gestión de Usuarios Recientes</h3>
            <button className="btn-primary">Ver Todos</button>
          </div>
          
          <div className="table-responsive">
            <table className="custom-table">
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Correo</th>
                  <th>Rol</th>
                  <th>Estado</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>Oscar Leyton</td>
                  <td>osleyton@duocuc.cl</td>
                  <td><span className="badge admin">ADMIN</span></td>
                  <td><span className="status-dot online"></span> Activo</td>
                </tr>
                <tr>
                  <td>Juan Pérez</td>
                  <td>jperez@correo.cl</td>
                  <td><span className="badge cliente">CLIENTE</span></td>
                  <td><span className="status-dot offline"></span> Inactivo</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

      </div>
    </div>
  );
}