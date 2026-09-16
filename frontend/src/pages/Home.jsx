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

      <section className="hero">

  <div className="hero-conteudo">

    <p className="hero-subtitulo">
      Conhecimento para todos
    </p>

    <h1>
      Biblioteca Comunitária
    </h1>

    <p>
      Consulte livros, participe de eventos
      e contribua com doações para fortalecer
      o acesso à leitura na comunidade.
    </p>

    <div className="acoes">

      <Link
        to="/catalogo"
        className="botao"
      >
        Explorar catálogo
      </Link>

      <Link
        to="/doacoes/nova"
        className="botao botao-secundario"
      >
        Doar livros
      </Link>

    </div>

  </div>

</section>

<section>

  <h2>O que você pode fazer</h2>

  <div className="grid-cards">

    <article className="card">

      <h3>Consultar livros</h3>

      <p>
        Veja os livros disponíveis no acervo
        da biblioteca.
      </p>

      <Link to="/catalogo">
        Ver catálogo
      </Link>

    </article>

    <article className="card">

      <h3>Reservar</h3>

      <p>
        Reserve um exemplar disponível
        para retirada.
      </p>

      <Link to="/reservas/nova">
        Fazer reserva
      </Link>

    </article>

    <article className="card">

      <h3>Doar</h3>

      <p>
        Ajude a ampliar o acervo da
        biblioteca comunitária.
      </p>

      <Link to="/doacoes/nova">
        Quero doar
      </Link>

    </article>

  </div>

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