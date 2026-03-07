import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' },
});

// Attach JWT token to every request
API.interceptors.request.use((config) => {
  const token = localStorage.getItem('pf_token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// Handle 401 globally (don't redirect if the failed request was login/register)
API.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response?.status === 401) {
      const isAuthRequest = err.config?.url?.includes('/auth/login') || err.config?.url?.includes('/auth/register');
      if (!isAuthRequest) {
        localStorage.removeItem('pf_token');
        localStorage.removeItem('pf_user');
        window.location.href = '/login';
      }
    }
    return Promise.reject(err);
  }
);

// ── Auth ──────────────────────────────────────────────
export const authAPI = {
  register: (data) => API.post('/auth/register', data),
  login:    (data) => API.post('/auth/login', data),
};

// ── Questions ─────────────────────────────────────────
export const questionsAPI = {
  getAll:         ()         => API.get('/questions'),
  getByCategory:  (category) => API.get(`/questions/category/${category}`),
};

// ── Assessment ────────────────────────────────────────
export const assessmentAPI = {
  submit: (data)  => API.post('/assessment/submit', data),
  latest: ()      => API.get('/assessment/latest'),
};

// ── Careers ───────────────────────────────────────────
export const careersAPI = {
  getAll:  ()   => API.get('/careers'),
  getById: (id) => API.get(`/careers/${id}`),
};

// ── Chat ──────────────────────────────────────────────
export const chatAPI = {
  sendMessage: (data) => API.post('/chat/message', data),
};

export default API;
