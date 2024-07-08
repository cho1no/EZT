/* eslint-disable perfectionist/sort-imports */
import 'src/global.css';

import { useState, useEffect, createContext } from 'react';

import { useScrollToTop } from 'src/hooks/use-scroll-to-top';

// eslint-disable-next-line import/no-cycle
import Router from 'src/routes/sections';
import ThemeProvider from 'src/theme';
import axios from 'axios';

// ----------------------------------------------------------------------
export const MyContext = createContext();

export default function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [myInfo, setMyInfo] = useState({ usersNo: 0, usersName: 'admin', usersRole: '' });
  useEffect(() => {
    checkAuthentication();
  }, []);
  useEffect(() => {
    console.log(myInfo);
  }, [myInfo]);
  const checkAuthentication = async () => {
    try {
      // token get
      const token = await axios.get('/adm/jwt');
      console.log(token);

      axios.defaults.headers.common.Authorization = `Bearer ${token.data}`;
      // token 확인
      const response = await axios.get('/adm/checkJwt');
      console.log(response);
      if (response.status === 200) {
        setIsAuthenticated(true);
      } else {
        alert('만료된 토큰입니다.');
        window.location.href = 'http://localhost:8080/main';
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
      alert('접근권한이 없습니다.', error);
      window.location.href = 'http://localhost:8080/main';
    }
  };
  useScrollToTop();

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
