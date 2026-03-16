import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Radar, Doughnut } from 'react-chartjs-2';
import {
  Chart as ChartJS, RadialLinearScale, PointElement, LineElement,
  Filler, Tooltip, Legend, ArcElement,
} from 'chart.js';
import { assessmentAPI } from '../services/api';
import { useAuth } from '../context/AuthContext';

ChartJS.register(RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend, ArcElement);

export default function Dashboard() {
  const { user } = useAuth();
  const [assessment, setAssessment] = useState(null);
  const [careers, setCareers]       = useState([]);
  const [loading, setLoading]       = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const aRes = await Promise.allSettled([assessmentAPI.latest()]);
        const res = aRes[0];
        if (res.status === 'fulfilled' && res.value?.data) {
          setAssessment(res.value.data);
          // Use personalized recommendations from assessment (top 3), not full career list
          const recs = res.value.data?.recommendations || [];
          setCareers(recs.map(r => ({ ...r, matchScore: r.matchPercentage ?? r.matchScore })));
        }
      } catch (_) {}
      finally { setLoading(false); }
    };
    fetchData();
  }, []);

  const radarData = {
    labels: assessment?.categories
      ? Object.keys(assessment.categories)
      : ['Technical', 'Creative', 'Leadership', 'Analytical', 'Communication', 'Problem Solving'],
    datasets: [{
      label: 'Your Skills',
      data: assessment?.categories
        ? Object.values(assessment.categories)
        : [70, 55, 65, 80, 60, 75],
      backgroundColor: 'rgba(108,99,255,0.2)',
      borderColor: '#6c63ff',
      borderWidth: 2,
      pointBackgroundColor: '#6c63ff',
      pointRadius: 4,
    }],
  };

  const doughnutData = {
    labels: ['Technical', 'Soft Skills', 'Creative', 'Other'],
    datasets: [{
      data: [35, 25, 25, 15],
      backgroundColor: ['#6c63ff', '#43e97b', '#ff6584', '#f5a623'],
      borderWidth: 0,
      hoverOffset: 6,
    }],
  };

  const chartOptions = {
    plugins: { legend: { labels: { color: '#e8e8f0', font: { family: 'DM Sans' } } } },
    scales: {
      r: {
        ticks: { color: '#6b6b80', backdropColor: 'transparent' },
        grid: { color: 'rgba(255,255,255,0.05)' },
        pointLabels: { color: '#e8e8f0', font: { family: 'DM Sans', size: 12 } },
      },
    },
  };

  const doughnutOptions = {
    plugins: { legend: { labels: { color: '#e8e8f0', font: { family: 'DM Sans' } } } },
    cutout: '65%',
  };

  const stats = [
    { label: 'Assessments Taken', value: assessment ? '1' : '0', icon: '📋', color: '#6c63ff' },
    { label: 'Top Matches',       value: careers.length,          icon: '🎯', color: '#43e97b' },
    { label: 'Skill Score',        value: assessment?.score ? `${assessment.score}%` : 'N/A', icon: '⚡', color: '#ff6584' },
    { label: 'Profile Complete',   value: assessment ? '100%' : '60%', icon: '✅', color: '#f5a623' },
  ];

  return (
    <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '40px 24px' }}>
      {/* Header */}
      <div className="animate-fadeUp" style={{ marginBottom: '40px' }}>
        <h1 style={{ fontFamily: 'Syne, sans-serif', fontSize: '2rem', fontWeight: 800, marginBottom: '8px' }}>
          Welcome back, <span className="gradient-text">{user?.name || 'Explorer'}</span> 👋
        </h1>
        <p style={{ color: 'var(--muted)' }}>Here's your career journey overview.</p>
      </div>

      {/* Stats */}
      <div className="animate-fadeUp-d1" style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit, minmax(220px, 1fr))',
        gap: '16px',
        marginBottom: '32px',
      }}>
        {stats.map((s, i) => (
          <div key={i} className="glass" style={{ padding: '24px' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
              <div>
                <p style={{ color: 'var(--muted)', fontSize: '0.8rem', marginBottom: '8px', textTransform: 'uppercase', letterSpacing: '0.06em' }}>{s.label}</p>
                <p style={{ fontFamily: 'Syne, sans-serif', fontSize: '2rem', fontWeight: 800, color: s.color }}>{s.value}</p>
              </div>
              <span style={{ fontSize: '1.8rem' }}>{s.icon}</span>
            </div>
          </div>
        ))}
      </div>

      {/* Charts */}
      {!loading && (
        <div className="animate-fadeUp-d2" style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fit, minmax(340px, 1fr))',
          gap: '20px',
          marginBottom: '32px',
        }}>
          <div className="glass" style={{ padding: '28px' }}>
            <h3 style={{ fontFamily: 'Syne, sans-serif', fontWeight: 700, marginBottom: '20px' }}>Skill Profile</h3>
            <Radar data={radarData} options={chartOptions} />
          </div>
          <div className="glass" style={{ padding: '28px' }}>
            <h3 style={{ fontFamily: 'Syne, sans-serif', fontWeight: 700, marginBottom: '20px' }}>Strength Breakdown</h3>
            <Doughnut data={doughnutData} options={doughnutOptions} />
          </div>
        </div>
      )}

      {/* CTA if no assessment */}
      {!assessment && !loading && (
        <div className="glass animate-fadeUp-d3" style={{
          padding: '40px',
          textAlign: 'center',
          background: 'linear-gradient(135deg, rgba(108,99,255,0.08), rgba(255,101,132,0.08))',
          borderColor: 'rgba(108,99,255,0.3)',
        }}>
          <div style={{ fontSize: '3rem', marginBottom: '16px' }}>🎯</div>
          <h2 style={{ fontFamily: 'Syne, sans-serif', fontWeight: 800, fontSize: '1.5rem', marginBottom: '12px' }}>
            Take Your Assessment
          </h2>
          <p style={{ color: 'var(--muted)', marginBottom: '24px' }}>
            Discover your strengths and get personalized career recommendations.
          </p>
          <Link to="/assessment">
            <button className="pf-btn-primary glow-btn" style={{ padding: '14px 36px' }}>
              Start Now →
            </button>
          </Link>
        </div>
      )}

      {/* Careers preview */}
      {careers.length > 0 && (
        <div className="animate-fadeUp-d4" style={{ marginTop: '32px' }}>
          <div style={{ marginBottom: '20px' }}>
            <h2 style={{ fontFamily: 'Syne, sans-serif', fontWeight: 700, fontSize: '1.3rem', marginBottom: '4px' }}>Your top 3 career matches</h2>
            <p style={{ color: 'var(--muted)', fontSize: '0.9rem', marginBottom: '12px' }}>Based on your assessment — these fit your profile best. Explore more options in Careers.</p>
            <Link to="/careers" style={{ color: 'var(--accent)', textDecoration: 'none', fontSize: '0.9rem' }}>Explore all careers →</Link>
          </div>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))', gap: '16px' }}>
            {careers.map((career, i) => (
              <div key={i} className="glass" style={{ padding: '20px', cursor: 'pointer', transition: 'transform 0.2s' }}
                onMouseEnter={e => e.currentTarget.style.transform = 'translateY(-2px)'}
                onMouseLeave={e => e.currentTarget.style.transform = 'translateY(0)'}
              >
                <h4 style={{ fontFamily: 'Syne, sans-serif', fontWeight: 700, marginBottom: '8px' }}>{career.title || career.name}</h4>
                <p style={{ color: 'var(--muted)', fontSize: '0.85rem', lineHeight: 1.5 }}>
                  {career.description?.slice(0, 100)}...
                </p>
                {career.matchScore && (
                  <div style={{ marginTop: '12px', display: 'flex', alignItems: 'center', gap: '8px' }}>
                    <div style={{ flex: 1, height: '4px', background: 'var(--border)', borderRadius: '2px' }}>
                      <div style={{ width: `${career.matchScore}%`, height: '100%', background: '#43e97b', borderRadius: '2px' }} />
                    </div>
                    <span style={{ fontSize: '0.8rem', color: '#43e97b', fontWeight: 600 }}>{career.matchScore}%</span>
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
