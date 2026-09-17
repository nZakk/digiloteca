import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

import { loginUsuario } from '../services/api'
import { useAuth } from '../context/AuthContext'

function Login() {

  const navigate = useNavigate()
  const { entrar } = useAuth()

  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')

  const [erro, setErro] = useState(null)
  const [enviando, setEnviando] = useState(false)

  async function handleSubmit(event) {

    event.preventDefault()

    setErro(null)
    setEnviando(true)

    try {

      const usuario =
        await loginUsuario({
          email,
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

      <h2>Entrar</h2>

      <form onSubmit={handleSubmit}>

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
          <label>Senha</label>

          <input
            type="password"
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
            ? 'Entrando...'
            : 'Entrar'}
        </button>

      </form>

      {erro && (
        <p className="mensagem-erro">
          {erro}
        </p>
      )}

      <p>
        Ainda não possui conta?{' '}
        <Link to="/cadastro">
          Criar conta
        </Link>
      </p>

    </div>
  )
}

export default Login