import { Link, Route, Routes } from 'react-router-dom'

import Home from './pages/Home'
import Catalogo from './pages/Catalogo'
import LivroDetalhes from './pages/LivroDetalhes'
import NovaReserva from './pages/NovaReserva'
import NovaDoacao from './pages/NovaDoacao'

function App() {

  return (
    <>
      <header>
        <h1>Biblioteca Comunitária</h1>

        <nav>
          <Link to="/">Início</Link>
          {' | '}
          <Link to="/catalogo">Catálogo</Link>
          {' | '}
          <Link to="/reservas/nova">Reservar</Link>
           {' | '}
          <Link to="/doacoes/nova">Doar livros</Link>

          
        </nav>
      </header>

      <main>
        <Routes>

          <Route
            path="/"
            element={<Home />}
          />

          <Route
            path="/catalogo"
            element={<Catalogo />}
          />

          <Route
            path="/livros/:id"
            element={<LivroDetalhes />}
          />

          <Route
            path="/reservas/nova"
            element={<NovaReserva />}
          />

          <Route
            path="/doacoes/nova"
            element={<NovaDoacao />}
          />

        </Routes>
      </main>
    </>
  )
}

export default App