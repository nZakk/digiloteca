import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

import { buscarLivros } from '../services/api'

function Catalogo() {

  const [livros, setLivros] = useState([])
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState(null)

  useEffect(() => {

    async function carregarLivros() {

      try {

        const dados =
          await buscarLivros()

        setLivros(dados)

      } catch (error) {

        setErro(error.message)

      } finally {

        setCarregando(false)

      }
    }

    carregarLivros()

  }, [])

  if (carregando) {
    return <p>Carregando catálogo...</p>
  }

  if (erro) {
    return <p>{erro}</p>
  }

  return (
    <div>

      <h2>Catálogo</h2>

      {livros.length === 0 && (
        <p>
          Nenhum livro cadastrado.
        </p>
      )}

      {livros.map(livro => (

        <article key={livro.id}>

          <h3>
            {livro.titulo}
          </h3>

          <p>
            <strong>Autor:</strong>{' '}
            {livro.autor}
          </p>

          <p>
            <strong>Categoria:</strong>{' '}
            {livro.categoria}
          </p>

          <Link to={`/livros/${livro.id}`}>
            Ver detalhes
          </Link>

        </article>

      ))}

    </div>
  )
}

export default Catalogo