import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';

export default function Register() {
  const [form, setForm]       = useState({ name: '', email: '', password: '', confirmPassword: '' });
  const [error, setError]     = useState('');
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleChange = e => setForm(prev => ({ ...prev, [e.target.name]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    if (form.password !== form.confirmPassword) {
      return setError("Passwords don't match.");
    }
    setLoading(true);
    try {
      const res = await authAPI.register({ name: form.name, email: form.email, password: form.password });
      const { token, user, email, name } = res.data;
      if (token) {
        login(user || { email, name }, token);
        navigate('/dashboard');
      } else {
        navigate('/login');
      }
    } catch (err) {
      const backend = err.response?.data;
      const message =
        backend?.message ||
        (typeof backend === 'string' ? backend : null) ||
        'Registration failed. Please try again.';
      setError(message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{
      minHeight: '100vh',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      padding: '24px',
      position: 'relative',
    }}>
      <div style={{
        position: 'fixed', top: '30%', right: '20%',
        width: '350px', height: '350px',
        background: 'radial-gradient(circle, rgba(255,101,132,0.1) 0%, transparent 70%)',
        pointerEvents: 'none',
      }} />

      <div className="glass animate-fadeUp" style={{ width: '100%', maxWidth: '440px', padding: '48px 40px' }}>
        <div style={{ textAlign: 'center', marginBottom: '36px' }}>
          <div style={{ fontSize: '2.5rem', marginBottom: '16px' }}>🚀</div>
          <h1 style={{ fontFamily: 'Syne, sans-serif', fontSize: '1.8rem', fontWeight: 800, marginBottom: '8px' }}>
            Start your journey
          </h1>
          <p style={{ color: 'var(--muted)', fontSize: '0.9rem' }}>Create your free PathFinder account</p>
        </div>

        <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          <div>
            <label style={{ display: 'block', marginBottom: '8px', fontSize: '0.85rem', color: 'var(--muted)', fontWeight: 500 }}>
              Full Name
            </label>
            <input className="pf-input" type="text" name="name" placeholder="Sivaraman" value={form.name} onChange={handleChange} required />
          </div>

          <div>
            <label style={{ display: 'block', marginBottom: '8px', fontSize: '0.85rem', color: 'var(--muted)', fontWeight: 500 }}>
              Email
            </label>
            <input className="pf-input" type="email" name="email" placeholder="you@example.com" value={form.email} onChange={handleChange} required />
          </div>

          <div>
            <label style={{ display: 'block', marginBottom: '8px', fontSize: '0.85rem', color: 'var(--muted)', fontWeight: 500 }}>
              Password
            </label>
            <input className="pf-input" type="password" name="password" placeholder="Min 6 characters" value={form.password} onChange={handleChange} required minLength={6} />
          </div>

          <div>
            <label style={{ display: 'block', marginBottom: '8px', fontSize: '0.85rem', color: 'var(--muted)', fontWeight: 500 }}>
              Confirm Password
            </label>
            <input className="pf-input" type="password" name="confirmPassword" placeholder="••••••••" value={form.confirmPassword} onChange={handleChange} required />
          </div>

          {error && (
            <div style={{
              background: 'rgba(255,101,132,0.1)',
              border: '1px solid rgba(255,101,132,0.3)',
              borderRadius: '8px',
              padding: '12px 16px',
              fontSize: '0.85rem',
              color: '#ff6584',
            }}>
              {error}
            </div>
          )}

          <button type="submit" className="pf-btn-primary" disabled={loading} style={{ width: '100%', marginTop: '8px', padding: '14px' }}>
            {loading ? 'Creating account...' : 'Create Account →'}
          </button>
        </form>

        <p style={{ textAlign: 'center', marginTop: '24px', fontSize: '0.9rem', color: 'var(--muted)' }}>
          Already have an account?{' '}
          <Link to="/login" style={{ color: 'var(--accent)', textDecoration: 'none', fontWeight: 600 }}>Sign in</Link>
        </p>
      </div>
    </div>
  );
}
