import { NavLink } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

function Header() {

  const { usuario, sair } = useAuth()

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

          <NavLink to="/catalogo">
            Catálogo
          </NavLink>

          <NavLink to="/eventos">
            Eventos
          </NavLink>

          {usuario && (
            <>
              <NavLink to="/reservas/nova">
                Reservar
              </NavLink>

              <NavLink to="/doacoes/nova">
                Doar
              </NavLink>
            </>
          )}

          {!usuario ? (
            <>
              <NavLink to="/login">
                Entrar
              </NavLink>

              <NavLink to="/cadastro">
                Criar conta
              </NavLink>
            </>
          ) : (
            <>
              <span>
                Olá, {usuario.nome}
              </span>

              <button
                type="button"
                onClick={sair}
              >
                Sair
              </button>
            </>
          )}

        </nav>

      </div>

    </header>
  )
}

export default Header