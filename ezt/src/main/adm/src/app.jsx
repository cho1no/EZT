/* eslint-disable perfectionist/sort-imports */
import 'src/global.css';
import { lazy, useState, useEffect, createContext } from 'react';

import { useScrollToTop } from 'src/hooks/use-scroll-to-top';

// eslint-disable-next-line import/no-cycle
import Router from 'src/routes/sections';
import ThemeProvider from 'src/theme';
import axios from 'axios';

// ----------------------------------------------------------------------
export const MyContext = createContext();

export const Page404 = lazy(() => import('src/pages/page-not-found'));

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isError, setIsError] = useState(false);
  const [myInfo, setMyInfo] = useState({ usersNo: 0, usersName: '', usersRole: '' });
  useEffect(() => {
    checkAuthentication();
  }, []);
  useEffect(() => {}, [myInfo]);
  const checkAuthentication = async () => {
    try {
      // token get
      const token = await axios.get('/adm/jwt');
      axios.defaults.headers.common.Authorization = `Bearer ${token.data}`;
      // token 확인
      const response = await axios.get('/adm/checkJwt');
      if (response.status === 200) {
        setIsAuthenticated(true);
      } else {
        setIsError(true);
        alert('만료된 토큰입니다.');
      }
      // token 기반 유저 정보 받기
      const info = await axios.get(`/adm/getMyInfo`);
      if (info.data) {
        Object.keys(info.data).forEach((key) => {
          if (info.data[key] === null) {
            delete info.data[key];
          }
        });
        setMyInfo(info.data);
      }
    } catch (error) {
      setIsError(true);
      alert('접근권한이 없습니다.', error);
    }
  };
  useScrollToTop();

  if (isError) {
    return <Page404 />;
  }
  if (!isAuthenticated) {
    return <div>인증중 ...</div>;
  }
  return (
    <MyContext.Provider value={myInfo}>
      <ThemeProvider>
        <Router />
      </ThemeProvider>
    </MyContext.Provider>
  );
}
