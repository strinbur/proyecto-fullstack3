import { useState, useEffect, useMemo } from "react";
import toast from "react-hot-toast";
import axios from "axios";
import {
  Package,
  DollarSign,
  Users,
  Receipt,
  CheckCircle,
  Clock,
  Banknote,
  BarChart3,
  XCircle,
  X,
} from "lucide-react";
import * as XLSX from "xlsx";
import {
  getAllProducts,
  updateProduct,
} from "../../features/inventory/inventoryApi";
import { createUser, getAllUsers, deleteUser } from "../../features/auth/authApi";
import { getAllOrders } from "../../features/order/orderApi";
import {
  getSalesSummary,
  getTopProducts,
  getCriticalStock,
  getHistory,
} from "../../features/reporting/reportingApi";

import "./Dashboard.css";


type Role = "CLIENTE" | "ADMIN" | "VENTAS";

type User = {
  id: string;
  name: string;
  lastname: string;
  email: string;
  role: Role;
  comprado?: number;
};

type Product = {
  id: string;
  code: string;
  name: string;
  brand: string;
  price: number;
  quantity: number;
  category: string;
};

type OrderItemDetail = {
  productCode: string;
  name: string;
  price: number;
  quantity: number;
  category: string;
  subtotal: number;
};

type OrderRecord = {
  id: string;
  userEmail: string;
  userName: string;
  items: OrderItemDetail[];
  total: number;
  status: string;
  createdAt: string;
};

// ----- Tipos de Reporting -----

type SalesSummary = {
  totalOrders: number;
  completedOrders: number;
  pendingOrders: number;
  cancelledOrders: number;
  totalRevenue: number;
  averageOrderValue: number;
};

type TopProductItem = {
  productCode: string;
  name: string;
  category: string;
  totalQuantitySold: number;
  totalRevenue: number;
};

type TopProducts = {
  topN: number;
  products: TopProductItem[];
};

type CriticalStockItem = {
  code: string;
  name: string;
  category: string;
  currentQuantity: number;
  price: number;
};

type CriticalStock = {
  threshold: number;
  totalCriticalItems: number;
  items: CriticalStockItem[];
};

type ReportLog = {
  id: string;
  requestedBy: string;
  requestedAt: string;
  snapshot: unknown;
};

type ReportTab = "summary" | "topProducts" | "criticalStock" | "history";

// ----- Tipo de la vista activa (controlada por las stat-cards) -----
type DashboardView = "products" | "sales" | "users";

const PAGE_SIZE = 10;


export default function Dashboard() {

  const [users, setUsers] = useState<User[]>([]);
  const [products, setProducts] = useState<Product[]>([]);
  const [purchaseStats, setPurchaseStats] = useState<Record<string, number>>({});
  const [orders, setOrders] = useState<OrderRecord[]>([]);
  const [ordersLoading, setOrdersLoading] = useState(false);
  const [ordersLoaded, setOrdersLoaded] = useState(false);

  const [name, setName] = useState("");
  const [lastname, setLastname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState<Role>("CLIENTE");

  // ----- Vista activa de la tabla principal -----
  const [activeView, setActiveView] = useState<DashboardView>("products");
  const [page, setPage] = useState(1);

  // ----- Modal de edicion de producto -----
  const [editingProduct, setEditingProduct] = useState<Product | null>(null);
  const [editPrice, setEditPrice] = useState("");
  const [editQuantity, setEditQuantity] = useState("");
  const [savingProduct, setSavingProduct] = useState(false);

  // ----- Estado de Reporting -----
  const [activeReportTab, setActiveReportTab] = useState<ReportTab>("summary");
  const [reportsLoading, setReportsLoading] = useState(false);
  const [salesSummary, setSalesSummary] = useState<SalesSummary | null>(null);
  const [topProducts, setTopProducts] = useState<TopProducts | null>(null);
  const [criticalStock, setCriticalStock] = useState<CriticalStock | null>(null);
  const [history, setHistory] = useState<ReportLog[]>([]);

  const computePurchaseStats = (loadedOrders: OrderRecord[]) => {
    return loadedOrders.reduce((stats, order) => {
      const totalItems = order.items.reduce((sum, item) => sum + item.quantity, 0);
      const key = order.userEmail;
      stats[key] = (stats[key] || 0) + totalItems;
      return stats;
    }, {} as Record<string, number>);
  };

  const loadAllSales = async () => {
    setOrdersLoading(true);
    try {
      const data: OrderRecord[] = await getAllOrders();
      setOrders(data);
      setPurchaseStats(computePurchaseStats(data));
      setOrdersLoaded(true);
    } catch (error) {
      console.error(error);
      toast.error("No se pudieron cargar las ventas");
    } finally {
      setOrdersLoading(false);
    }
  };

  const fetchProducts = async () => {
    try {
      const data: Product[] = await getAllProducts();
      setProducts(data);
    } catch (error) {
      console.error(error);
      toast.error("Error al cargar productos");
    }
  };

  const fetchUsers = async () => {
    try {
      const data: User[] = await getAllUsers();
      setUsers(data);
    } catch (error) {
      console.error(error);
      toast.error("Error al cargar usuarios");
    }
  };

  useEffect(() => {
    fetchUsers();
    fetchProducts();
    loadAllSales();
  }, []);

  const computeTotalSales = () => {
    return orders.reduce((sum, order) => {
      return sum + order.items.reduce((s, item) => s + item.quantity, 0);
    }, 0);
  };

  // ----- Carga de reportes (lazy, segun el tab activo) -----
  useEffect(() => {
    const fetchReportData = async () => {
      setReportsLoading(true);
      try {
        if (activeReportTab === "summary" && !salesSummary) {
          const data: SalesSummary = await getSalesSummary();
          setSalesSummary(data);
        }

        if (activeReportTab === "topProducts" && !topProducts) {
          const data: TopProducts = await getTopProducts(5);
          setTopProducts(data);
        }

        if (activeReportTab === "criticalStock" && !criticalStock) {
          const data: CriticalStock = await getCriticalStock();
          setCriticalStock(data);
        }

        if (activeReportTab === "history" && history.length === 0) {
          const data: ReportLog[] = await getHistory();
          setHistory(data);
        }
      } catch (error) {
        console.error(error);
        toast.error("Error al cargar el reporte");
      } finally {
        setReportsLoading(false);
      }
    };

    fetchReportData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [activeReportTab]);

//Crea un nuevo usuario
  const handleCreateUser = async () => {
    try {
      await createUser({
        name,
        lastname,
        email,
        password,
        role
      });

      toast.success(`Usuario ${role} creado correctamente`);

      setName("");
      setLastname("");
      setEmail("");
      setPassword("");
      setRole("CLIENTE");

      await fetchUsers();

    } catch (error: unknown) {

      console.error(error);

      if (axios.isAxiosError(error)) {

        const status = error.response?.status;
        const data = error.response?.data;

        const message =
          typeof data === "string"
            ? data
            : data?.message;

        if (status === 409 || message?.toLowerCase().includes("correo")) {
          toast.error("El correo ya está registrado");
          return;
        }

        toast.error(message || "Error al crear usuario");

      } else {
        toast.error("Error inesperado");
      }
    }
  };

//Elimina un usuario por su ID
  const handleDeleteUser = async (id: string) => {
    try {
      await deleteUser(id);
      toast.success("Usuario eliminado");
      await fetchUsers();
    } catch (error) {
      console.error(error);
      toast.error("Error al eliminar usuario");
    }
  };

  // ----- Cambiar la vista activa al hacer click en una stat-card -----
  const handleSelectView = (view: DashboardView) => {
    setActiveView(view);
    setPage(1);

    if (view === "sales" && !ordersLoaded) {
      loadAllSales();
    }
  };

  // ----- Modal de edicion de producto -----
  const openEditModal = (product: Product) => {
    setEditingProduct(product);
    setEditPrice(String(product.price));
    setEditQuantity(String(product.quantity));
  };

  const closeEditModal = () => {
    setEditingProduct(null);
    setEditPrice("");
    setEditQuantity("");
  };

  const handleSaveProduct = async () => {
    if (!editingProduct) return;

    const priceNumber = Number(editPrice);
    const quantityNumber = Number(editQuantity);

    if (Number.isNaN(priceNumber) || priceNumber < 0) {
      toast.error("Precio invalido");
      return;
    }

    if (Number.isNaN(quantityNumber) || quantityNumber < 0) {
      toast.error("Cantidad invalida");
      return;
    }

    setSavingProduct(true);
    try {
      await updateProduct(editingProduct.code, {
        name: editingProduct.name,
        brand: editingProduct.brand,
        category: editingProduct.category,
        price: priceNumber,
        quantity: quantityNumber,
      });

      toast.success("Producto actualizado");
      closeEditModal();
      await fetchProducts();
    } catch (error) {
      console.error(error);
      toast.error("Error al actualizar el producto");
    } finally {
      setSavingProduct(false);
    }
  };

  // ----- Paginacion generica -----
  const paginatedProducts = useMemo(() => {
    const start = (page - 1) * PAGE_SIZE;
    return products.slice(start, start + PAGE_SIZE);
  }, [products, page]);

  const paginatedOrders = useMemo(() => {
    const start = (page - 1) * PAGE_SIZE;
    return orders.slice(start, start + PAGE_SIZE);
  }, [orders, page]);

  const paginatedUsers = useMemo(() => {
    const start = (page - 1) * PAGE_SIZE;
    return users.slice(start, start + PAGE_SIZE);
  }, [users, page]);

  const totalItemsByView: Record<DashboardView, number> = {
    products: products.length,
    sales: orders.length,
    users: users.length,
  };

  const totalPages = Math.max(1, Math.ceil(totalItemsByView[activeView] / PAGE_SIZE));

  const viewTitles: Record<DashboardView, string> = {
    products: "Lista de productos",
    sales: "Lista de ventas",
    users: "Gestión de usuarios",
  };

  const formatSalesItemSummary = (items: OrderItemDetail[]) => {
    return items
      .map((item) => `${item.name} (${item.quantity})`)
      .join("; ");
  };

  const exportToExcel = (data: object[], sheetName: string, fileName: string) => {
    const workbook = XLSX.utils.book_new();
    const worksheet = XLSX.utils.json_to_sheet(data);
    XLSX.utils.book_append_sheet(workbook, worksheet, sheetName);
    const excelBuffer = XLSX.write(workbook, { bookType: "xlsx", type: "array" });
    const blob = new Blob([excelBuffer], { type: "application/octet-stream" });
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.download = fileName;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(url);
  };

  const exportCurrentView = () => {
    if (activeView === "products") {
      const rows = products.map((product) => ({
        Código: product.code,
        Nombre: product.name,
        Categoría: product.category,
        Precio: product.price,
        Stock: product.quantity,
      }));
      exportToExcel(rows, "Productos", "productos.xlsx");
      return;
    }

    if (activeView === "sales") {
      const rows = orders.map((order) => ({
        "ID Pedido": order.id,
        Cliente: order.userName,
        "Correo cliente": order.userEmail,
        Estado: order.status,
        Total: order.total,
        Productos: formatSalesItemSummary(order.items),
        Fecha: new Date(order.createdAt).toLocaleString("es-ES", {
          dateStyle: "short",
          timeStyle: "short",
        }),
      }));
      exportToExcel(rows, "Ventas", "ventas.xlsx");
      return;
    }

    if (activeView === "users") {
      const rows = users.map((user) => ({
        Nombre: `${user.name} ${user.lastname}`,
        Correo: user.email,
        Rol: user.role,
        Comprado: user.comprado ?? 0,
      }));
      exportToExcel(rows, "Usuarios", "usuarios.xlsx");
      return;
    }
  };

  return (
    <div className="dashboard-page">
      <div className="dashboard-container">

        <header className="dashboard-header-centered">
          <h1>Panel de Administración</h1>
          <p>Bienvenido al centro de control de Grupo Cordillera</p>
        </header>

        <div className="stats-grid-centered">

          <button
            type="button"
            className={`stat-card stat-card-clickable ${activeView === "products" ? "stat-card-active" : ""}`}
            onClick={() => handleSelectView("products")}
          >
            <div className="stat-icon blue">
              <Package size={22} />
            </div>
            <div>
              <span className="stat-label">Productos</span>
              <span className="stat-number">{products.length}</span>
            </div>
          </button>

          <button
            type="button"
            className={`stat-card stat-card-clickable ${activeView === "sales" ? "stat-card-active" : ""}`}
            onClick={() => handleSelectView("sales")}
          >
            <div className="stat-icon green">
              <DollarSign size={22} />
            </div>
            <div>
              <span className="stat-label">Ventas</span>
              <span className="stat-number">{computeTotalSales()}</span>
            </div>
          </button>

          <button
            type="button"
            className={`stat-card stat-card-clickable ${activeView === "users" ? "stat-card-active" : ""}`}
            onClick={() => handleSelectView("users")}
          >
            <div className="stat-icon orange">
              <Users size={22} />
            </div>
            <div>
              <span className="stat-label">Usuarios</span>
              <span className="stat-number">{users.length}</span>
            </div>
          </button>

        </div>

        <div className="create-user-card">

          <h2>Crear Usuario</h2>

          <input placeholder="Nombre" value={name} onChange={(e) => setName(e.target.value)} />
          <input placeholder="Apellido" value={lastname} onChange={(e) => setLastname(e.target.value)} />
          <input placeholder="Correo" value={email} onChange={(e) => setEmail(e.target.value)} />
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />

          <select value={role} onChange={(e) => setRole(e.target.value as Role)}>
            <option value="CLIENTE">Cliente</option>
            <option value="ADMIN">Administrador</option>
            <option value="VENTAS">Ventas</option>
          </select>

          <button onClick={handleCreateUser}>
            Crear Usuario
          </button>

        </div>

        <div className="dashboard-main-card">

          <div className="dashboard-main-card-header">
            <div>
              <h3>{viewTitles[activeView]}</h3>
            </div>
            <button className="view-sales-btn" onClick={exportCurrentView}>
              Descargar lista actual
            </button>
          </div>

          {activeView === "products" && (
            products.length === 0 ? (
              <p>No hay productos registrados.</p>
            ) : (
              <>
                <table className="custom-table">
                  <thead>
                    <tr>
                      <th>Código</th>
                      <th>Nombre</th>
                      <th>Categoría</th>
                      <th>Precio</th>
                      <th>Stock</th>
                      <th>Gestión</th>
                    </tr>
                  </thead>
                  <tbody>
                    {paginatedProducts.map((product) => (
                      <tr key={product.code}>
                        <td>{product.code}</td>
                        <td>{product.name}</td>
                        <td>{product.category}</td>
                        <td>${product.price.toFixed(2)}</td>
                        <td>{product.quantity}</td>
                        <td>
                          <button
                            className="manage-btn"
                            onClick={() => openEditModal(product)}
                          >
                            Gestionar
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>

                <Pagination page={page} totalPages={totalPages} onChange={setPage} />
              </>
            )
          )}

          {activeView === "sales" && (
            ordersLoading ? (
              <p>Cargando ventas...</p>
            ) : orders.length === 0 ? (
              <p>No hay ventas registradas.</p>
            ) : (
              <>
                <table className="custom-table sales-table">
                  <thead>
                    <tr>
                      <th>ID Pedido</th>
                      <th>Cliente</th>
                      <th>Correo cliente</th>
                      <th>Estado</th>
                      <th>Total</th>
                      <th>Productos</th>
                      <th>Fecha</th>
                    </tr>
                  </thead>
                  <tbody>
                    {paginatedOrders.map((order) => (
                      <tr key={order.id}>
                        <td>{order.id.slice(-6)}</td>
                        <td>{order.userName}</td>
                        <td>{order.userEmail}</td>
                        <td>{order.status}</td>
                        <td>${order.total.toFixed(2)}</td>
                        <td>{order.items.length}</td>
                        <td>{new Date(order.createdAt).toLocaleString("es-ES", { dateStyle: "short", timeStyle: "short" })}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>

                <Pagination page={page} totalPages={totalPages} onChange={setPage} />
              </>
            )
          )}

          {activeView === "users" && (
            users.length === 0 ? (
              <p>No hay usuarios registrados.</p>
            ) : (
              <>
                <table className="custom-table">
                  <thead>
                    <tr>
                      <th>Nombre</th>
                      <th>Correo</th>
                      <th>Rol</th>
                      <th>Comprado</th>
                      <th>Gestión</th>
                    </tr>
                  </thead>
                  <tbody>
                    {paginatedUsers.map((user) => (
                      <tr key={user.id}>
                        <td>{user.name} {user.lastname}</td>
                        <td>{user.email}</td>
                        <td>{user.role}</td>
                        <td>{purchaseStats[user.email] ?? 0}</td>
                        <td>
                          <button
                            className="delete-btn"
                            onClick={() => handleDeleteUser(user.id)}
                          >
                            Eliminar
                          </button>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>

                <Pagination page={page} totalPages={totalPages} onChange={setPage} />
              </>
            )
          )}

        </div>

        {/* ===== SECCION DE REPORTES ===== */}
        <div className="dashboard-main-card reports-card">

          <div className="dashboard-main-card-header">
            <h3>Reportes</h3>
          </div>

          <div className="report-tabs">
            <button
              className={`report-tab-btn ${activeReportTab === "summary" ? "active" : ""}`}
              onClick={() => setActiveReportTab("summary")}
            >
              Resumen de ventas
            </button>
            <button
              className={`report-tab-btn ${activeReportTab === "topProducts" ? "active" : ""}`}
              onClick={() => setActiveReportTab("topProducts")}
            >
              Top productos
            </button>
            <button
              className={`report-tab-btn ${activeReportTab === "criticalStock" ? "active" : ""}`}
              onClick={() => setActiveReportTab("criticalStock")}
            >
              Stock crítico
            </button>
            <button
              className={`report-tab-btn ${activeReportTab === "history" ? "active" : ""}`}
              onClick={() => setActiveReportTab("history")}
            >
              Historial
            </button>
          </div>

          <div className="report-tab-content">

            {reportsLoading ? (
              <p>Cargando reporte...</p>
            ) : (
              <>

                {activeReportTab === "summary" && salesSummary && (
                  <div className="stats-grid-centered report-summary-grid">
                    <div className="stat-card">
                      <div className="stat-icon blue">
                        <Receipt size={22} />
                      </div>
                      <div>
                        <span className="stat-label">Total pedidos</span>
                        <span className="stat-number">{salesSummary.totalOrders}</span>
                      </div>
                    </div>
                    <div className="stat-card">
                      <div className="stat-icon green">
                        <CheckCircle size={22} />
                      </div>
                      <div>
                        <span className="stat-label">Completados</span>
                        <span className="stat-number">{salesSummary.completedOrders}</span>
                      </div>
                    </div>
                    <div className="stat-card">
                      <div className="stat-icon orange">
                        <Clock size={22} />
                      </div>
                      <div>
                        <span className="stat-label">Pendientes</span>
                        <span className="stat-number">{salesSummary.pendingOrders}</span>
                      </div>
                    </div>
                    <div className="stat-card">
                      <div className="stat-icon blue">
                        <Banknote size={22} />
                      </div>
                      <div>
                        <span className="stat-label">Ingresos totales</span>
                        <span className="stat-number">${salesSummary.totalRevenue.toFixed(2)}</span>
                      </div>
                    </div>
                    <div className="stat-card">
                      <div className="stat-icon green">
                        <BarChart3 size={22} />
                      </div>
                      <div>
                        <span className="stat-label">Ticket promedio</span>
                        <span className="stat-number">${salesSummary.averageOrderValue.toFixed(2)}</span>
                      </div>
                    </div>
                    <div className="stat-card">
                      <div className="stat-icon orange">
                        <XCircle size={22} />
                      </div>
                      <div>
                        <span className="stat-label">Cancelados</span>
                        <span className="stat-number">{salesSummary.cancelledOrders}</span>
                      </div>
                    </div>
                  </div>
                )}

                {activeReportTab === "topProducts" && topProducts && (
                  <table className="custom-table">
                    <thead>
                      <tr>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Categoría</th>
                        <th>Unidades vendidas</th>
                        <th>Ingresos</th>
                      </tr>
                    </thead>
                    <tbody>
                      {topProducts.products.map((p) => (
                        <tr key={p.productCode}>
                          <td>{p.productCode}</td>
                          <td>{p.name}</td>
                          <td>{p.category}</td>
                          <td>{p.totalQuantitySold}</td>
                          <td>${p.totalRevenue.toFixed(2)}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                )}

                {activeReportTab === "criticalStock" && criticalStock && (
                  <>
                    <p className="report-subtext">
                      Umbral crítico: {criticalStock.threshold} unidades · {criticalStock.totalCriticalItems} producto(s) en alerta
                    </p>
                    <table className="custom-table">
                      <thead>
                        <tr>
                          <th>Código</th>
                          <th>Nombre</th>
                          <th>Categoría</th>
                          <th>Cantidad actual</th>
                          <th>Precio</th>
                        </tr>
                      </thead>
                      <tbody>
                        {criticalStock.items.map((item) => (
                          <tr key={item.code}>
                            <td>{item.code}</td>
                            <td>{item.name}</td>
                            <td>{item.category}</td>
                            <td>{item.currentQuantity}</td>
                            <td>${item.price.toFixed(2)}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </>
                )}

                {activeReportTab === "history" && (
                  history.length === 0 ? (
                    <p>No hay historial de reportes.</p>
                  ) : (
                    <table className="custom-table">
                      <thead>
                        <tr>
                          <th>Solicitado por</th>
                          <th>Fecha</th>
                        </tr>
                      </thead>
                      <tbody>
                        {history.map((log) => (
                          <tr key={log.id}>
                            <td>{log.requestedBy}</td>
                            <td>{new Date(log.requestedAt).toLocaleString("es-ES", { dateStyle: "short", timeStyle: "short" })}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  )
                )}

              </>
            )}

          </div>

        </div>

      </div>

      {/* ===== MODAL DE EDICION DE PRODUCTO ===== */}
      {editingProduct && (
        <div className="modal-overlay" onClick={closeEditModal}>
          <div className="modal-card" onClick={(e) => e.stopPropagation()}>

            <div className="modal-header">
              <h3>Gestionar producto</h3>
              <button className="modal-close-btn" onClick={closeEditModal} aria-label="Cerrar">
                <X size={20} />
              </button>
            </div>

            <div className="modal-body">

              <div className="modal-field">
                <span className="modal-field-label">Código</span>
                <span className="modal-field-value">{editingProduct.code}</span>
              </div>

              <div className="modal-field">
                <span className="modal-field-label">Nombre</span>
                <span className="modal-field-value">{editingProduct.name}</span>
              </div>

              <div className="modal-field">
                <span className="modal-field-label">Marca</span>
                <span className="modal-field-value">{editingProduct.brand}</span>
              </div>

              <div className="modal-field">
                <span className="modal-field-label">Categoría</span>
                <span className="modal-field-value">{editingProduct.category}</span>
              </div>

              <div className="modal-editable-field">
                <label htmlFor="edit-price">Precio</label>
                <input
                  id="edit-price"
                  type="number"
                  min="0"
                  step="0.01"
                  value={editPrice}
                  onChange={(e) => setEditPrice(e.target.value)}
                />
              </div>

              <div className="modal-editable-field">
                <label htmlFor="edit-quantity">Stock</label>
                <input
                  id="edit-quantity"
                  type="number"
                  min="0"
                  step="1"
                  value={editQuantity}
                  onChange={(e) => setEditQuantity(e.target.value)}
                />
              </div>

            </div>

            <div className="modal-footer">
              <button className="modal-cancel-btn" onClick={closeEditModal} disabled={savingProduct}>
                Cancelar
              </button>
              <button className="modal-save-btn" onClick={handleSaveProduct} disabled={savingProduct}>
                {savingProduct ? "Guardando..." : "Guardar cambios"}
              </button>
            </div>

          </div>
        </div>
      )}

    </div>
  );
}


// ----- Componente de paginacion reutilizable -----
function Pagination({
  page,
  totalPages,
  onChange,
}: {
  page: number;
  totalPages: number;
  onChange: (page: number) => void;
}) {
  if (totalPages <= 1) return null;

  return (
    <div className="pagination">
      <button
        className="pagination-btn"
        onClick={() => onChange(Math.max(1, page - 1))}
        disabled={page === 1}
      >
        Anterior
      </button>

      <span className="pagination-info">
        Página {page} de {totalPages}
      </span>

      <button
        className="pagination-btn"
        onClick={() => onChange(Math.min(totalPages, page + 1))}
        disabled={page === totalPages}
      >
        Siguiente
      </button>
    </div>
  );
}