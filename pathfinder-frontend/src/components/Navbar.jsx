import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
  const { isLoggedIn, user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const navLinks = [
    { to: '/dashboard',  label: 'Dashboard' },
    { to: '/assessment', label: 'Assessment' },
    { to: '/careers',    label: 'Careers' },
    { to: '/chat',       label: 'AI Chat' },
  ];

  return (
    <nav style={{
      background: 'rgba(10,10,15,0.85)',
      backdropFilter: 'blur(20px)',
      borderBottom: '1px solid var(--border)',
      position: 'sticky',
      top: 0,
      zIndex: 100,
    }}>
      <div style={{
        maxWidth: '1200px',
        margin: '0 auto',
        padding: '0 24px',
        height: '64px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
      }}>
        {/* Logo */}
        <Link to="/" style={{ textDecoration: 'none' }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
            <div style={{
              width: '32px', height: '32px',
              background: 'linear-gradient(135deg, #6c63ff, #ff6584)',
              borderRadius: '8px',
              display: 'flex', alignItems: 'center', justifyContent: 'center',
              fontSize: '16px',
            }}>🧭</div>
            <span style={{
              fontFamily: 'Syne, sans-serif',
              fontWeight: 800,
              fontSize: '1.2rem',
              color: 'var(--text)',
            }}>PathFinder</span>
          </div>
        </Link>

        {/* Nav Links */}
        {isLoggedIn && (
          <div style={{ display: 'flex', gap: '4px' }}>
            {navLinks.map(link => (
              <Link key={link.to} to={link.to} style={{
                textDecoration: 'none',
                padding: '6px 16px',
                borderRadius: '8px',
                fontSize: '0.9rem',
                fontWeight: 500,
                color: location.pathname === link.to ? 'white' : 'var(--muted)',
                background: location.pathname === link.to ? 'rgba(108,99,255,0.2)' : 'transparent',
                transition: 'all 0.2s',
              }}>
                {link.label}
              </Link>
            ))}
          </div>
        )}

        {/* Auth area */}
        <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
          {isLoggedIn ? (
            <>
              <span style={{
                fontSize: '0.85rem',
                color: 'var(--muted)',
                fontWeight: 500,
              }}>
                👋 {user?.name || user?.email?.split('@')[0] || 'User'}
              </span>
              <button onClick={handleLogout} className="pf-btn-ghost" style={{ padding: '8px 18px', fontSize: '0.85rem' }}>
                Logout
              </button>
            </>
          ) : (
            <>
              <Link to="/login"><button className="pf-btn-ghost" style={{ padding: '8px 18px', fontSize: '0.85rem' }}>Login</button></Link>
              <Link to="/register"><button className="pf-btn-primary" style={{ padding: '8px 18px', fontSize: '0.85rem' }}>Sign Up</button></Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}
