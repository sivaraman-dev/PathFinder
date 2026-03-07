import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const features = [
  { icon: '🎯', title: 'Smart Assessment', desc: 'Answer curated questions to discover your strengths, interests, and working style.' },
  { icon: '🤖', title: 'AI-Powered Matching', desc: 'Our AI engine analyses your profile to recommend the most fitting career paths.' },
  { icon: '💬', title: 'Career AI Chat', desc: 'Ask anything about careers, skills, or next steps — get instant expert guidance.' },
  { icon: '📊', title: 'Visual Analytics', desc: 'See your results in beautiful charts that make self-discovery effortless.' },
];

export default function Home() {
  const { isLoggedIn } = useAuth();

  return (
    <div style={{ minHeight: '100vh', position: 'relative', overflow: 'hidden' }}>
      {/* Background glow orbs */}
      <div style={{
        position: 'fixed', top: '-20%', left: '-10%',
        width: '600px', height: '600px',
        background: 'radial-gradient(circle, rgba(108,99,255,0.15) 0%, transparent 70%)',
        pointerEvents: 'none',
      }} />
      <div style={{
        position: 'fixed', bottom: '-20%', right: '-10%',
        width: '500px', height: '500px',
        background: 'radial-gradient(circle, rgba(255,101,132,0.12) 0%, transparent 70%)',
        pointerEvents: 'none',
      }} />

      {/* Hero */}
      <section style={{
        maxWidth: '1200px',
        margin: '0 auto',
        padding: '100px 24px 80px',
        textAlign: 'center',
        position: 'relative',
        zIndex: 1,
      }}>
        <div className="animate-fadeUp" style={{
          display: 'inline-block',
          background: 'rgba(108,99,255,0.12)',
          border: '1px solid rgba(108,99,255,0.3)',
          borderRadius: '100px',
          padding: '6px 18px',
          fontSize: '0.8rem',
          color: '#a29bff',
          marginBottom: '28px',
          letterSpacing: '0.08em',
          textTransform: 'uppercase',
          fontWeight: 600,
        }}>
          ✦ AI-Powered Career Guidance
        </div>

        <h1 className="animate-fadeUp-d1" style={{
          fontFamily: 'Syne, sans-serif',
          fontSize: 'clamp(2.8rem, 7vw, 5.5rem)',
          fontWeight: 800,
          lineHeight: 1.05,
          marginBottom: '28px',
          color: 'var(--text)',
        }}>
          Find Your{' '}
          <span className="gradient-text">Perfect</span>
          <br />Career Path
        </h1>

        <p className="animate-fadeUp-d2" style={{
          fontSize: '1.15rem',
          color: 'var(--muted)',
          maxWidth: '540px',
          margin: '0 auto 44px',
          lineHeight: 1.7,
        }}>
          Take a smart assessment, get AI-powered career recommendations, and chat with our AI mentor — all in one place.
        </p>

        <div className="animate-fadeUp-d3" style={{ display: 'flex', gap: '14px', justifyContent: 'center', flexWrap: 'wrap' }}>
          {isLoggedIn ? (
            <Link to="/assessment">
              <button className="pf-btn-primary glow-btn" style={{ fontSize: '1rem', padding: '14px 36px' }}>
                Start Assessment →
              </button>
            </Link>
          ) : (
            <>
              <Link to="/register">
                <button className="pf-btn-primary glow-btn" style={{ fontSize: '1rem', padding: '14px 36px' }}>
                  Get Started Free →
                </button>
              </Link>
              <Link to="/login">
                <button className="pf-btn-ghost" style={{ fontSize: '1rem', padding: '14px 36px' }}>
                  Sign In
                </button>
              </Link>
            </>
          )}
        </div>
      </section>

      {/* Features */}
      <section style={{
        maxWidth: '1200px',
        margin: '0 auto',
        padding: '40px 24px 100px',
        position: 'relative',
        zIndex: 1,
      }}>
        <div style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fit, minmax(260px, 1fr))',
          gap: '20px',
        }}>
          {features.map((f, i) => (
            <div key={i} className="glass animate-fadeUp" style={{
              padding: '32px',
              animationDelay: `${i * 0.1}s`,
              transition: 'transform 0.2s, box-shadow 0.2s',
              cursor: 'default',
            }}
              onMouseEnter={e => {
                e.currentTarget.style.transform = 'translateY(-4px)';
                e.currentTarget.style.boxShadow = '0 20px 40px rgba(0,0,0,0.3)';
              }}
              onMouseLeave={e => {
                e.currentTarget.style.transform = 'translateY(0)';
                e.currentTarget.style.boxShadow = 'none';
              }}
            >
              <div style={{ fontSize: '2rem', marginBottom: '16px' }}>{f.icon}</div>
              <h3 style={{ fontFamily: 'Syne, sans-serif', fontWeight: 700, marginBottom: '10px', fontSize: '1.1rem' }}>{f.title}</h3>
              <p style={{ color: 'var(--muted)', lineHeight: 1.6, fontSize: '0.9rem' }}>{f.desc}</p>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}
