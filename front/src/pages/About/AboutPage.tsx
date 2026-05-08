import React from 'react';
import './AboutPage.css';

const AboutPage: React.FC = () => {
  const team = [
    { name: 'Patricio Olguin', role: 'CEO & Fundadora', img: 'https://static.aiquickdraw.com/tools/example/1764123592677_LXcR2PNo.png' },
    { name: 'Oscar Leyton', role: 'Director de Producto', img: 'https://media.licdn.com/dms/image/v2/D4D03AQFiQ24tAnisJg/profile-displayphoto-scale_200_200/B4DZw.5lIXHwAY-/0/1770581808675?e=2147483647&v=beta&t=nmGrPvlP-RIpfzZfjGhsONujttvkXRrUr73R4eNmSvE' },
    { name: 'Benjamin Vásquez', role: 'Lead Developer', img: 'https://media.licdn.com/dms/image/v2/D4E03AQHbA9dgksT4KQ/profile-displayphoto-scale_200_200/B4EZgLCCIpGoAY-/0/1752531746955?e=2147483647&v=beta&t=bHNl_J6bW4eJbutPQuhhNWb48cj8F-ONhY9C2wS5cec' },
  ];

  return (
    <div className="about-container">
      {/* HERO SECTION */}
      <header className="about-hero">
        <div className="content">
          <h1>Quiénes Somos</h1>
          <p>Somos una organización líder en el sector de retail y comercialización de productos para el hogar y tecnología en Chile. </p>
          <p>Con presencia en las principales ciudades del país y múltiples sucursales, nos especializamos en conectar las necesidades de nuestros clientes con las mejores soluciones para su vida diaria.</p>
        </div>
      </header>

      {/* MISSION & VISION */}
      <section className="about-section mission-vision">
        <div className="about-grid">
          <div className="image-wrapper">
            <img 
              src="https://images.pexels.com/photos/3183150/pexels-photo-3183150.jpeg" 
              alt="Nuestro equipo" 
            />
          </div>
          <div className="text-wrapper">
            <div className="card-info">
              <h2>Nuestra Misión</h2>
              <p>Consolidar y transformar los datos de nuestras diversas áreas de negocio en información valiosa y oportuna. Buscamos eliminar las barreras de la información mediante plataformas inteligentes que permitan a nuestra alta gerencia tomar decisiones basadas en la realidad del mercado en tiempo real.</p>
            </div>
            <div className="card-info">
              <h2>Nuestra Visión</h2>
              <p>Ser el referente nacional en retail inteligente, destacando por nuestra capacidad de adaptación tecnológica, escalabilidad y la eficiencia de nuestras operaciones integradas bajo estándares de clase mundial.</p>
            </div>
          </div>
        </div>
      </section>

      {/* VALUES */}
      <section className="about-values">
        <h2>Nuestros Valores</h2>
        <div className="values-grid">
          <div className="value-item">
            <span className="icon">💡</span>
            <h3>Innovación</h3>
            <p>Siempre a la vanguardia de las últimas tecnologías.</p>
          </div>
          <div className="value-item">
            <span className="icon">🔒</span>
            <h3>Seguridad</h3>
            <p>Protegemos la integridad de cada dato y proceso.</p>
          </div>
          <div className="value-item">
            <span className="icon">🚀</span>
            <h3>Excelencia</h3>
            <p>Entregamos resultados que superan las expectativas.</p>
          </div>
        </div>
      </section>

      {/* TEAM SECTION */}
      <section className="about-team">
        <h2>Conoce al Equipo</h2>
        <div className="team-grid">
          {team.map((member, index) => (
            <div key={index} className="team-card">
              <img src={member.img} alt={member.name} />
              <div className="team-info">
                <h3>{member.name}</h3>
                <p>{member.role}</p>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};

export default AboutPage;