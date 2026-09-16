import { NavLink } from 'react-router-dom'

function Header() {

  return (
    <header className="header-principal">

      <div className="header-conteudo">

        <NavLink
          to="/"
          className="logo"
        >
          Biblioteca Comunitária
        </NavLink>

        <nav className="menu">

          <NavLink to="/">
            Início
          </NavLink>
{' | '}
          <NavLink to="/catalogo">
            Catálogo
          </NavLink>
{' | '}
          <NavLink to="/eventos">
            Eventos
          </NavLink>
{' | '}
          <NavLink to="/reservas/nova">
            Reservar
          </NavLink>
{' | '}
          <NavLink to="/doacoes/nova">
            Doar
          </NavLink>

        </nav>

      </div>

    </header>
  )
}

export default Header