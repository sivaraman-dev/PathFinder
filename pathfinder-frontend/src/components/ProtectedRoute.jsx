import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function ProtectedRoute({ children }) {
  const { isLoggedIn, ready } = useAuth();
  if (!ready) return null;
  return isLoggedIn ? children : <Navigate to="/login" replace />;
}
