import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

import {
  criarDoacao
} from '../services/api'

function NovaDoacao() {

  const { usuario } = useAuth()
  const [tituloLivro, setTituloLivro] = useState('')
  const [autor, setAutor] = useState('')
  const [isbn, setIsbn] = useState('')
  const [quantidade, setQuantidade] = useState(1)
  const [categorias, setCategorias] = useState('')
  const [detalhes, setDetalhes] = useState('')

  const [carregando, setCarregando] = useState(true)
  const [enviando, setEnviando] = useState(false)

  const [erro, setErro] = useState(null)
  const [sucesso, setSucesso] = useState(null)

  useEffect(() => {setCarregando(false)}, [])

  async function handleSubmit(event) {
  event.preventDefault()

  setErro(null)
  setSucesso(null)

  if (!usuario?.id) {
    setErro(
      'Não foi possível identificar o usuário logado. Faça login novamente.'
    )
    return
  }

  setEnviando(true)


    try {

      const doacao = await criarDoacao({
        usuarioId: usuario.id,
        tituloLivro,
        autor,
        isbn,
        quantidade: Number(quantidade),
        categorias,
        detalhes,
      })

      setSucesso(
        `Doação #${doacao.id} registrada com sucesso. ` +
        `Status: ${doacao.status}.`
      )

      setTituloLivro('')
      setAutor('')
      setIsbn('')
      setQuantidade(1)
      setCategorias('')
      setDetalhes('')

    } catch (error) {

      setErro(error.message)

    } finally {

      setEnviando(false)

    }
  }

  if (carregando) {
    return <p>Carregando...</p>
  }

  return (
    <div>

      <Link to="/">
        ← Voltar ao início
      </Link>

      <h2>Doar Livros</h2>

      <p>
        Preencha os dados abaixo para oferecer
        livros à biblioteca comunitária.
      </p>

      <form onSubmit={handleSubmit}>

        <div>
          <label htmlFor="titulo">
            Título do livro
          </label>

          <input
            id="titulo"
            type="text"
            value={tituloLivro}
            onChange={event =>
              setTituloLivro(event.target.value)
            }
            required
          />
        </div>

        <div>
          <label htmlFor="autor">
            Autor
          </label>

          <input
            id="autor"
            type="text"
            value={autor}
            onChange={event =>
              setAutor(event.target.value)
            }
            required
          />
        </div>

        <div>
          <label htmlFor="isbn">
            ISBN
          </label>

          <input
            id="isbn"
            type="text"
            value={isbn}
            onChange={event =>
              setIsbn(event.target.value)
            }
          />
        </div>

        <div>
          <label htmlFor="quantidade">
            Quantidade
          </label>

          <input
            id="quantidade"
            type="number"
            min="1"
            value={quantidade}
            onChange={event =>
              setQuantidade(event.target.value)
            }
            required
          />
        </div>

        <div>
          <label htmlFor="categorias">
            Categorias
          </label>

          <input
            id="categorias"
            type="text"
            value={categorias}
            onChange={event =>
              setCategorias(event.target.value)
            }
          />
        </div>

        <div>
          <label htmlFor="detalhes">
            Detalhes
          </label>

          <textarea
            id="detalhes"
            value={detalhes}
            onChange={event =>
              setDetalhes(event.target.value)
            }
          />
        </div>

        <button
          type="submit"
          disabled={enviando}
        >
          {enviando
            ? 'Enviando...'
            : 'Enviar doação'}
        </button>

      </form>

      {sucesso && (
        <p className="mensagem-sucesso">
          {sucesso}
        </p>
      )}

      {erro && (
        <p className="mensagem-erro">
          {erro}
        </p>
      )}

    </div>
  )
}

export default NovaDoacao