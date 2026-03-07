import { useState, useRef, useEffect } from 'react';
import { chatAPI } from '../services/api';

const suggestions = [
  'What careers suit me based on my assessment?',
  'How do I become a software engineer?',
  'What skills should I learn for data science?',
  'Compare UX designer vs product manager roles',
];

export default function Chat() {
  const [messages, setMessages] = useState([
    {
      role: 'assistant',
      content: "Hi! I'm your PathFinder AI mentor 🧭 I can help you explore career paths, understand skill requirements, and guide your professional journey. What would you like to know?",
    },
  ]);
  const [input, setInput]       = useState('');
  const [loading, setLoading]   = useState(false);
  const bottomRef = useRef(null);
  const inputRef  = useRef(null);

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  const sendMessage = async (text) => {
    const userText = text || input.trim();
    if (!userText || loading) return;

    const userMsg = { role: 'user', content: userText };
    setMessages(prev => [...prev, userMsg]);
    setInput('');
    setLoading(true);

    try {
      const res = await chatAPI.sendMessage({ message: userText });
      const reply = res.data?.reply || res.data?.message || res.data?.content || 'I received your message!';
      setMessages(prev => [...prev, { role: 'assistant', content: reply }]);
    } catch (err) {
      setMessages(prev => [...prev, {
        role: 'assistant',
        content: 'Sorry, I had trouble responding. Please try again.',
        error: true,
      }]);
    } finally {
      setLoading(false);
      inputRef.current?.focus();
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  };

  return (
    <div style={{
      maxWidth: '800px',
      margin: '0 auto',
      padding: '32px 24px',
      height: 'calc(100vh - 80px)',
      display: 'flex',
      flexDirection: 'column',
    }}>
      {/* Header */}
      <div className="animate-fadeUp" style={{ marginBottom: '24px' }}>
        <h1 style={{ fontFamily: 'Syne, sans-serif', fontSize: '1.8rem', fontWeight: 800, marginBottom: '4px' }}>
          AI Career Mentor
        </h1>
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
          <div style={{ width: '8px', height: '8px', borderRadius: '50%', background: '#43e97b', boxShadow: '0 0 8px #43e97b' }} />
          <span style={{ color: 'var(--muted)', fontSize: '0.85rem' }}>Online — ready to guide you</span>
        </div>
      </div>

      {/* Suggestions (only when 1 message) */}
      {messages.length === 1 && (
        <div className="animate-fadeUp-d1" style={{ display: 'flex', flexWrap: 'wrap', gap: '8px', marginBottom: '20px' }}>
          {suggestions.map((s, i) => (
            <button
              key={i}
              onClick={() => sendMessage(s)}
              style={{
                background: 'var(--surface2)',
                border: '1px solid var(--border)',
                borderRadius: '100px',
                padding: '8px 16px',
                fontSize: '0.8rem',
                color: 'var(--muted)',
                cursor: 'pointer',
                transition: 'all 0.15s',
                fontFamily: 'DM Sans, sans-serif',
              }}
              onMouseEnter={e => { e.currentTarget.style.borderColor = 'var(--accent)'; e.currentTarget.style.color = 'var(--text)'; }}
              onMouseLeave={e => { e.currentTarget.style.borderColor = 'var(--border)'; e.currentTarget.style.color = 'var(--muted)'; }}
            >
              {s}
            </button>
          ))}
        </div>
      )}

      {/* Messages */}
      <div style={{
        flex: 1,
        overflowY: 'auto',
        display: 'flex',
        flexDirection: 'column',
        gap: '16px',
        marginBottom: '20px',
        paddingRight: '4px',
      }}>
        {messages.map((msg, i) => (
          <div key={i} style={{
            display: 'flex',
            justifyContent: msg.role === 'user' ? 'flex-end' : 'flex-start',
            gap: '10px',
            alignItems: 'flex-end',
          }}>
            {msg.role === 'assistant' && (
              <div style={{
                width: '32px', height: '32px',
                background: 'linear-gradient(135deg, #6c63ff, #ff6584)',
                borderRadius: '50%',
                display: 'flex', alignItems: 'center', justifyContent: 'center',
                fontSize: '14px',
                flexShrink: 0,
              }}>🧭</div>
            )}
            <div style={{
              maxWidth: '75%',
              background: msg.role === 'user'
                ? 'linear-gradient(135deg, #6c63ff, #8b5cf6)'
                : msg.error ? 'rgba(255,101,132,0.1)' : 'var(--surface2)',
              border: `1px solid ${msg.role === 'user' ? 'transparent' : msg.error ? 'rgba(255,101,132,0.3)' : 'var(--border)'}`,
              borderRadius: msg.role === 'user' ? '18px 18px 4px 18px' : '18px 18px 18px 4px',
              padding: '14px 18px',
              color: msg.error ? '#ff6584' : 'var(--text)',
              fontSize: '0.9rem',
              lineHeight: 1.6,
              whiteSpace: 'pre-wrap',
            }}>
              {msg.content}
            </div>
          </div>
        ))}

        {loading && (
          <div style={{ display: 'flex', gap: '10px', alignItems: 'flex-end' }}>
            <div style={{ width: '32px', height: '32px', background: 'linear-gradient(135deg, #6c63ff, #ff6584)', borderRadius: '50%', display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: '14px' }}>🧭</div>
            <div style={{ background: 'var(--surface2)', border: '1px solid var(--border)', borderRadius: '18px 18px 18px 4px', padding: '14px 18px' }}>
              <div style={{ display: 'flex', gap: '4px', alignItems: 'center' }}>
                {[0,1,2].map(j => (
                  <div key={j} style={{
                    width: '6px', height: '6px', borderRadius: '50%', background: 'var(--muted)',
                    animation: `pulse-glow 1.2s ${j * 0.2}s ease-in-out infinite`,
                  }} />
                ))}
              </div>
            </div>
          </div>
        )}
        <div ref={bottomRef} />
      </div>

      {/* Input */}
      <div className="glass" style={{ padding: '12px', display: 'flex', gap: '10px', alignItems: 'flex-end' }}>
        <textarea
          ref={inputRef}
          className="pf-input"
          value={input}
          onChange={e => setInput(e.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Ask about careers, skills, or your path..."
          rows={1}
          style={{
            flex: 1,
            resize: 'none',
            border: 'none',
            background: 'transparent',
            boxShadow: 'none',
            padding: '8px 4px',
          }}
        />
        <button
          className="pf-btn-primary"
          onClick={() => sendMessage()}
          disabled={!input.trim() || loading}
          style={{ padding: '10px 20px', flexShrink: 0 }}
        >
          Send ↑
        </button>
      </div>
    </div>
  );
}
