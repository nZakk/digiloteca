const API_URL = '/api'

export async function buscarLivros() {

  const response = await fetch(`${API_URL}/livros`)

  if (!response.ok) {
    throw new Error('Não foi possível carregar os livros.')
  }

  return response.json()
}

export async function buscarLivroPorId(id) {

  const response = await fetch(`${API_URL}/livros/${id}`)

  if (!response.ok) {
    throw new Error('Não foi possível carregar o livro.')
  }

  return response.json()
}

export async function buscarProximosEventos() {

  const response = await fetch(`${API_URL}/eventos/proximos`)

  if (!response.ok) {
    throw new Error('Não foi possível carregar os eventos.')
  }

  return response.json()
}

async function processarResposta(response) {

  if (response.ok) {
    return response.json()
  }

  let mensagem = 'Ocorreu um erro inesperado.'

  try {

    const erro = await response.json()

    if (erro.mensagem) {
      mensagem = erro.mensagem
    }

  } catch {
    // mantém a mensagem padrão
  }

  throw new Error(mensagem)
}

export async function buscarUsuarios() {

  const response = await fetch(
    `${API_URL}/usuarios`
  )

  return processarResposta(response)
}

export async function criarReserva(dados) {

  const response = await fetch(
    `${API_URL}/reservas`,
    {
      method: 'POST',

      headers: {
        'Content-Type': 'application/json',
      },

      body: JSON.stringify(dados),
    }
  )

  return processarResposta(response)
}

export async function criarDoacao(dados) {

  const response = await fetch(
    `${API_URL}/doacoes`,
    {
      method: 'POST',

      headers: {
        'Content-Type': 'application/json',
      },

      body: JSON.stringify(dados),
    }
  )

  return processarResposta(response)
}