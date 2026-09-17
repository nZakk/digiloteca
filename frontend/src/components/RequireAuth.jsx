import { Navigate } from 'react-router-dom'

import { useAuth } from '../context/AuthContext'

function RequireAuth({ children }) {

  const { usuario } = useAuth()

  if (!usuario) {
    return <Navigate to="/login" replace />
  }

  return children
}

export default RequireAuth