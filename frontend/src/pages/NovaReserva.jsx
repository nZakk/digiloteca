import { useEffect, useState } from 'react'
import { Link, useSearchParams } from 'react-router-dom'

import {
  buscarLivros,
  buscarUsuarios,
  criarReserva
} from '../services/api'

function NovaReserva() {

  const [searchParams] = useSearchParams()

  const livroIdInicial =
    searchParams.get('livroId') || ''

  const [usuarios, setUsuarios] = useState([])
  const [livros, setLivros] = useState([])

  const [usuarioId, setUsuarioId] = useState('')
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

        const [
          usuariosRecebidos,
          livrosRecebidos
        ] = await Promise.all([
          buscarUsuarios(),
          buscarLivros()
        ])

        setUsuarios(
          usuariosRecebidos.filter(
            usuario => usuario.ativo
          )
        )

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
    setEnviando(true)

    try {

      const reserva = await criarReserva({
        usuarioId: Number(usuarioId),
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
          <label htmlFor="usuario">
            Usuário
          </label>

          <select
            id="usuario"
            value={usuarioId}
            onChange={event =>
              setUsuarioId(event.target.value)
            }
            required
          >

            <option value="">
              Selecione um usuário
            </option>

            {usuarios.map(usuario => (

              <option
                key={usuario.id}
                value={usuario.id}
              >
                {usuario.nome}
              </option>

            ))}

          </select>
        </div>

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