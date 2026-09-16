import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'

import { buscarLivroPorId } from '../services/api'

function LivroDetalhes() {

  const { id } = useParams()

  const [livro, setLivro] = useState(null)
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState(null)

  useEffect(() => {

    async function carregarLivro() {

      try {

        const dados =
          await buscarLivroPorId(id)

        setLivro(dados)

      } catch (error) {

        setErro(error.message)

      } finally {

        setCarregando(false)

      }
    }
    function formatarStatus(status) {

      const nomes = {
        DISPONIVEL: 'Disponível',
        RESERVADO: 'Reservado',
        EMPRESTADO: 'Emprestado',
        INDISPONIVEL: 'Indisponível',
      }

      return nomes[status] || status
    }

    carregarLivro()

  }, [id])

  if (carregando) {
    return <p>Carregando livro...</p>
  }

  if (erro) {
    return <p>{erro}</p>
  }

  if (!livro) {
    return <p>Livro não encontrado.</p>
  }

  return (
    <div>

      <Link to="/catalogo">
        ← Voltar ao catálogo
      </Link>

      <h2>
        {livro.titulo}
      </h2>

      <p>
        <strong>Autor:</strong>{' '}
        {livro.autor}
      </p>

      <p>
        <strong>ISBN:</strong>{' '}
        {livro.isbn}
      </p>

      <p>
        <strong>Editora:</strong>{' '}
        {livro.editora}
      </p>

      <p>
        <strong>Ano:</strong>{' '}
        {livro.anoPublicacao}
      </p>

      <p>
        <strong>Categoria:</strong>{' '}
        {livro.categoria}
      </p>

      <p>
        {livro.descricao}
      </p>

      <hr />

      <p>
        <strong>Total de exemplares:</strong>{' '}
        {livro.quantidadeExemplares}
      </p>

      <p>
        <strong>Disponíveis:</strong>{' '}
        
        <strong>Disponíveis:</strong>{' '}
        {livro.quantidadeExemplaresDisponiveis > 0 ? (

          <Link to={`/reservas/nova?livroId=${livro.id}`}>
          Reservar este livro
          </Link>

        ) : (

          <p>
            Nenhum exemplar disponível para reserva.
          </p>

        )}
      </p>

      <h3>Exemplares</h3>

      {livro.exemplares?.map(exemplar => (

        <div key={exemplar.id}>

          <div key={exemplar.id} className="exemplar">

          <span>
          {exemplar.codigo}
          </span>

          <span className={`status ${exemplar.status.toLowerCase()}`}>
          {formatarStatus(exemplar.status)}
          </span>

          </div>

        </div>

      ))}

    </div>
  )
}

export default LivroDetalhes