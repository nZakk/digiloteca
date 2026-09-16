import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

import { buscarProximosEventos } from '../services/api'

function Home() {

  const [eventos, setEventos] = useState([])
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState(null)

  useEffect(() => {

    async function carregarEventos() {

      try {

        const dados =
          await buscarProximosEventos()

        setEventos(dados)

      } catch (error) {

        setErro(error.message)

      } finally {

        setCarregando(false)

      }
    }

    carregarEventos()

  }, [])

  return (
    <div>

      <section>
        <h2>Bem-vindo à Biblioteca Comunitária</h2>

        <p>
          Consulte nosso acervo, encontre livros disponíveis
          e acompanhe os próximos eventos da comunidade.
        </p>

        <Link to="/catalogo">
          Ver catálogo
        </Link>
      </section>

      <section>

        <h2>Próximos eventos</h2>

        {carregando && (
          <p>Carregando eventos...</p>
        )}

        {erro && (
          <p>{erro}</p>
        )}

        {!carregando &&
         !erro &&
         eventos.length === 0 && (

          <p>
            Nenhum evento programado no momento.
          </p>

        )}

        {eventos.map(evento => (

          <article key={evento.id}>

            <h3>{evento.titulo}</h3>

            <p>
              {evento.descricao}
            </p>

            <p>
              <strong>Local:</strong>{' '}
              {evento.local}
            </p>

            <p>
              <strong>Data:</strong>{' '}
              {new Date(
                evento.dataHora
              ).toLocaleString('pt-BR')}
            </p>

          </article>

        ))}

      </section>

    </div>
  )
}

export default Home