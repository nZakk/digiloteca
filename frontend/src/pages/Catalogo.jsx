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

      <div className="grid-cards">

  {livros.map(livro => (

    <article
      key={livro.id}
      className="card"
    >

      <span className="categoria">
        {livro.categoria}
      </span>

      <h3>
        {livro.titulo}
      </h3>

      <p>
        {livro.autor}
      </p>

      <Link
        to={`/livros/${livro.id}`}
        className="botao-link"
      >
        Ver detalhes
      </Link>

    </article>

  ))}

</div>

    </div>
  )
}

export default Catalogo