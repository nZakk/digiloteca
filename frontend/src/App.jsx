import { Link, Route, Routes } from 'react-router-dom'

import Header from './components/Header'

import Home from './pages/Home'
import Catalogo from './pages/Catalogo'
import LivroDetalhes from './pages/LivroDetalhes'
import NovaReserva from './pages/NovaReserva'
import NovaDoacao from './pages/NovaDoacao'
import NotFound from './pages/NotFound'

function App() {

  return (
    <>
      <Header/>

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


          <Route
            path="*"
            element={<NotFound />}
          />

        </Routes>
      </main>
    </>
  )
}

export default App