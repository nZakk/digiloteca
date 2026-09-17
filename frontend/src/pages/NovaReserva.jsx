import { useEffect, useState } from 'react'
import { Link, useSearchParams } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

import {
  buscarLivros,
  criarReserva
} from '../services/api'

function NovaReserva() {

  const [searchParams] = useSearchParams()

  const livroIdInicial =
    searchParams.get('livroId') || ''

  const { usuario } = useAuth()
  const [livros, setLivros] = useState([])

  const [livroId, setLivroId] =
    useState(livroIdInicial)

  const [carregando, setCarregando] =
    useState(true)

  const [enviando, setEnviando] =
    useState(false)

  const [erro, setErro] = useState(null)
  const [sucesso, setSucesso] = useState(null)

  useEffect(() => {

  async function carregarDados() {

    try {

      const livrosRecebidos =
        await buscarLivros()

      setLivros(livrosRecebidos)

    } catch (error) {

      setErro(error.message)

    } finally {

      setCarregando(false)

    }

  }

  carregarDados()

}, [])

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

  if (!livroId) {

    setErro('Selecione um livro.')
    return
  }

  setEnviando(true)


    try {

      const reserva = await criarReserva({
        usuarioId: usuario.id,
        livroId: Number(livroId),
      })

      setSucesso(
        `Reserva #${reserva.id} criada com sucesso. ` +
        `Exemplar ${reserva.codigoExemplar} reservado.`
      )

    } catch (error) {

      setErro(error.message)

    } finally {

      setEnviando(false)

    }
  }

  if (carregando) {
    return <p>Carregando dados...</p>
  }

  return (
    <div>

      <Link to="/catalogo">
        ← Voltar ao catálogo
      </Link>

      <h2>Nova Reserva</h2>

      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="livro">
            Livro
          </label>

          <select
            id="livro"
            value={livroId}
            onChange={event =>
              setLivroId(event.target.value)
            }
            required
          >

            <option value="">
              Selecione um livro
            </option>

            {livros.map(livro => (

              <option
                key={livro.id}
                value={livro.id}
              >
                {livro.titulo}
              </option>

            ))}

          </select>
        </div>

        <button
          type="submit"
          disabled={enviando}
        >
          {enviando
            ? 'Reservando...'
            : 'Confirmar reserva'}
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

export default NovaReserva