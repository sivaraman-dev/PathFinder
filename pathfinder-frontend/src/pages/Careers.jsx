import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { careersAPI } from '../services/api';

export default function Careers() {
  const [careers, setCareers]   = useState([]);
  const [search, setSearch]     = useState('');
  const [loading, setLoading]   = useState(true);
  const [selected, setSelected] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    careersAPI.getAll()
      .then(res => { setCareers(res.data || []); setLoading(false); })
      .catch(() => setLoading(false));
  }, []);

  const filtered = careers.filter(c =>
    (c.title || c.name || '').toLowerCase().includes(search.toLowerCase()) ||
    (c.description || '').toLowerCase().includes(search.toLowerCase())
  );

  const handleCareerClick = async (career) => {
    if (career.id) {
      try {
        const res = await careersAPI.getById(career.id);
        setSelected(res.data);
      } catch { setSelected(career); }
    } else {
      setSelected(career);
    }
  };

  const categoryColors = {
    Technology:  '#6c63ff',
    Business:    '#43e97b',
    Creative:    '#ff6584',
    Healthcare:  '#4ecdc4',
    Education:   '#f5a623',
    Engineering: '#a29bff',
  };

  return (
    <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '40px 24px' }}>
      <div className="animate-fadeUp" style={{ marginBottom: '36px' }}>
        <h1 style={{ fontFamily: 'Syne, sans-serif', fontSize: '2rem', fontWeight: 800, marginBottom: '8px' }}>
          Explore Careers
        </h1>
        <p style={{ color: 'var(--muted)' }}>Discover paths that match your unique profile.</p>
      </div>

      {/* Search */}
      <div className="animate-fadeUp-d1" style={{ marginBottom: '32px' }}>
        <input
          className="pf-input"
          placeholder="🔍  Search careers..."
          value={search}
          onChange={e => setSearch(e.target.value)}
          style={{ maxWidth: '480px' }}
        />
      </div>

      {loading ? (
        <div style={{ display: 'flex', justifyContent: 'center', padding: '80px' }}>
          <div style={{ width: '40px', height: '40px', border: '3px solid var(--border)', borderTopColor: 'var(--accent)', borderRadius: '50%', animation: 'spin-slow 0.8s linear infinite' }} />
        </div>
      ) : (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '20px' }}>
          {filtered.map((career, i) => {
            const color = categoryColors[career.category] || '#6c63ff';
            return (
              <div
                key={i}
                className="glass"
                onClick={() => handleCareerClick(career)}
                style={{
                  padding: '28px',
                  cursor: 'pointer',
                  transition: 'transform 0.2s, box-shadow 0.2s',
                  borderTop: `2px solid ${color}`,
                }}
                onMouseEnter={e => {
                  e.currentTarget.style.transform = 'translateY(-4px)';
                  e.currentTarget.style.boxShadow = `0 16px 40px rgba(0,0,0,0.3)`;
                }}
                onMouseLeave={e => {
                  e.currentTarget.style.transform = 'translateY(0)';
                  e.currentTarget.style.boxShadow = 'none';
                }}
              >
                {career.category && (
                  <span style={{
                    display: 'inline-block',
                    background: `${color}20`,
                    color,
                    borderRadius: '100px',
                    padding: '3px 12px',
                    fontSize: '0.75rem',
                    fontWeight: 600,
                    marginBottom: '14px',
                    letterSpacing: '0.06em',
                  }}>
                    {career.category}
                  </span>
                )}
                <h3 style={{ fontFamily: 'Syne, sans-serif', fontWeight: 700, fontSize: '1.1rem', marginBottom: '10px' }}>
                  {career.title || career.name}
                </h3>
                <p style={{ color: 'var(--muted)', fontSize: '0.85rem', lineHeight: 1.6, marginBottom: '16px' }}>
                  {(career.description || '').slice(0, 120)}{career.description?.length > 120 ? '...' : ''}
                </p>
                {career.averageSalary && (
                  <div style={{ display: 'flex', alignItems: 'center', gap: '6px', color: '#43e97b', fontSize: '0.85rem', fontWeight: 600 }}>
                    💰 {career.averageSalary}
                  </div>
                )}
                {career.matchScore && (
                  <div style={{ marginTop: '12px' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '6px' }}>
                      <span style={{ fontSize: '0.75rem', color: 'var(--muted)' }}>Match</span>
                      <span style={{ fontSize: '0.75rem', color, fontWeight: 600 }}>{career.matchScore}%</span>
                    </div>
                    <div style={{ height: '4px', background: 'var(--border)', borderRadius: '2px' }}>
                      <div style={{ width: `${career.matchScore}%`, height: '100%', background: color, borderRadius: '2px' }} />
                    </div>
                  </div>
                )}
              </div>
            );
          })}

          {filtered.length === 0 && (
            <div style={{ gridColumn: '1/-1', textAlign: 'center', padding: '60px', color: 'var(--muted)' }}>
              No careers found matching "{search}"
            </div>
          )}
        </div>
      )}

      {/* Career Detail Modal */}
      {selected && (
        <div
          onClick={() => setSelected(null)}
          style={{
            position: 'fixed', inset: 0,
            background: 'rgba(0,0,0,0.7)',
            backdropFilter: 'blur(8px)',
            display: 'flex', alignItems: 'center', justifyContent: 'center',
            zIndex: 200, padding: '24px',
          }}
        >
          <div
            onClick={e => e.stopPropagation()}
            className="glass"
            style={{ maxWidth: '560px', width: '100%', padding: '40px', maxHeight: '80vh', overflowY: 'auto' }}
          >
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '24px' }}>
              <h2 style={{ fontFamily: 'Syne, sans-serif', fontWeight: 800, fontSize: '1.5rem' }}>
                {selected.title || selected.name}
              </h2>
              <button onClick={() => setSelected(null)} style={{ background: 'none', border: 'none', color: 'var(--muted)', fontSize: '1.5rem', cursor: 'pointer' }}>×</button>
            </div>

            {selected.category && (
              <span style={{ display: 'inline-block', background: 'rgba(108,99,255,0.15)', color: '#a29bff', borderRadius: '100px', padding: '4px 14px', fontSize: '0.8rem', fontWeight: 600, marginBottom: '16px' }}>
                {selected.category}
              </span>
            )}

            <p style={{ color: 'var(--muted)', lineHeight: 1.7, marginBottom: '24px' }}>{selected.description}</p>

            {selected.requiredSkills && (
              <div style={{ marginBottom: '20px' }}>
                <h4 style={{ fontFamily: 'Syne, sans-serif', fontWeight: 700, marginBottom: '12px' }}>Required Skills</h4>
                <div style={{ display: 'flex', flexWrap: 'wrap', gap: '8px' }}>
                  {(Array.isArray(selected.requiredSkills) ? selected.requiredSkills : selected.requiredSkills.split(',')).map((skill, i) => (
                    <span key={i} style={{ background: 'var(--surface2)', border: '1px solid var(--border)', borderRadius: '6px', padding: '4px 12px', fontSize: '0.8rem', color: 'var(--text)' }}>
                      {skill.trim()}
                    </span>
                  ))}
                </div>
              </div>
            )}

            {selected.averageSalary && (
              <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: '#43e97b', fontWeight: 600 }}>
                💰 Average Salary: {selected.averageSalary}
              </div>
            )}

            <button
              className="pf-btn-primary"
              style={{ width: '100%', marginTop: '28px' }}
              onClick={() => { setSelected(null); navigate('/chat'); }}
            >
              Ask AI About This Career →
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
