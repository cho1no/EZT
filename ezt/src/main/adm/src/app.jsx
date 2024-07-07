/* eslint-disable perfectionist/sort-imports */
import 'src/global.css';

import { useState, useEffect } from 'react';

import { useScrollToTop } from 'src/hooks/use-scroll-to-top';

import Router from 'src/routes/sections';
import ThemeProvider from 'src/theme';
import axios from 'axios';

// ----------------------------------------------------------------------

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    checkAuthentication();
  }, []);

  const checkAuthentication = async () => {
    try {
      const token = await axios.get('/adm/jwt');
      console.log(token);
      if (token.data) {
        axios.defaults.headers.common.Authorization = `Bearer ${token.data}`;
      }
      const response = await axios.get('/adm/checkJwt');
      console.log(response);
      if (response.status === 200) {
        setIsAuthenticated(true);
      } else {
        alert('만료된 토큰입니다.');
        window.location.href = 'http://localhost:8080/main';
      }
    } catch (error) {
      alert('접근권한이 없습니다.', error);
      window.location.href = 'http://localhost:8080/main';
    }
  };

  useScrollToTop();

  if (!isAuthenticated) {
    return <div>인증중 ...</div>;
  }

  return (
    <ThemeProvider>
      <Router />
    </ThemeProvider>
  );
}
