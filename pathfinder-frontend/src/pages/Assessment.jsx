import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { questionsAPI, assessmentAPI } from '../services/api';

export default function Assessment() {
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers]     = useState({});
  const [current, setCurrent]     = useState(0);
  const [preferredArea, setPreferredArea] = useState('');
  const [loading, setLoading]     = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError]         = useState('');
  const navigate = useNavigate();

  const AREA_OPTIONS = [
    { value: 'technical', label: 'Technical (IT, Programming, Data)', icon: '💻' },
    { value: 'creative', label: 'Creative (Design, Arts, Media)', icon: '🎨' },
    { value: 'business', label: 'Business & Finance', icon: '📊' },
    { value: 'healthcare', label: 'Healthcare', icon: '🏥' },
    { value: 'other', label: 'Other / Not sure yet', icon: '✨' },
  ];

  useEffect(() => {
    questionsAPI.getAll()
      .then(res => {
        setQuestions(res.data || []);
        setLoading(false);
      })
      .catch(() => {
        setError('Failed to load questions. Please try again.');
        setLoading(false);
      });
  }, []);

  const currentQ  = questions[current];
  const progress  = questions.length ? ((current) / questions.length) * 100 : 0;
  const isAnswered = currentQ && answers[currentQ.id] !== undefined;

  const handleAnswer = (questionId, value) => {
    setAnswers(prev => ({ ...prev, [questionId]: value }));
  };

  const handleNext = () => {
    if (current < questions.length - 1) {
      setCurrent(prev => prev + 1);
    }
  };

  const handlePrev = () => {
    if (current > 0) setCurrent(prev => prev - 1);
  };

  const handleSubmit = async () => {
    setSubmitting(true);
    try {
      const payloadAnswers = {};
      questions.forEach((q) => {
        const val = answers[q.id];
        if (val !== undefined) {
          const key = `${q.category || 'general'}_${q.id}`;
          payloadAnswers[key] = val;
        }
      });
      await assessmentAPI.submit({ answers: payloadAnswers, preferredArea: preferredArea || undefined });
      navigate('/dashboard');
    } catch (err) {
      const backend = err.response?.data;
      const message =
        backend?.message ||
        (typeof backend === 'string' ? backend : null) ||
        'Submission failed. Please try again.';
      setError(message);
      setSubmitting(false);
    }
  };

  // Loading state
  if (loading) return (
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', minHeight: '60vh', flexDirection: 'column', gap: '16px' }}>
      <div style={{
        width: '48px', height: '48px',
        border: '3px solid var(--border)',
        borderTopColor: 'var(--accent)',
        borderRadius: '50%',
        animation: 'spin-slow 0.8s linear infinite',
      }} />
      <p style={{ color: 'var(--muted)' }}>Loading your questions...</p>
    </div>
  );

  if (error) return (
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', minHeight: '60vh' }}>
      <div className="glass" style={{ padding: '40px', textAlign: 'center', maxWidth: '400px' }}>
        <div style={{ fontSize: '3rem', marginBottom: '16px' }}>⚠️</div>
        <p style={{ color: '#ff6584', marginBottom: '20px' }}>{error}</p>
        <button className="pf-btn-primary" onClick={() => window.location.reload()}>Try Again</button>
      </div>
    </div>
  );

  if (!questions.length) return (
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', minHeight: '60vh' }}>
      <div className="glass" style={{ padding: '40px', textAlign: 'center', maxWidth: '400px' }}>
        <div style={{ fontSize: '3rem', marginBottom: '16px' }}>📭</div>
        <p style={{ color: 'var(--muted)' }}>No questions available yet.</p>
      </div>
    </div>
  );

  return (
    <div style={{ maxWidth: '760px', margin: '0 auto', padding: '48px 24px' }}>
      {/* Header */}
      <div className="animate-fadeUp" style={{ marginBottom: '40px', textAlign: 'center' }}>
        <h1 style={{ fontFamily: 'Syne, sans-serif', fontSize: '2rem', fontWeight: 800, marginBottom: '8px' }}>
          Career Assessment
        </h1>
        <p style={{ color: 'var(--muted)' }}>Answer honestly — there are no wrong answers.</p>
      </div>

      {/* Progress bar */}
      <div className="animate-fadeUp-d1" style={{ marginBottom: '36px' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '10px' }}>
          <span style={{ color: 'var(--muted)', fontSize: '0.85rem' }}>
            Question {current + 1} of {questions.length}
          </span>
          <span style={{ color: 'var(--accent)', fontSize: '0.85rem', fontWeight: 600 }}>
            {Math.round(progress)}% complete
          </span>
        </div>
        <div style={{ height: '6px', background: 'var(--border)', borderRadius: '3px', overflow: 'hidden' }}>
          <div style={{
            width: `${progress}%`,
            height: '100%',
            background: 'linear-gradient(90deg, #6c63ff, #ff6584)',
            borderRadius: '3px',
            transition: 'width 0.4s ease',
          }} />
        </div>
      </div>

      {/* Question card */}
      <div key={current} className="glass animate-fadeUp" style={{ padding: '40px', marginBottom: '28px' }}>
        {currentQ?.category && (
          <div style={{
            display: 'inline-block',
            background: 'rgba(108,99,255,0.12)',
            border: '1px solid rgba(108,99,255,0.2)',
            borderRadius: '100px',
            padding: '4px 14px',
            fontSize: '0.75rem',
            color: '#a29bff',
            marginBottom: '20px',
            textTransform: 'uppercase',
            letterSpacing: '0.07em',
            fontWeight: 600,
          }}>
            {currentQ.category}
          </div>
        )}

        <h2 style={{
          fontFamily: 'Syne, sans-serif',
          fontSize: '1.3rem',
          fontWeight: 700,
          lineHeight: 1.5,
          marginBottom: '32px',
        }}>
          {currentQ?.questionText || currentQ?.text || currentQ?.question}
        </h2>

        {/* Options */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
          {(currentQ?.options || ['Strongly Agree', 'Agree', 'Neutral', 'Disagree', 'Strongly Disagree']).map((opt, i) => {
            const label    = typeof opt === 'object' ? opt.label : opt;
            const value    = i + 1; // numeric scale 1-5
            const selected = answers[currentQ.id] === value;

            return (
              <button
                key={i}
                onClick={() => handleAnswer(currentQ.id, value)}
                style={{
                  background: selected ? 'rgba(108,99,255,0.2)' : 'var(--surface2)',
                  border: `1px solid ${selected ? 'var(--accent)' : 'var(--border)'}`,
                  borderRadius: '10px',
                  padding: '14px 20px',
                  color: selected ? 'white' : 'var(--muted)',
                  textAlign: 'left',
                  cursor: 'pointer',
                  fontSize: '0.95rem',
                  transition: 'all 0.15s',
                  fontFamily: 'DM Sans, sans-serif',
                }}
                onMouseEnter={e => { if (!selected) { e.currentTarget.style.borderColor = 'rgba(108,99,255,0.5)'; e.currentTarget.style.color = 'var(--text)'; }}}
                onMouseLeave={e => { if (!selected) { e.currentTarget.style.borderColor = 'var(--border)'; e.currentTarget.style.color = 'var(--muted)'; }}}
              >
                {label}
              </button>
            );
          })}
        </div>

        {/* Career area interest — shown on last question so recommendations match your interest */}
        {current === questions.length - 1 && (
          <div style={{
            marginTop: '32px',
            paddingTop: '24px',
            borderTop: '1px solid var(--border)',
          }}>
            <p style={{ fontWeight: 600, marginBottom: '12px', color: 'var(--text)' }}>
              Which career area interests you most?
            </p>
            <p style={{ color: 'var(--muted)', fontSize: '0.9rem', marginBottom: '16px' }}>
              This helps us recommend careers that fit your interest (e.g. technical roles like Software Developer, Data Scientist).
            </p>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px' }}>
              {AREA_OPTIONS.map((opt) => {
                const selected = preferredArea === opt.value;
                return (
                  <button
                    key={opt.value}
                    type="button"
                    onClick={() => setPreferredArea(opt.value)}
                    style={{
                      background: selected ? 'rgba(108,99,255,0.2)' : 'var(--surface2)',
                      border: `1px solid ${selected ? 'var(--accent)' : 'var(--border)'}`,
                      borderRadius: '10px',
                      padding: '12px 16px',
                      color: selected ? 'white' : 'var(--muted)',
                      cursor: 'pointer',
                      fontSize: '0.9rem',
                      display: 'flex',
                      alignItems: 'center',
                      gap: '8px',
                    }}
                  >
                    <span>{opt.icon}</span>
                    <span>{opt.label}</span>
                  </button>
                );
              })}
            </div>
          </div>
        )}
      </div>

      {/* Navigation */}
      <div style={{ display: 'flex', justifyContent: 'space-between', gap: '12px' }}>
        <button
          className="pf-btn-ghost"
          onClick={handlePrev}
          disabled={current === 0}
          style={{ opacity: current === 0 ? 0.3 : 1 }}
        >
          ← Previous
        </button>

        {current < questions.length - 1 ? (
          <button
            className="pf-btn-primary"
            onClick={handleNext}
            disabled={!isAnswered}
          >
            Next →
          </button>
        ) : (
          <button
            className="pf-btn-primary glow-btn"
            onClick={handleSubmit}
            disabled={submitting || !isAnswered}
          >
            {submitting ? 'Submitting...' : 'Submit & See Results ✓'}
          </button>
        )}
      </div>
    </div>
  );
}
