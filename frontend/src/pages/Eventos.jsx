import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

import { buscarEventos } from '../services/api'

function Eventos() {

  const [eventos, setEventos] = useState([])
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState(null)

  useEffect(() => {

    async function carregarEventos() {

      try {

        const dados = await buscarEventos()

        const ordenados = [...dados].sort(
          (a, b) =>
            new Date(a.dataHora) -
            new Date(b.dataHora)
        )

        setEventos(ordenados)

      } catch (error) {

        setErro(error.message)

      } finally {

        setCarregando(false)

      }
    }

    carregarEventos()

  }, [])

  if (carregando) {
    return <p>Carregando eventos...</p>
  }

  if (erro) {
    return <p>{erro}</p>
  }

  return (
    <div>

      <div className="cabecalho-pagina">

        <div>
          <h2>Eventos</h2>

          <p>
            Acompanhe as atividades e encontros
            promovidos pela biblioteca.
          </p>
        </div>

        <Link to="/">
          Voltar ao início
        </Link>

      </div>

      {eventos.length === 0 ? (

        <p>
          Nenhum evento cadastrado no momento.
        </p>

      ) : (

        <div className="grid-cards">

          {eventos.map(evento => (

            <article
              key={evento.id}
              className="card"
            >

              <h3>{evento.titulo}</h3>

              <p>
                {evento.descricao}
              </p>

              <p>
                <strong>Data:</strong>{' '}
                {new Date(
                  evento.dataHora
                ).toLocaleString('pt-BR')}
              </p>

              <p>
                <strong>Local:</strong>{' '}
                {evento.local}
              </p>

            </article>

          ))}

        </div>

      )}

    </div>
  )
}

export default Eventos