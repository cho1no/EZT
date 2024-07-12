import React from 'react';
import styled from '@emotion/styled';
// eslint-disable-next-line import/no-extraneous-dependencies
import { SyncLoader } from 'react-spinners';

const Spinner = () => (
  <SpinnerWrapper>
    <h3>잠시만 기다려주세요...</h3>
    <SyncLoader color="#1877f2" margin={5} size={10} />
  </SpinnerWrapper>
);
export default Spinner;

const SpinnerWrapper = styled.div`
  z-index: 999;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
