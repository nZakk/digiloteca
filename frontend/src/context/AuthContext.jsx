import {
  createContext,
  useContext,
  useState
} from 'react'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {

  const [usuario, setUsuario] = useState(() => {

    const salvo =
      localStorage.getItem('usuario')

    return salvo
      ? JSON.parse(salvo)
      : null
  })

  function entrar(usuarioRecebido) {

    setUsuario(usuarioRecebido)

    localStorage.setItem(
      'usuario',
      JSON.stringify(usuarioRecebido)
    )
  }

  function sair() {

    setUsuario(null)

    localStorage.removeItem('usuario')
  }

  return (
    <AuthContext.Provider
      value={{
        usuario,
        entrar,
        sair,
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  return useContext(AuthContext)
}