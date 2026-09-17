import { Link, Route, Routes } from 'react-router-dom'

import Header from './components/Header'

import Home from './pages/Home'
import Catalogo from './pages/Catalogo'
import LivroDetalhes from './pages/LivroDetalhes'
import NovaReserva from './pages/NovaReserva'
import NovaDoacao from './pages/NovaDoacao'
import NotFound from './pages/NotFound'
import Eventos from './pages/Eventos'
import Login from './pages/Login'
import Cadastro from './pages/Cadastro'
import RequireAuth from './components/RequireAuth'

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
            path="/eventos"
            element={<Eventos />}
          />

          <Route
            path="/reservas/nova"
            element={
              <RequireAuth>
                <NovaReserva />
              </RequireAuth>
            }
          />

          <Route
            path="/doacoes/nova"
            element={
              <RequireAuth>
                <NovaDoacao />
              </RequireAuth>
            }
          /> 

          <Route
            path="*"
            element={<NotFound />}
          />

          <Route
            path="/login"
            element={<Login />}
          />

          <Route 
          path="/cadastro"
          element={<Cadastro />}
          />

        </Routes>
      </main>
    </>
  )
}

export default App