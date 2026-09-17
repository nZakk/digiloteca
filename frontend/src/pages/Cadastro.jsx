import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

import { cadastrarUsuario } from '../services/api'
import { useAuth } from '../context/AuthContext'

function Cadastro() {

  const navigate = useNavigate()
  const { entrar } = useAuth()

  const [nome, setNome] = useState('')
  const [email, setEmail] = useState('')
  const [telefone, setTelefone] = useState('')
  const [senha, setSenha] = useState('')

  const [erro, setErro] = useState(null)
  const [enviando, setEnviando] = useState(false)

  async function handleSubmit(event) {

    event.preventDefault()

    setErro(null)
    setEnviando(true)

    try {

      const usuario =
        await cadastrarUsuario({
          nome,
          email,
          telefone,
          senha,
        })

      entrar(usuario)

      navigate('/')

    } catch (error) {

      setErro(error.message)

    } finally {

      setEnviando(false)

    }
  }

  return (
    <div>

      <h2>Criar conta</h2>

      <form onSubmit={handleSubmit}>

        <div>
          <label>Nome</label>

          <input
            value={nome}
            onChange={event =>
              setNome(event.target.value)
            }
            required
          />
        </div>

        <div>
          <label>Email</label>

          <input
            type="email"
            value={email}
            onChange={event =>
              setEmail(event.target.value)
            }
            required
          />
        </div>

        <div>
          <label>Telefone</label>

          <input
            value={telefone}
            onChange={event =>
              setTelefone(event.target.value)
            }
          />
        </div>

        <div>
          <label>Senha</label>

          <input
            type="password"
            minLength="6"
            value={senha}
            onChange={event =>
              setSenha(event.target.value)
            }
            required
          />
        </div>

        <button
          type="submit"
          disabled={enviando}
        >
          {enviando
            ? 'Criando...'
            : 'Criar conta'}
        </button>

      </form>

      {erro && (
        <p className="mensagem-erro">
          {erro}
        </p>
      )}

      <p>
        Já possui conta?{' '}
        <Link to="/login">
          Entrar
        </Link>
      </p>

    </div>
  )
}

export default Cadastro