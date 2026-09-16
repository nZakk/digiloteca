import { Link } from 'react-router-dom'

function NotFound() {

  return (
    <div className="pagina-centralizada">

      <h2>Página não encontrada</h2>

      <p>
        O endereço acessado não existe.
      </p>

      <Link
        to="/"
        className="botao"
      >
        Voltar ao início
      </Link>

    </div>
  )
}

export default NotFound